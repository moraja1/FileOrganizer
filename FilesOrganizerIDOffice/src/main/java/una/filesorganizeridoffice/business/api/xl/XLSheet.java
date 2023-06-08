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
     * Obtains the Row XLCells of a specific row number
     * @param idx int
     * @return XLRow
     */
    public XLRow getRow(int idx) {
        //Cell memory space
        XLRow row = new XLRow(idx);
        XLCell<?> xlCell;
        String cellValue;
        String cellColumn;
        Integer cellRow;
        //Get all rows
        NodeList rows = xlSheet.getElementsByTagName("row");
        int rowsCant = rows.getLength();
        if(idx > 0 && idx <= rowsCant){
            for (int i = 0; i < rows.getLength(); i++) {
                Element e = (Element) rows.item(i);
                //Obtains the row number
                String r = e.getAttributeNode("r").getValue();
                cellRow = Integer.valueOf(r);
                //Compares row number
                if (cellRow == idx){
                    //Obtains the cells
                    NodeList cells = e.getElementsByTagName("c");
                    for (int j = 0; j < cells.getLength(); j++) {
                        Element cell = (Element) cells.item(j);
                        //Prepare cell information
                        String cellPoint = cell.getAttributeNode("r").getValue();
                        cellColumn = cellPoint.replace(String.valueOf(cellRow), "");
                        //Ignore Column Cases
                        if(ignoreColumnCases.contains(cellColumn)){
                            continue;
                        }
                        //Obtains index of sharedString.xml or null if it's not a sharedString
                        Integer sharedStrIdx = getSharedIdx(cell);
                        if(sharedStrIdx != null){
                            //Obtain sharedString.xml value based on the index
                            cellValue = xlWorkbook.getSharedStrValue(sharedStrIdx);
                        }else{
                            Element v = (Element) cell.getFirstChild();
                            //Obtains the cell value
                            if(v != null){
                                cellValue = v.getTextContent();
                            }else{
                                cellValue = "";
                            }
                        }
                        //Creates de cell with proper value Type
                        Integer intValue;
                        LocalDate dateValue;
                        String stringValue;
                        if(isScientificNotation(cellValue)){
                            intValue = new BigDecimal(cellValue).intValue();
                            xlCell = new XLCell<Integer>(cellColumn, cellRow, intValue);
                        }else if(DateUtil.isDate(cellValue)){
                            dateValue = DateUtil.toDate(cellValue);
                            xlCell = new XLCell<LocalDate>(cellColumn, cellRow, dateValue);
                        }else if(cellValue.matches("\\d*") && !cellValue.isEmpty()){
                            intValue = Integer.valueOf(cellValue);
                            xlCell = new XLCell<Integer>(cellColumn, cellRow, intValue);
                        }else{
                            stringValue = cellValue;
                            xlCell = new XLCell<String>(cellColumn, cellRow, stringValue);
                        }

                        row.addXlCell(xlCell);
                        //Ends if its adult
                        if(xlCell.getValue().equals("Mayor de edad")) j = cells.getLength();
                    }
                    return row;
                }
            }
        }
        return null;
    }

    public void insertRow(XLRow row) {

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