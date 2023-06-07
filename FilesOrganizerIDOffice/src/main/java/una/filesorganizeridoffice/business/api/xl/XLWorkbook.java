package una.filesorganizeridoffice.business.api.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public final class XLWorkbook {
    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private final String xlName;
    private final File xlFile;
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

    public File getXlFile() {
        return xlFile;
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
        String v;
        NodeList siNodes = xlSharedStrings.getElementsByTagName("si");
        if(sharedStrIdx >= 0 && sharedStrIdx < siNodes.getLength()){
            Element e = (Element) siNodes.item(sharedStrIdx);
            Element t = (Element) e.getFirstChild();
            v = t.getTextContent();
            if(t.hasAttribute("xml:space")){
                v = v.replaceAll("\\s+$", "");
            }
            return v;
        }
        return "";
    }
}
