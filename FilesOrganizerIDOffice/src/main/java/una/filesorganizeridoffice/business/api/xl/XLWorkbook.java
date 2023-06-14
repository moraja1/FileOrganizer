package una.filesorganizeridoffice.business.api.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

import static una.filesorganizeridoffice.business.api.xl.util.NumberUtil.isNumber;

public final class XLWorkbook {
    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private final String xlName;
    private final File xlFile;
    private Document xlWorkbook;
    private Document xlSharedStrings;
    private Document xlStyles;
    private final HashMap<String, String> sheetsIdName = new HashMap();
    final List<XLSheet> xlSheets = new LinkedList<>();

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
        if(xlWorkbook != null){
            try {
                this.xlWorkbook = dbf.newDocumentBuilder().newDocument();
                this.xlWorkbook.appendChild(
                        this.xlWorkbook.importNode(xlWorkbook.getDocumentElement(), true)
                );
            } catch (ParserConfigurationException e) {
                e.printStackTrace(System.out);
            }
        }else{
            this.xlWorkbook = null;
        }
    }

    public Document getXlSharedStrings() {
        return xlSharedStrings;
    }

    public void setXlSharedStrings(Document xlSharedStrings) {
        if(xlSharedStrings != null){
            try {
                this.xlSharedStrings = dbf.newDocumentBuilder().newDocument();
                this.xlSharedStrings.appendChild(
                        this.xlSharedStrings.importNode(xlSharedStrings.getDocumentElement(), true)
                );
            } catch (ParserConfigurationException e) {
                e.printStackTrace(System.out);
            }
        }else{
            this.xlSharedStrings = null;
        }
    }

    public Document getXlStyles() {
        return xlStyles;
    }

    public void setXlStyles(Document xlStyles) {
        if(xlStyles != null){
            try {
                this.xlStyles = dbf.newDocumentBuilder().newDocument();
                this.xlStyles.appendChild(
                        this.xlStyles.importNode(xlStyles.getDocumentElement(), true)
                );
            } catch (ParserConfigurationException e) {
                e.printStackTrace(System.out);
            }
        }
        else{
            this.xlStyles = null;
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

    /**
     * Returns the int value of the shared string in the sharedStrings.xml if exists or -1 if it does not exist yet.
     * @param value string that will be search in sharedStrings.xml file.
     * @return index of the value or -1 if it does not exist.
     */
    private int isSharedStr(String value) {
        NodeList siNodes = xlSharedStrings.getElementsByTagName("si");
        int siNodesLength = siNodes.getLength();
        if(siNodesLength > 0){
            for(int i = 0; i < siNodesLength; i++){
                Element siTag = (Element) siNodes.item(i);
                Element tTag = (Element)siTag.getFirstChild();

                String tValue = tTag.getTextContent();
                if(tValue.equals(value)){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Inserts a shared string in sharedStrings.xml if the value passed is no empty.
     * @param textContext that will be placed as shared string.
     * @return int value index of the shared string if exists.
     */
    public int createSharedStr(String textContext) {
        int idx = isSharedStr(textContext);
        if(!textContext.isEmpty() && !isNumber(textContext)){
            if(idx != -1){
                return idx;
            }
            Element sstTag = (Element) xlSharedStrings.getElementsByTagName("sst").item(0);
            Element siTag = xlSharedStrings.createElement("si");
            Element tTag = xlSharedStrings.createElement("t");

            tTag.setTextContent(textContext);
            siTag.appendChild(tTag);
            sstTag.appendChild(siTag);
        }
        return isSharedStr(textContext);
    }
}
