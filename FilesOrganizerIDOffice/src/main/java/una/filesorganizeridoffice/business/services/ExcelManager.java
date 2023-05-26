package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.api.xl.XLRow;
import una.filesorganizeridoffice.business.api.xl.XLSheet;
import una.filesorganizeridoffice.business.api.xl.XLWorkbook;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.business.api.xl.util.XLFactory;
import una.filesorganizeridoffice.business.api.xl.util.XLSerializer;
import una.filesorganizeridoffice.model.Adult;
import una.filesorganizeridoffice.model.Authorized;
import una.filesorganizeridoffice.model.UnderAgeStudent;
import una.filesorganizeridoffice.model.base.PersonalData;
import una.filesorganizeridoffice.model.base.UniversityPerson;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ExcelManager {
    private final XLWorkbook xlWorkbook;
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
    public List<UniversityPerson> getRequests(int initialRow, int finalRow, Boolean isStudent) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
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
            XLSerializer<PersonalData> studentSerializer = new XLSerializer<>();
            UniversityPerson request = new Adult();
            Authorized authorized;
            String processOf;
            if(isStudent){
                if (!isAdult(row)){
                    request = new UnderAgeStudent();
                    authorized = new Authorized();
                    studentSerializer.rowToRequest(row, authorized, "authorized");
                    ((UnderAgeStudent) request).setAuthorized(authorized);
                }
                processOf = "student";
            }else{
                processOf = "employee";
            }
            studentSerializer.rowToRequest(row, request, processOf);
            //Add model to list
            requests.add(request);
        }
        xlSheet.clearIgnoreColumnCases();
        //Return list
        return requests;
    }

    /***
     * Verifies if a row contains at least one cell stating the request to be from an adult.
     * @param row XLRow
     * @return Boolean
     */
    private boolean isAdult(XLRow row) {
        for (int i = 0; i < row.getCellCount(); i++) {
            if(row.getCell(i).getValue().equals("Mayor de edad")){
                return true;
            } else if (row.getCell(i).getValue().equals("Menor de edad")) {
                return false;
            }
        }
        return false;
    }
}