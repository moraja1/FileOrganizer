package una.filesorganizeridoffice.business.api.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.api.xl.util.TriConsumer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class XLFactory {
    private static final TriConsumer<XLWorkbook, ZipFile, ZipEntry> BUILDER = (w, zip, entry) ->
    {
        if (entry.getName().equals("xl/sharedStrings.xml")) {
            w.setXlSharedStrings(createDocument(zip, entry));
        }else if (entry.getName().equals("xl/styles.xml")) {
            w.setXlStyles(createDocument(zip, entry));
        }else if (entry.getName().equals("xl/workbook.xml")) {
            w.setXlWorkbook(createDocument(zip, entry));
        }
    };

    private static final TriConsumer<XLWorkbook, ZipFile, ZipEntry> SAVER = (w, zip, entry) ->
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(outputStream);
        DOMSource source = null;
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(w.getXlFile()));
        if (entry.getName().equals("xl/sharedStrings.xml")) {
            source = new DOMSource(w.getXlSharedStrings());
        }else if (entry.getName().equals("xl/styles.xml")) {
            source = new DOMSource(w.getXlStyles());
        }else if (entry.getName().equals("xl/workbook.xml")) {
            source = new DOMSource(w.getXlWorkbook());
        }
        if(source != null){
            transformer.transform(source, result);
            /*
            EN DESARROLLO
             */
        }

    };
    /***
     * Obtains Document object from excel file.
     * @param workbook XLWorkbook
     */
    public static void buildWorkbook(XLWorkbook workbook) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        coreAction(workbook, BUILDER);

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

    private static void coreAction(XLWorkbook workbook, TriConsumer<XLWorkbook, ZipFile, ZipEntry> consumer) throws IOException,
            ParserConfigurationException, SAXException, TransformerException {
        //Obtaining xml basic files
        ZipFile zipFile = new ZipFile(workbook.getXlFile());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            consumer.apply(workbook, zipFile, entry);
            if (workbook.getXlWorkbook() != null && workbook.getXlStyles() != null &&
                    workbook.getXlSharedStrings() != null) {
                break;
            }
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
    public static XLSheet buildSheet(XLWorkbook w, Integer i) throws IOException, ParserConfigurationException, SAXException {
        ZipFile zipFile = new ZipFile(w.getXlFile());
        String baseName = "xl/worksheets/sheet";
        ZipEntry entry = null;
        String entryName = baseName.concat(i.toString()).concat(".xml");
        entry = zipFile.getEntry(entryName);
        if(entry != null){
            XLSheet xlSheet = new XLSheet(w, createDocument(zipFile, entry), entryName);
            w.xlSheets.add(xlSheet);
            return xlSheet;
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
        serializer.getDomConfig().setParameter("format-pretty-print", true);
        String str = serializer.writeToString(node);
        System.out.println(str);
    }

    public static void toStringSheets(XLWorkbook w){
        for(XLSheet node : w.xlSheets){
            Document document = node.getXlSheet();
            DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
            LSSerializer serializer = domImplLS.createLSSerializer();
            serializer.getDomConfig().setParameter("format-pretty-print", true);
            String str = serializer.writeToString(node.getXlSheet().getDocumentElement());
            System.out.println(str);
        }
    }

    public static void saveWorkbook(XLWorkbook xlWorkbook) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        coreAction(xlWorkbook, SAVER);
    }
}