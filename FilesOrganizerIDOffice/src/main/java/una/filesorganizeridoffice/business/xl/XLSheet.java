package una.filesorganizeridoffice.business.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import una.filesorganizeridoffice.business.xl.util.ExcelFactory;

import javax.xml.parsers.ParserConfigurationException;

public class XLSheet {
    private XLWorkbook xlWorkbook;
    private Document xlSheet;

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

    public XLRow getRow(int idx) {
        //Cell memory space
        XLCell xlCell;
        String cellValue;
        String cellColumn;
        Integer cellRow;
        //Get all rows
        NodeList rows = xlSheet.getElementsByTagName("row");
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
                    //Obtains index of sharedString.xml or null if it's not a sharedString
                    Integer sharedStrIdx = getSharedIdx(cell);
                    if(sharedStrIdx != null){
                        //Obtain sharedString.xml value based on the index
                        cellValue = xlWorkbook.getSharedStrValue(sharedStrIdx);
                    }else{
                        //Obtains the cell value
                        cellValue = cell.getFirstChild().getFirstChild().getNodeValue();
                    }
                    //Prepare cell information
                    String cellPoint = cell.getAttributeNode("r").getValue();
                    cellColumn = cellPoint.replace(String.valueOf(cellRow), "");

                    //Converts cell value to the proper type

                }
                i = rows.getLength();
            }
        }
        return new XLRow();
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
}
