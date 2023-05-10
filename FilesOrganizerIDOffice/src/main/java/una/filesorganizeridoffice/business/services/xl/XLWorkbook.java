package una.filesorganizeridoffice.business.services.xl;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XLWorkbook {
    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private String xlName;
    private File xlFile;
    private Document xlWorkbook;
    private Document xlSharedStrings;
    private final List<Document> sheets = new ArrayList<>();
    private Document xlStyles;

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
        try {
            this.xlStyles = dbf.newDocumentBuilder().newDocument();
            this.xlStyles.appendChild(
                    this.xlStyles.importNode(xlStyles.getDocumentElement(), true)
            );
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        }
    }

    public List<Document> getSheets() {
        return sheets;
    }

    public void addSheet(Document sheet) {
        sheets.add(sheet);
    }

    public Document getSheet(int index) {
        return sheets.get(index);
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
}
