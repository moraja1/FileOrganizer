package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.api.xl.XLWorkbook;
import una.filesorganizeridoffice.business.api.xl.util.XLFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

class ExcelReaderTest {
    public static void main(String[] args) {
        try {
            XLWorkbook w = new XLWorkbook("C:\\Users\\N00148095\\Downloads\\Copia de Lista de Solicitudes Pendientes de Resultado.xlsx");
            XLFactory.buildWorkbook(w);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}