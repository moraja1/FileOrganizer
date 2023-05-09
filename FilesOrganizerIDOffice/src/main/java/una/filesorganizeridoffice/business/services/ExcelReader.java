package una.filesorganizeridoffice.business.services;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExcelReader {
    private static Document sheet1;
    private static Document sharedStrings;

    public ExcelReader(String excelUrl) {
        File excel = new File(excelUrl);
        try(FileInputStream fileInputStream = new FileInputStream(excel);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)){
            ZipEntry zip;
            while ((zip = zipInputStream.getNextEntry()) != null){
                String fileName = zip.getName();
                System.out.println(fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
