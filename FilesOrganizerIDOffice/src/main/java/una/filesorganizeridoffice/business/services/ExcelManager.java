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
import una.filesorganizeridoffice.business.util.Processes;
import una.filesorganizeridoffice.business.util.Tools;
import una.filesorganizeridoffice.model.Adult;
import una.filesorganizeridoffice.model.Authorized;
import una.filesorganizeridoffice.model.UnderAgeStudent;
import una.filesorganizeridoffice.model.base.PersonalData;
import una.filesorganizeridoffice.model.base.UniversityPerson;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * A service class who modularize every method related to Excel management.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public class ExcelManager {
    /**
     * The Excel used by the manager to operate.
     */
    private final XLWorkbook xlWorkbook;
    /**
     * The sheet that it's been used by the manager.
     */
    private XLSheet xlSheet;

    /**
     * Receives the Workbook URL.
     * @param url of the workbook file
     */
    public ExcelManager(String url) {
        this.xlWorkbook = new XLWorkbook(url);
    }

    /**
     * Completes XLWorkbook using Excel utility. This charges the basic xml files and the sheets id and names.
     * @return Protocol.Accept or Refuse, depending on the result.
     */
    public Protocol openXL(){
        try {
            XLFactory.buildWorkbook(xlWorkbook);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }

    /**
     * This method opens the default sheet where it will operate, its like executing the Excel.
     * @return Protocol.Accept or Refuse, depending on the result.
     */
    public Protocol startWorking() {
        try{
            openSheet(0);
        }catch (IOException | ParserConfigurationException | SAXException | BusinessException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
    }

    /**
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

    /**
     * This method read row by row and uses XL API to serialize every single row to a request and save one by one
     * in a List, then returns that List.
     * @param initialRow int
     * @param finalRow int
     * @param isStudent boolean
     * @return ArrayList of requests
     */
    public void getRequests(int initialRow, int finalRow, Boolean isStudent) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
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
            Integer processOf;
            if(isStudent){
                if (!isAdult(row)){
                    request = new UnderAgeStudent();
                    authorized = new Authorized();
                    studentSerializer.rowToType(row, authorized, Processes.AUTHORIZED);
                    ((UnderAgeStudent) request).setAuthorized(authorized);
                }
                processOf = Processes.STUDENT;
            }else{
                processOf = Processes.EMPLOYEE;
            }
            studentSerializer.rowToType(row, request, processOf);
            //Add model to list
            Tools.requests.add(request);
        }
        xlSheet.clearIgnoreColumnCases();
    }

    /**
     *
     * @param request
     * @param isStudent
     * @return
     */
    public void createExcel(UniversityPerson request, Boolean isStudent) {
        /*
        EN DESARROLLO
         */
    }

    /**
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

    /**
     * Returns the XLWorkbook that its being managed at the time of the invocation.
     * @return XLWorkbook
     */
    public XLWorkbook getXlWorkbook() {
        return xlWorkbook;
    }

    /**
     * Returns the XLSheet that its being managed at the time of the invocation.
     * @return XLSheet
     */
    public XLSheet getXlSheet() {
        return xlSheet;
    }
}