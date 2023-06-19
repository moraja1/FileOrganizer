package una.filesorganizeridoffice.business.api.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.api.xl.util.XLFactoryConsumer;
import una.filesorganizeridoffice.business.api.xl.util.XLPaths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static una.filesorganizeridoffice.business.api.xl.util.XLPaths.*;

public final class XLFactory {
    /**
     * Obtains Document object from excel file.
     * @param workbook XLWorkbook
     */
    public static void buildWorkbook(XLWorkbook workbook) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        //Obtaining xml basic files
        ZipFile zipFile = new ZipFile(workbook.getXlFile());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if (entry.getName().equals(SHARED_STRINGS)) {
                workbook.setXlSharedStrings(createDocument(zipFile, entry));
            }else if (entry.getName().equals(STYLES)) {
                workbook.setXlStyles(createDocument(zipFile, entry));
            }else if (entry.getName().equals(WORKBOOK)) {
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

    /**
     * Creates a Document object from a ZipEntry
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

    /**
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
        ZipEntry entry;
        String entryName = SHEET.apply(i.toString());
        entry = zipFile.getEntry(entryName);
        if(entry != null){
            XLSheet xlSheet = new XLSheet(w, createDocument(zipFile, entry), entryName);
            w.xlSheets.add(xlSheet);
            return xlSheet;
        }
        return null;
    }

    public static void saveWorkbook(XLWorkbook xlWorkbook, String filename) throws ParserConfigurationException, SAXException, TransformerException {
        //Create a xlsx in the temp directory where the information will be placed.
        String path = System.getProperty("java.io.tmpdir");
        path = path.concat("tempReq").concat("\\");
        File newXL = new File(path);
        if(!newXL.exists()){
            newXL.mkdirs();
        }
        path = path.concat(filename).concat(".xlsx");
        newXL = new File(path);
        File tempFile = new File(newXL.getParent());

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(xlWorkbook.getXlFile());
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryFile = new File(newXL, entry.getName());
                //Create directory entries
                if(entry.isDirectory()){
                    entryFile.mkdirs();
                } else {
                    File entryParent = entryFile.getParentFile();
                    if(!entryParent.exists()){
                        entryParent.mkdirs();
                    }
                }
                //Create entries
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(new File(newXL, entry.getName()));
                fos.write(is.readAllBytes());

                //Change the xml content
                if (entry.getName().equals(SHARED_STRINGS)) {
                    //toStringNode(xlWorkbook.getXlSharedStrings().getDocumentElement());
                    transformXML(xlWorkbook.getXlSharedStrings(), entryFile);
                }else if (entry.getName().equals(STYLES)) {

                }else if (entry.getName().equals(WORKBOOK)) {

                }else{
                    for (int i = 1; i <= xlWorkbook.xlSheets.size(); i++) {
                        if(entry.getName().equals(SHEET.apply(String.valueOf(i)))){
                            transformXML(xlWorkbook.xlSheets.get((i-1)).getXlSheet(), entryFile);
                        }
                    }
                }
            }
        } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }

    private static void transformXML(Document document, File entryFile) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        FileWriter writer = new FileWriter(entryFile);
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
    }

    /**
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
}