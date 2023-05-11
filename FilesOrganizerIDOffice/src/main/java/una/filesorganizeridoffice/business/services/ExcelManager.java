package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.services.xl.XLSheet;
import una.filesorganizeridoffice.business.services.xl.XLWorkbook;
import una.filesorganizeridoffice.business.services.xl.util.ExcelFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ExcelManager {
    private XLWorkbook xlWorkbook;

    private XLSheet xlSheet;

    public ExcelManager(String url) {
        this.xlWorkbook = new XLWorkbook(url);
    }

    public Protocol completeXl(){
        try {
            ExcelFactory.buildWorkbook(xlWorkbook);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }


    public Protocol loadSheet(int i) {
        i++;
        try {
            xlSheet = ExcelFactory.buildSheet(xlWorkbook, i);
            if(xlSheet == null){
                return Protocol.Refused;
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }
}
