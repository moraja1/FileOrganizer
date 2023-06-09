package una.filesorganizeridoffice.business.api.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import una.filesorganizeridoffice.business.api.xl.util.DateUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static una.filesorganizeridoffice.business.api.xl.util.DateUtil.isDate;
import static una.filesorganizeridoffice.business.api.xl.util.DateUtil.toDate;
import static una.filesorganizeridoffice.business.api.xl.util.NumberUtil.isNumber;
import static una.filesorganizeridoffice.business.api.xl.util.NumberUtil.isScientificNotation;

public final class XLSheet {
    private final XLWorkbook xlWorkbook;
    private final Document xlSheet;
    private final String name;
    private final List<String> ignoreColumnCases = new ArrayList<>();

    /***
     * Creates a XLSheet
     * @param xlWorkbook
     * @param document
     * @throws ParserConfigurationException
     */
    public XLSheet(XLWorkbook xlWorkbook, Document document, String name) throws ParserConfigurationException {
        this.xlWorkbook = xlWorkbook;
        this.xlSheet = xlWorkbook.getDbf().newDocumentBuilder().newDocument();
        this.xlSheet.appendChild(
                this.xlSheet.importNode(document.getDocumentElement(), true)
        );
        this.name = name;
    }

    /***
     * Returns the XLRow of a specific row number with all its XLCell values if the row exists, else, a base XLRow is returned.
     * This method return null if the index of the row passed if less than zero.
     * @param idx int
     * @return XLRow
     */
    public XLRow getRow(int idx) {
        if(idx > 0) {
            //Cell memory space
            XLRow row = new XLRow(idx);
            XLCell<?> xlCell;
            String cellValue;
            String cellColumn;
            Integer cellRow;
            //Get Row Element
            Element rowTag = getRowElement(idx);
            //Obtains the row number
            if(rowTag != null){
                String rAttr = rowTag.getAttribute("r");
                cellRow = Integer.valueOf(rAttr);
                //Compares row number
                if (cellRow == idx) {
                    //Obtains the cells
                    NodeList cells = rowTag.getElementsByTagName("c");
                    for (int j = 0; j < cells.getLength(); j++) {
                        Element cell = (Element) cells.item(j);
                        //Prepare cell information
                        String cellPoint = cell.getAttribute("r");
                        cellColumn = cellPoint.replace(String.valueOf(cellRow), "");
                        //Ignore Column Cases
                        if (ignoreColumnCases.contains(cellColumn)) {
                            continue;
                        }
                        //Obtains index of sharedString.xml or null if it's not a sharedString
                        Integer sharedStrIdx = getSharedIdx(cell);
                        if (sharedStrIdx != null) {
                            //Obtain sharedString.xml value based on the index
                            cellValue = xlWorkbook.getSharedStrValue(sharedStrIdx);
                        } else {
                            Element v = (Element) cell.getFirstChild();
                            //Obtains the cell value
                            if (v != null) {
                                cellValue = v.getTextContent();
                            } else {
                                cellValue = "";
                            }
                        }
                        //Creates de cell with proper value Type
                        Integer intValue;
                        LocalDate dateValue;
                        String stringValue;
                        if (isScientificNotation(cellValue)) {
                            intValue = new BigDecimal(cellValue).intValue();
                            xlCell = new XLCell<Integer>(cellColumn, cellRow, intValue);
                        } else if (isDate(cellValue)) {
                            dateValue = toDate(cellValue);
                            xlCell = new XLCell<LocalDate>(cellColumn, cellRow, dateValue);
                        } else if (cellValue.matches("\\d*") && !cellValue.isEmpty()) {
                            intValue = Integer.valueOf(cellValue);
                            xlCell = new XLCell<Integer>(cellColumn, cellRow, intValue);
                        } else {
                            stringValue = cellValue;
                            xlCell = new XLCell<String>(cellColumn, cellRow, stringValue);
                        }
                        row.addXlCell(xlCell);
                        //Ends if its adult
                        if (xlCell.getValue().equals("Mayor de edad")) j = cells.getLength();
                    }
                }
            }
            return row;
        }
        return null;
    }

    public void pasteRow(XLRow row) {
        int rowIdx = row.getRowNum();
        if(rowIdx > 0){
            Element rowTag = getRowElement(rowIdx);
            if(rowTag == null){
                //Creo el row
                rowTag = xlSheet.createElement("row");
                rowTag.setAttribute("r", String.valueOf(rowIdx));
                rowTag.setAttribute("spans", "1:2");
                rowTag.setAttributeNS("x14ac", "dyDescent", "0.25");

                for(XLCell<?> cell : row.asList()){
                    //Creo cada celda sin formato y se la inserto al Row
                    Element cellTag = xlSheet.createElement("c");
                    String rValue = cell.getColumnName().concat(String.valueOf(rowIdx));
                    cellTag.setAttribute("r", rValue);

                    Element vTag = xlSheet.createElement("v");
                    var cellValue = cell.getValue();
                    String textContext = convertCellType(cellValue);
                    vTag.setTextContent(textContext);
                    rowTag.appendChild(cellTag);
                }
            }else{
                NodeList cells = rowTag.getElementsByTagName("c");
                for(int i = 0; i < cells.getLength(); i++) {
                   //De cada tag de celda obtengo la columna
                   Element cellTag = (Element)cells.item(i);
                   String cellColumn = cellTag.getAttribute("r");
                   cellColumn = cellColumn.replace(String.valueOf(rowIdx), "");

                   //Recorro cada celda del XLRow y comparo las columnas, si coinciden inserto el valor
                    for(XLCell<?> cell : row.asList()){
                        if (cell.getColumnName().equals(cellColumn)){
                            cellTag.setTextContent(convertCellType(cell.getValue()));
                        }
                    }
                }
            }
        }
    }

    private String convertCellType(Object cellValue) {
        if(cellValue instanceof Integer){
            return cellValue.toString();
        }
        if(cellValue instanceof LocalDate){
            return DateUtil.toString(((LocalDate)cellValue));
        }
        if(cellValue instanceof String){
            return (String)cellValue;
        }
        return null;
    }

    private Element getRowElement(int idx) {
        NodeList rows = xlSheet.getElementsByTagName("row");
        int rowsCant = rows.getLength();
        if(idx <= rowsCant) {
            return (Element) rows.item(idx - 1);
        }
        return null;
    }

    /***
     * Obtains index of sharedString.xml or null if it's not a sharedString
     * @param e Element
     * @return Integer if it is a sharedString or null if no
     */
    private Integer getSharedIdx(Element e) {
        if(e.hasAttribute("t")){
           String tValue = e.getFirstChild().getFirstChild().getNodeValue();
           return Integer.valueOf(tValue);
        }
        return null;
    }

    public void addIgnoreColumnCase(String column) {
        ignoreColumnCases.add(column);
    }

    public void clearIgnoreColumnCases() {
        if (!ignoreColumnCases.isEmpty()){
            ignoreColumnCases.clear();
        }
    }

    /**
     * Returns the name of the xml file that contains the sheet's information
     * @return String
     */
    public String getName() {
        return name;
    }
}