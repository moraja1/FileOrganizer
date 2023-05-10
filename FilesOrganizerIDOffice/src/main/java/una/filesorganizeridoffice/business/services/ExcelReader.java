package una.filesorganizeridoffice.business.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.services.xlService.XLWorkbook;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExcelReader {
    /***
     * Obtains Document object from excel file.
     * @param workbook XLWorkbook
     */
    public ExcelReader(XLWorkbook workbook) throws IOException, ParserConfigurationException, SAXException{
        //Obtaining xml files
        ZipFile zipFile = new ZipFile(workbook.getXlFile());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if(entry.getName().equals("xl/styles.xml")){
                workbook.setXlStyles(createDocument(zipFile, entry));
            } else if (entry.getName().equals("xl/sharedStrings.xml")) {
                workbook.setXlSharedStrings(createDocument(zipFile, entry));
            } else if (entry.getName().equals("xl/workbook.xml")) {
                workbook.setXlWorkbook(createDocument(zipFile, entry));
            } else if (workbook.getXlStyles() != null && workbook.getXlSharedStrings() != null) {
                break;
            }
        }
        Integer count = 1;
        String baseName = "xl/worksheets/sheet";
        ZipEntry entry = null;
        do{
            String entryName = baseName.concat(count.toString()).concat(".xml");
            entry = zipFile.getEntry(entryName);
            if(entry != null){
                workbook.addSheet(createDocument(zipFile, entry));
            }
            count++;
        }while (entry != null);
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
    private Document createDocument(ZipFile z,ZipEntry entry) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = z.getInputStream(entry);
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
    }

    /***
     * Return a sheet from a XLWorkbook with the specified name
     */
    public Document getSheetByName(XLWorkbook w, String name){
        NodeList sheets = w.getXlWorkbook().getElementsByTagName("sheet");

        for (int i = 0; i < sheets.getLength(); i++) {
            Element sheet = (Element) sheets.item(i);
            String nameInSheet = sheet.getAttributeNode("name").getValue();
            if(nameInSheet.equals(name)){
                String rId = sheet.getAttributeNode("r:id").getValue();
                Document xlSheet = lookForSheet(w, rId);
                if(xlSheet != null){
                    return xlSheet;
                }
            }
        }
        return null;
    }

    /***
     * Looks for a sheet based on the r:id param, in the workbook passed by parameter.
     * @param w XlWorkbook
     * @param rId sheet's id
     * @return Document
     */
    private Document lookForSheet(XLWorkbook w, String rId) {
        List<Document> sheetsInW = w.getSheets();

        for (Document d : sheetsInW){
            Element pageSetup = (Element) d.getElementsByTagName("pageSetup").item(0);
            String page_rId = pageSetup.getAttributeNode("r:id").getValue();
            if(page_rId.equals(rId)){
                return d;
            }
        }
        return null;
    }

    /***
     * Print in console any Node. Test purposes.
     * @param node xml Node to be printed.
     */
    private void toStringNode(Element node){
        Document document = node.getOwnerDocument();
        DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(node);
        System.out.println(str);
    }
}
