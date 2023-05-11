package una.filesorganizeridoffice.business.services.xl;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.HashMap;

public class XLWorkbook {
    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private String xlName;
    private File xlFile;
    private Document xlWorkbook;
    private Document xlSharedStrings;
    private Document xlStyles;
    private final HashMap<String, String> sheetsIdName = new HashMap();

    public XLWorkbook(String xlUrl) {
        xlFile = new File(xlUrl);
        xlName = xlFile.getName();
    }
    public String getXlName() {
        return xlName;
    }

    public void setXlName(String xlName) {
        this.xlName = xlName;
    }

    public File getXlFile() {
        return xlFile;
    }

    public void setXlFile(File xlFile) {
        this.xlFile = xlFile;
    }

    public void addSheet(String sheet_rId, String sheetName) {
        sheetsIdName.put(sheetName, sheet_rId);
    }

    public String getSheet(String key) {
        return sheetsIdName.get(key);
    }

    public HashMap<String, String> getSheets() {
        return sheetsIdName;
    }

    public Document getXlWorkbook() {
        return xlWorkbook;
    }

    public void setXlWorkbook(Document xlWorkbook) {
        try {
            this.xlWorkbook = dbf.newDocumentBuilder().newDocument();
            this.xlWorkbook.appendChild(
                    this.xlWorkbook.importNode(xlWorkbook.getDocumentElement(), true)
            );
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        }
    }

    public Document getXlSharedStrings() {
        return xlSharedStrings;
    }

    public void setXlSharedStrings(Document xlSharedStrings) {
        try {
            this.xlSharedStrings = dbf.newDocumentBuilder().newDocument();
            this.xlSharedStrings.appendChild(
                    this.xlSharedStrings.importNode(xlSharedStrings.getDocumentElement(), true)
            );
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        }
    }

    public Document getXlStyles() {
        return xlStyles;
    }

    public void setXlStyles(Document xlStyles) {
        this.xlStyles = xlStyles;
        try {
            this.xlStyles = dbf.newDocumentBuilder().newDocument();
            this.xlStyles.appendChild(
                    this.xlStyles.importNode(xlStyles.getDocumentElement(), true)
            );
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        }
    }

    /***
     * Looks for a sheet based on the r:id param, in the workbook passed by parameter.
     * @param rId sheet's id
     * @return Document
     */
    /*private Document lookForSheet(String rId) {
        for (Document d : sheets){
            Element pageSetup = (Element) d.getElementsByTagName("pageSetup").item(0);
            String page_rId = pageSetup.getAttributeNode("r:id").getValue();
            if(page_rId.equals(rId)){
                return d;
            }
        }
        return null;
    }*/

    public DocumentBuilderFactory getDbf() {
        return dbf;
    }
}
