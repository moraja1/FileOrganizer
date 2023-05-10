package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {
    public static void main(String[] args) {
        try {
            ExcelReader ex = new ExcelReader("C:\\Users\\N00148095\\Downloads\\Copia de Lista de Solicitudes Pendientes de Resultado.xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}