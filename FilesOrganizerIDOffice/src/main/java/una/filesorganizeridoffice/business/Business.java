package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.services.ExcelManager;
import una.filesorganizeridoffice.business.services.FileManager;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.model.base.UniversityPerson;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/***
 * Final class that contains all the logical process of the system. This class establishes the step by step to
 * automate the process of organizing and creating files needed.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public final class Business {
    /***
     * Contains the message and protocol for exception throwing.
     */
    private HashMap<String, Protocol> errorList = new HashMap<>();
    /***
     * Contains all the processes completed for log writing.
     */
    private final List<String> approvedProcesses = new LinkedList<>();
    /***
     * Excel manager instance, required to process API.xl
     */
    private ExcelManager xlManager;
    /***
     * Requests List of UniversityPerson Class, required to contain the list of requesters of University Licence.
     */
    private List<UniversityPerson> requests = new LinkedList<>();

    /***
     * Base Constructor
     */
    public Business() {
    }
    /***
     * This method leads the organization process. Passing the information from one service to another to find any issue
     * and to organize the files the best way possible.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message to be displayed on screen.
     */
    public boolean startOrganization(WindowInfo info, Boolean isStudent) throws BusinessException {
        try {
            securityProcess(info, isStudent);
            FileManager.prepareFiles(info, isStudent);
            readExcel(info, isStudent);
            for (UniversityPerson request : requests) {
                organizeFiles(request, info, isStudent);
                writeExcel(request, info, isStudent);
            }
        } catch (BusinessException e) {
            //createLog(); Save a registry of the errors and results in a file.
            errorList.clear();
            throw e;
        } catch (XLSerializableException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("No se completó el proceso. Se utilizó un modelo de clase distinta al permitido.");
        }
        createLog();
        return true;
    }

    /***
     * This method executes the Security process and evaluates whether Security accepted it or not.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message created by createException method.
     */
    private void securityProcess(WindowInfo info, Boolean isStudent) throws BusinessException {
        Protocol securityResponse = Security.verifyInformation(info, isStudent);
        switch (securityResponse)
        {
            case Refused:
                errorList = Security.getErrorList();
                createLog();
                createException();
                break;
            case Accepted:
                //Update progress bar
                break;
            default:
                break;
        }
    }

    /***
     * This method uses an ExcelManager service to create the requests list.
     * @param info Window Info
     * @param isStudent Boolean
     * @throws BusinessException
     * @throws XLSerializableException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void readExcel(WindowInfo info, Boolean isStudent) throws BusinessException, XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Si es estudiante la idea sería recibir aqui la lista de estudiantes según los row
        xlManager = new ExcelManager(info.getExcelFileUrl());
        Protocol xlBuilding = xlManager.openXL();
        switch (xlBuilding) {
            case Accepted:
                Protocol xlSheetBuilding = xlManager.startWorking();
                //update progress bar
                switch (xlSheetBuilding) {
                    case Accepted:
                        requests = xlManager.getRequests(info.getInitialRow(), info.getFinalRow(), isStudent);
                        //update progress bar
                        break;
                    case Refused:
                        errorList.put("Lectura de Hoja de Excel de Solicitudes: ", Protocol.Refused);
                        createLog();
                        createException();
                    default:
                        break;
                }
                break;
            case Refused:
                errorList.put("Lectura de Excel de Solicitudes: ", Protocol.Refused);
                createLog();
                createException();
            default:
                break;
        }
    }

    /***
     * This method manages to create a directory for every request as well as move their required files.
     * @param request personal data of the one who is requesting a Licence.
     * @param info inserted in the fields of the window.
     * @param isStudent indicates if the person is student or not
     */
    private void organizeFiles(UniversityPerson request, WindowInfo info, boolean isStudent) throws BusinessException {
        //Creates directory absolute path
        String dirName = "".concat(request.getName()).concat(" ").concat(request.getMiddleName()).concat(" ").
                concat(request.getLastName()).concat("-").concat(request.getId_una());
        String dirAbsolutePath = info.getOutputFileUrl().concat("\\").concat(dirName);
        File dir = new File(dirAbsolutePath);

        //Creates directory if necessary
        boolean dirExists = dir.exists();
        if(!dirExists){
            if(!dir.mkdir()){
                //Save errors for future logger.
                //Error are not process, just skipped.
                errorList.put(dirName, Protocol.CreateDirError);
            }else dirExists = true;
        }

        //Move required files if possible
        try{
            if(isStudent){
                //Move photo if its student
                if(moveFile(request.getId_una(), dir, info.getPhotoFileUrl())){
                    String approvedMessage = request.getName() + " " + request.getMiddleName() + " " + request.getLastName() +
                            ": " + "Se movió correctamente el archivo " + request.getId_una().concat(".jpg");
                    approvedProcesses.add(approvedMessage);
                }
            }
            //Move pdf for any request
            if(moveFile(request.getId_una(), dir, info.getPdfFileUrl())){
                String approvedMessage = request.getName() + " " + request.getMiddleName() + " " + request.getLastName() +
                        ": " + "Se movió correctamente el archivo " + request.getId_una().concat(".pdf");
                approvedProcesses.add(approvedMessage);
            }
        }catch (IOException e){
            errorList.put(dirName, Protocol.MoveFileError);
        }
    }

    /***
     * Move a file to a new directory only if the new directory exists and the filename is in the fileToMoveDir.
     * @param fileName information of the one who requests a licence.
     * @param fileNewDirectory file of the new directory that will contain the organized files.
     * @param fileToMoveDir the url of the directory where the file is located.
     * @throws IOException
     */
    private boolean moveFile(String fileName, File fileNewDirectory, String fileToMoveDir) throws IOException {
        if(fileNewDirectory.exists()){
            File fileDirectory = new File(fileToMoveDir);
            File[] entries = fileDirectory.listFiles();
            for (File entry : entries){
                if(entry.getName().startsWith(fileName)){
                    String newPath = fileNewDirectory.getAbsolutePath();
                    newPath = newPath.concat("\\").concat(entry.getName());
                    Files.move(entry.toPath(), Paths.get(newPath));
                    return true;
                }
            }
        }
        return false;
    }

    private void writeExcel(UniversityPerson request, WindowInfo info, Boolean isStudent) {

    }

    /***
     * This method creates the Error Message that will be displayed on the screen.
     * @throws BusinessException with the message to be displayed on screen.
     */
    private void createException() throws BusinessException {
        String errorMessage = "Los siguientes errores impiden continuar: \n";
        for (String k : errorList.keySet()){
            Protocol p = errorList.get(k);
            errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
        }
        throw new BusinessException(errorMessage);
    }

    private void createLog() {
        /*
        EXAMPLE LOG, DATE, HOUR, ROWS REMAIN TO BE ADDED TO THE MESSAGE,
        STILL NEED TO BE WROTE IN A TXT FILE.
         */
        Thread log = new Thread(new Runnable() {
            @Override
            public void run() {
                String approvedMessage = "=======================================================================================\n" +
                        "Las siguientes gestiones se completaron correctamente: \n";
                for (String message : approvedProcesses){
                    approvedMessage = approvedMessage.concat(message).concat("\n");
                }

                System.out.println(approvedMessage);

                if(!errorList.isEmpty()){
                    String errorMessage = "=======================================================================================\n"
                            + "Los siguientes errores ocurrieron: \n";
                    for (String k : errorList.keySet()){
                        Protocol p = errorList.get(k);
                        errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
                    }
                    System.out.println(errorMessage);
                }
                System.out.println("====================================PROGRAM STOPED=====================================");
                approvedProcesses.clear();
                errorList.clear();
            }
        });
        log.start();
    }
}