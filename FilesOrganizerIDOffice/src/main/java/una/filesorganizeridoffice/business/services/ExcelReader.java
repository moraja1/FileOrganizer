package una.filesorganizeridoffice.business.services;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExcelReader {
    private String excelName;
    private Document sheet1;
    private Document sharedStrings;

    /***
     * Obtains Document object from excel file.
     * @param excelUrl
     */
    public ExcelReader(String excelUrl) throws IOException, ParserConfigurationException, SAXException{
        File excel = new File(excelUrl);
        excelName = excel.getName();

        //Obtaining xml files
        ZipFile zipFile = new ZipFile(excel);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if(entry.getName().equals("xl/worksheets/sheet1.xml")){
                sheet1 = createDocument(zipFile, entry);
            } else if (entry.getName().equals("xl/sharedStrings.xml")) {
                sharedStrings = createDocument(zipFile, entry);
            } else if (sheet1 != null && sharedStrings != null) {
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
    private Document createDocument(ZipFile z,ZipEntry entry) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = z.getInputStream(entry);
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
    }
}
