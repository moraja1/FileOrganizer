package una.filesorganizeridoffice.business.api.xl.util;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void apply(T t, U u, V v) throws IOException, ParserConfigurationException, SAXException, TransformerException;
}
