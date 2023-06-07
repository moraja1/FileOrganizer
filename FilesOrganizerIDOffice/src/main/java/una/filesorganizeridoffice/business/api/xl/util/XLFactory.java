package una.filesorganizeridoffice.business.api.xl.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.api.xl.XLSheet;
import una.filesorganizeridoffice.business.api.xl.XLWorkbook;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class XLFactory {
    /***
     * Obtains Document object from excel file.
     * @param workbook XLWorkbook
     */
    public static void buildWorkbook(XLWorkbook workbook) throws IOException, ParserConfigurationException, SAXException{
        //Obtaining xml basic files
        ZipFile zipFile = new ZipFile(workbook.getXlFile());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if (entry.getName().equals("xl/sharedStrings.xml")) {
                workbook.setXlSharedStrings(createDocument(zipFile, entry));
            }else if (entry.getName().equals("xl/styles.xml")) {
                workbook.setXlStyles(createDocument(zipFile, entry));
            }else if (entry.getName().equals("xl/workbook.xml")) {
                workbook.setXlWorkbook(createDocument(zipFile, entry));
            }
            if (workbook.getXlWorkbook() != null && workbook.getXlStyles() != null &&
                    workbook.getXlSharedStrings() != null) {
                break;
            }
        }

        //Set sheets in workbook
        Document xlWorkbook = workbook.getXlWorkbook();
        NodeList sheets = xlWorkbook.getElementsByTagName("sheet");

        for (int i = 0; i < sheets.getLength(); i++) {
            Element sheet = (Element) sheets.item(i);
            String sheetName = sheet.getAttributeNode("name").getValue();
            String sheet_rId = sheet.getAttributeNode("r:id").getValue();
            workbook.addSheet(sheet_rId, sheetName);
        }
    }

    /***
     * Creates a Documento object from a ZipEntry
     * @param z ZipFile
     * @param entry ZipEntry
     * @return org.w3c.dom.Document
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private static Document createDocument(ZipFile z,ZipEntry entry) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = z.getInputStream(entry);
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
    }

    /***
     * Creates a Document of the sheet with the filename "sheet" plus the index passed by param plus ".xml" located in
     * the Workbook file passed by Param.
     * @param w XLWorkbook
     * @param i Integer
     * @return XLSheet if the sheet with the name exists, or null if not.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static XLSheet loadSheet(XLWorkbook w, Integer i) throws IOException, ParserConfigurationException, SAXException {
        ZipFile zipFile = new ZipFile(w.getXlFile());
        String baseName = "xl/worksheets/sheet";
        ZipEntry entry = null;
        String entryName = baseName.concat(i.toString()).concat(".xml");
        entry = zipFile.getEntry(entryName);
        if(entry != null){
            return new XLSheet(w, createDocument(zipFile, entry), entryName);
        }
        return null;
    }

    /***
     * Print in console any Node. Test purposes.
     * @param node xml Node to be printed.
     */
    public static void toStringNode(Element node){
        Document document = node.getOwnerDocument();
        DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(node);
        System.out.println(str);
    }
}