package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.xl.XLRow;
import una.filesorganizeridoffice.business.xl.XLSheet;
import una.filesorganizeridoffice.business.xl.XLWorkbook;
import una.filesorganizeridoffice.business.xl.util.ExcelFactory;
import una.filesorganizeridoffice.model.UniversityPerson;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelManager {
    private XLWorkbook xlWorkbook;

    private XLSheet xlSheet;

    /***
     * Receives the Workbook URL.
     * @param url
     */
    public ExcelManager(String url) {
        this.xlWorkbook = new XLWorkbook(url);
    }

    /***
     * Completes XLWorkbook using Excel utility. This charges the basic xml files and the sheets id and names.
     * @return
     */
    public Protocol completeXl(){
        try {
            ExcelFactory.buildWorkbook(xlWorkbook);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }

    /***
     * This method creates an Excel sheet based on the index provided. By default, it will charge de index 0.
     * @param i sheet´s index
     * @return Protocol.Accept or Refuse, depending on the result.
     */
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

    public List<UniversityPerson> getRequests(int initialRow, int finalRow, Boolean isStudent) {
        List<UniversityPerson> solicitudes = new ArrayList<>();
        xlSheet.addIgnoreColumnCase("A");
        if (isStudent){
            xlSheet.addIgnoreColumnCase("R");
            xlSheet.addIgnoreColumnCase("S");
        }else{
            xlSheet.addIgnoreColumnCase("C");
        }

        for (int i = initialRow; i <= finalRow; i++) {
            //Ask the sheet to return a row by number
            XLRow row = xlSheet.getRow(i);

            //Converts the row into model


            //Add model to list


        }
        xlSheet.clearIgnoreColumnCases();
        //Return list
        return  new ArrayList<>();
    }
}
