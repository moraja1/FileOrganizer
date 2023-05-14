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
        NodeList rows = xlSheet.getElementsByTagName("row");
        for (int i = 0; i < rows.getLength(); i++) {
            Element e = (Element) rows.item(i);
            String r = e.getAttributeNode("r").getValue();
            boolean isNumeric = (r != null && r.matches("[0-9]+"));
            if(isNumeric){
                int rowNum = Integer.valueOf(r);
                if (rowNum == idx){
                    NodeList cells = e.getElementsByTagName("c");
                    for (int j = 0; j < cells.getLength(); j++) {
                        Element cell = (Element) cells.item(j);
                        Integer sharedStrIdx = getSharedIdx(cell);
                        if(sharedStrIdx != null){
                            //Obtengo el valor de SharedStrings.xml
                            String sharedStr = xlWorkbook.getSharedStrValue(sharedStrIdx);
                        }else{
                            //Obtengo el valor directamente de la celda.
                            /*
                            EN DESARROLLO
                             */
                        }
                    }
                    break;
                }
            }
        }
        return new XLRow();
    }

    private Integer getSharedIdx(Element e) {
        if(e.hasAttribute("t")){
            /*
            EN DESARROLLO
             */
        }else{
            /*
            EN DESARROLLO
             */
        }
        return null;
    }
}
