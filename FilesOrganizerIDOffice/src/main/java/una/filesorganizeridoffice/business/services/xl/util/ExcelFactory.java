package una.filesorganizeridoffice.business.services.xl.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.services.xl.XLSheet;
import una.filesorganizeridoffice.business.services.xl.XLWorkbook;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExcelFactory {
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
                workbook.setXlWorkbook(createDocument(zipFile, entry));
            }else if (entry.getName().equals("xl/styles.xml")) {
                workbook.setXlWorkbook(createDocument(zipFile, entry));
            }else if (entry.getName().equals("xl/workbook.xml")) {
                workbook.setXlWorkbook(createDocument(zipFile, entry));
            } else if (workbook.getXlWorkbook() != null && workbook.getXlStyles() != null &&
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
     * Print in console any Node. Test purposes.
     * @param node xml Node to be printed.
     */
    private static void toStringNode(Element node){
        Document document = node.getOwnerDocument();
        DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(node);
        System.out.println(str);
    }

    public static XLSheet buildSheet(XLWorkbook w, Integer i) throws IOException, ParserConfigurationException, SAXException {
        ZipFile zipFile = new ZipFile(w.getXlFile());
        String baseName = "xl/worksheets/sheet";
        ZipEntry entry = null;
        i++;
        String entryName = baseName.concat(i.toString()).concat(".xml");
        entry = zipFile.getEntry(entryName);
        if(entry != null){
            XLSheet sheet = new XLSheet(w);
            sheet.setXlSheet(createDocument(zipFile, entry));
            return sheet;
        }
        return null;
    }
}