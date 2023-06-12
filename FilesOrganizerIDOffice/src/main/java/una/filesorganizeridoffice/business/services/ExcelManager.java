package una.filesorganizeridoffice.business.services;

import org.xml.sax.SAXException;
import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.api.xl.XLCell;
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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

/**
 * A service class who modularize every method related to Excel management.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public class ExcelManager {
    /**
     * The Excel used by the manager to operate.
     */
    private XLWorkbook xlWorkbook;
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
     * This method opens the default sheet where it will operate, it is like executing the Excel file.
     * @return Protocol.Accept or Refuse, depending on the result.
     */
    public Protocol startWorking() {
        try{
            xlSheet = XLFactory.buildSheet(xlWorkbook, 1);
            if(xlSheet == null){
                throw  new BusinessException("Hoja de Excel");
            }
        }catch (IOException | ParserConfigurationException | SAXException | BusinessException e) {
            return Protocol.Refused;
        }
        return Protocol.Accepted;
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
            if(row != null){
                //Correct address
                for(XLCell cell : row.asList()){
                    if(isStudent && cell.getColumnName().equals("N")){
                        String address = (String) cell.getValue();
                        address = address.substring(0, address.indexOf("-"));
                        cell.setValue(address);
                        break;
                    } else if(!isStudent && cell.getColumnName().equals("O")) {
                        String address = (String) cell.getValue();
                        address = address.substring(0, address.indexOf("-"));
                        cell.setValue(address);
                        break;
                    }
                }
                //Converts the row into model
                XLSerializer<PersonalData> serializer = new XLSerializer<>();
                UniversityPerson request = new Adult();
                Authorized authorized;
                int processOf;
                if(isStudent){
                    if (!isAdult(row)){
                        request = new UnderAgeStudent();
                        authorized = new Authorized();
                        serializer.rowToType(row, authorized, Processes.AUTHORIZED);
                        ((UnderAgeStudent) request).setAuthorized(authorized);
                    }
                    processOf = Processes.STUDENT;
                }else{
                    processOf = Processes.EMPLOYEE;
                }
                serializer.rowToType(row, request, processOf);
                //Add model to list
                Tools.requests.add(request);
            }
        }
        xlSheet.clearIgnoreColumnCases();
    }

    /**
     *
     * @param request
     * @param isStudent
     */
    public void createExcel(UniversityPerson request, Boolean isStudent) throws XLSerializableException, InvocationTargetException, IllegalAccessException, IOException {
        //Create a xlsx in the temp directory where the information will be placed.
        String path = System.getProperty("java.io.tmpdir");
        path = path.concat("tempReq").concat("\\");
        File newXL = new File(path);
        if(!newXL.exists()){
            newXL.mkdirs();
        }
        path = path.concat(request.getId_una()).concat(".xlsx");
        newXL = new File(path);
        if(!newXL.exists()){
            Files.copy(xlWorkbook.getXlFile().toPath(), newXL.toPath());
        }

        //Opens temp xlsx
        //Verifying is not a must as it is a copy of a file that has already been opened.
        xlWorkbook = new XLWorkbook(path);
        openXL();
        startWorking();

        //Transforms request into row
        XLRow row = xlSheet.getRow(2);
        if(row != null){
            XLRow authorizedRow;
            int processOf;
            if (isStudent) {
                if (request instanceof UnderAgeStudent) {
                    processOf = Processes.UNDER_AGE_STUDENT;
                } else {
                    processOf = Processes.ADULT_STUDENT;
                }
            } else {
                processOf = Processes.EMPLOYEE;
            }
            //If it is an under-age student first the authorized row is created and added to the main row
            XLSerializer<PersonalData> serializer = new XLSerializer<>();
            if (processOf == Processes.UNDER_AGE_STUDENT) {
                authorizedRow = new XLRow(2);
                serializer.typeToRow(authorizedRow, ((UnderAgeStudent) request).getAuthorized(), Processes.AUTHORIZED);
                for (XLCell<?> cell : authorizedRow.asList()) {
                    row.addXlCell(cell);
                }
            }
            serializer.typeToRow(row, request, processOf);
            row.sort();
            //Insert Row in the sheet
            xlSheet.pasteRow(row);
        }
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