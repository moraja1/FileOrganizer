package una.filesorganizeridoffice.business.api.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public final class XLSheet {
    private final XLWorkbook xlWorkbook;
    private final Document xlSheet;
    private final List<String> ignoreColumnCases = new ArrayList<>();

    /***
     * Creates a XLSheet
     * @param xlWorkbook
     * @param document
     * @throws ParserConfigurationException
     */
    public XLSheet(XLWorkbook xlWorkbook, Document document) throws ParserConfigurationException {
        this.xlWorkbook = xlWorkbook;
        this.xlSheet = xlWorkbook.getDbf().newDocumentBuilder().newDocument();
        this.xlSheet.appendChild(
                this.xlSheet.importNode(document.getDocumentElement(), true)
        );
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
                        xlCell = new XLCell<>(cellColumn, cellRow, cellValue);
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
}