package una.filesorganizeridoffice.business.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

    public XLRow getRow(int i) {
        NodeList rows = xlSheet.getElementsByTagName("row");
        for (int j = 0; j < rows.getLength(); j++) {
            ExcelFactory.toStringNode((Element) rows.item(j));
        }
        return new XLRow();
    }
}
