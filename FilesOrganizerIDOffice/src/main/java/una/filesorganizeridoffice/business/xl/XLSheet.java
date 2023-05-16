package una.filesorganizeridoffice.business.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import una.filesorganizeridoffice.business.xl.util.DateUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XLSheet {
    private XLWorkbook xlWorkbook;
    private Document xlSheet;
    private List<String> ignoreColumnCases = new ArrayList<>();

    public XLSheet(XLWorkbook xlWorkbook) {
        this.xlWorkbook = xlWorkbook;
    }

    public Document getXlSheet() {
        return xlSheet;
    }

    public void setXlSheet(Document xlSheet) {
        try {
            this.xlSheet = xlWorkbook.getDbf().newDocumentBuilder().newDocument();
            this.xlSheet.appendChild(
                    this.xlSheet.importNode(xlSheet.getDocumentElement(), true)
            );
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        }
    }

    public XLWorkbook getXlWorkbook() {
        return xlWorkbook;
    }

    /***
     * Obtains the Row XLCells of a specific row number
     * @param idx int
     * @return XLRow
     */
    public XLRow getRow(int idx) {
        //Cell memory space
        XLRow row = new XLRow();
        XLCell xlCell;
        String cellValue;
        String cellColumn;
        Integer cellRow;
        //Get all rows
        NodeList rows = xlSheet.getElementsByTagName("row");
        if(idx > 0 && idx < rows.getLength()){
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
                        xlCell = createXlCell(cellColumn, cellRow, cellValue);
                        row.addXlCell(xlCell);
                    }
                    System.out.println(row);
                    return row;
                }
            }
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

    /***
     * Creates an XLCell containing the row number, column name and a value of the proper Type.
     * @param cellColumn String
     * @param cellRow Integer
     * @param cellValue Object
     * @return XLCell
     */
    private XLCell createXlCell(String cellColumn, Integer cellRow, String cellValue) {
        Integer intValue;
        LocalDate dateValue;
        if(isScientificNotation(cellValue)){
            intValue = new BigDecimal(cellValue).intValue();
            return new XLCell<Integer>(cellColumn, cellRow, intValue);
        }else if(DateUtil.isDate(cellValue)){
            dateValue = DateUtil.toDate(cellValue);
            return new XLCell<LocalDate>(cellColumn, cellRow, dateValue);
        }
        return new XLCell<String>(cellColumn, cellRow, cellValue);
    }

    /***
     * Detects whether a String contains a Scientific Notation Number
     * @param numberString String vale
     * @return Boolean
     */
    private boolean isScientificNotation(String numberString) {

        // Validate number
        try {
            new BigDecimal(numberString);
        } catch (NumberFormatException e) {
            return false;
        }

        // Check for scientific notation
        return numberString.toUpperCase().contains("E") && (numberString.charAt(1)=='.' || numberString.charAt(2)=='.');
    }

    public void addIgnoreColumnCase(String column) {
        ignoreColumnCases.add(column);
    }

    public void clearIgnoreColumnCases() {
        if (!ignoreColumnCases.isEmpty()){
            ignoreColumnCases.clear();
        }
    }
}