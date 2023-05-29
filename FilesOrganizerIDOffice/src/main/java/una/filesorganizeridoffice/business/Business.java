package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.services.ExcelManager;
import una.filesorganizeridoffice.business.services.FilePreparer;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.model.base.UniversityPerson;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class Business {
    private HashMap<String, Protocol> errorList = new HashMap<>();
    private final List<String> approvedProcesses = new LinkedList<>();
    private ExcelManager xlManager;
    private List<UniversityPerson> requests = new ArrayList<>();
    public Business() {
    }
    /***
     * This method leads the organization process. Passing the information from one service to another to find any issue
     * and to organize the files the best way possible.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message to be displayed on screen.
     */
    public void startOrganization(WindowInfo info, Boolean isStudent) throws BusinessException {
        try {
            securityProcess(info, isStudent);
            FilePreparer.prepareFiles(info, isStudent);
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
        approvedProcesses.clear();
        errorList.clear();
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
     * @param info
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
                moveFile(request, dir, info.getPhotoFileUrl());
            }
            //Move pdf for any request
            moveFile(request, dir, info.getPdfFileUrl());
        }catch (IOException e){
            errorList.put(dirName, Protocol.MoveFileError);
        }
    }

    private void moveFile(UniversityPerson request, File fileNewDirectory, String fileToMoveUrl) throws IOException {
        if(fileNewDirectory.exists()){
            File fileDirectory = new File(fileToMoveUrl);
            File[] entries = fileDirectory.listFiles();
            for (File entry : entries){
                if(entry.getName().startsWith(request.getId_una())){
                    String newPath = fileNewDirectory.getAbsolutePath();
                    newPath = newPath.concat("\\").concat(entry.getName());
                    Files.copy(entry.toPath(), Paths.get(newPath));

                    String approvedMessage = request.getName() + " " + request.getMiddleName() + " " + request.getLastName() +
                            ": " + "Se aprueba movió correctamente el archivo " + entry.getName();
                    approvedProcesses.add(approvedMessage);
                }
            }
        }
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
        Thread log = new Thread(new Runnable() {
            @Override
            public void run() {
                String approvedMessage = "=============================================================================\n" +
                        "Las siguientes gestiones se completaron correctamente: \n";
                for (String message : approvedProcesses){
                    approvedMessage = approvedMessage.concat(message).concat("\n");
                }

                System.out.println(approvedMessage);
                System.out.println("\n\n");

                String errorMessage = "=============================================================================\n"
                        + "Los siguientes errores ocurrieron: \n";
                for (String k : errorList.keySet()){
                    Protocol p = errorList.get(k);
                    errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
                }
                errorMessage = errorMessage.concat("==================================PROGRAM STOPED==================================");

                System.out.println(errorMessage);
            }
        });
        log.start();
    }
}