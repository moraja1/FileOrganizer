package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.xl.XLRow;
import una.filesorganizeridoffice.business.xl.XLSheet;
import una.filesorganizeridoffice.business.xl.XLWorkbook;
import una.filesorganizeridoffice.business.xl.util.XLFactory;
import una.filesorganizeridoffice.business.xl.util.XLSerializer;
import una.filesorganizeridoffice.model.Adult;
import una.filesorganizeridoffice.model.UnderAgeStudent;
import una.filesorganizeridoffice.model.base.UniversityPerson;

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
    public Protocol openXL(){
        try {
            XLFactory.buildWorkbook(xlWorkbook);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }

    public Protocol startWorking() {
        try{
            openSheet(0);
        }catch (IOException | ParserConfigurationException | SAXException | BusinessException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }

    /***
     * This method creates an Excel sheet based on the index provided. By default, it will charge de index 0.
     * @param i sheet´s index
     * @return Protocol.Accept or Refuse, depending on the result.
     */
    private void openSheet(int i) throws IOException, ParserConfigurationException, SAXException, BusinessException {
        i++;
        xlSheet = XLFactory.loadSheet(xlWorkbook, i);
        if(xlSheet == null){
            throw  new BusinessException("Hoja de Excel");
        }
    }

    /***
     * This method read row by row and uses XL API to serialize every single row to a request and save one by one
     * in a List, then returns that List.
     * @param initialRow int
     * @param finalRow int
     * @param isStudent boolean
     * @return ArrayList of requests
     */
    public List<UniversityPerson> getRequests(int initialRow, int finalRow, Boolean isStudent) {
        List<UniversityPerson> requests = new ArrayList<>();
        xlSheet.addIgnoreColumnCase("A");
        if (isStudent) {
            xlSheet.addIgnoreColumnCase("R");
            xlSheet.addIgnoreColumnCase("S");
        } else {
            xlSheet.addIgnoreColumnCase("C");
        }

        for (int i = initialRow; i <= finalRow; i++) {
            //Ask the sheet to return a row by number
            XLRow row = xlSheet.getRow(i);
            //Converts the row into model
            requests.add(XLSerializer.rowToRequest(row, isStudent));
            //Add model to list

        }
        xlSheet.clearIgnoreColumnCases();
        //Return list
        return requests;
    }


}
