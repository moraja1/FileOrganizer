package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.services.xlService.XLWorkbook;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {
    public static void main(String[] args) {
        try {
            XLWorkbook w = new XLWorkbook("C:\\Users\\N00148095\\Downloads\\Copia de Lista de Solicitudes Pendientes de Resultado.xlsx");
            ExcelReader ex = new ExcelReader(w);
            DOMSource domSource = new DOMSource(w.getSheet(0));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            ex.getSheetByName(w, "130320i23");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}