package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.services.xl.XLWorkbook;
import una.filesorganizeridoffice.business.services.xl.util.ExcelFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

class ExcelReaderTest {
    public static void main(String[] args) {
        try {
            XLWorkbook w = new XLWorkbook("C:\\Users\\N00148095\\Downloads\\Copia de Lista de Solicitudes Pendientes de Resultado.xlsx");
            ExcelFactory.buildWorkbook(w);
            System.out.println(w.getSheets().keySet());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}