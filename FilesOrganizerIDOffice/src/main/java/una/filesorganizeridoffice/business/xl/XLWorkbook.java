package una.filesorganizeridoffice.business.xl;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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

    public DocumentBuilderFactory getDbf() {
        return dbf;
    }

    /***
     * Get the value from sharedString.xml based on the index passed by param.
     * @param sharedStrIdx Value Index
     * @return String with the value or empty String if index it's out of bounds.
     */
    public String getSharedStrValue(Integer sharedStrIdx) {
        NodeList siNodes = xlSharedStrings.getElementsByTagName("si");
        if(sharedStrIdx >= 0 && sharedStrIdx < siNodes.getLength()){
            return siNodes.item(sharedStrIdx).getFirstChild().getFirstChild().getNodeValue();
        }
        return "";
    }
}
