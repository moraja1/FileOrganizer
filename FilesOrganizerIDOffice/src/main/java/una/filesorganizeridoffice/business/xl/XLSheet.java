package una.filesorganizeridoffice.business.xl;

import org.w3c.dom.Document;

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
}
