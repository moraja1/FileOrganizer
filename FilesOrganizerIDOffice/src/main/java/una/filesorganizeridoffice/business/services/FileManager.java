package una.filesorganizeridoffice.business.services;

import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.util.Tools;
import una.filesorganizeridoffice.model.base.UniversityPerson;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A file manager service who encapsulate all methods related to file handle.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public final class FileManager {

    /***
     * Removes any space on the name of the files in a directory and changes the extension if needed.
     * @param info Window Info
     * @param isStudent Boolean
     */
    public static void prepareFiles(WindowInfo info, Boolean isStudent) {
        //Correct pdf names if necessary
        correctFileNames(info.getPdfFileUrl(), "pdf");

        //Correct photo names if necessary
        if(isStudent){
            correctFileNames(info.getPhotoFileUrl(), "jpg");
        }
    }

    /***
     * Remove any space on the name of the files in the directory indicated, as well as changes their extension to the
     * one indicated by parameter.
     * Note: This method changes the extension of the files of a directory. This could damage an important file.
     * @param dirUrl the directory url that contains the files to be corrected
     * @param extension the new extension of the files
     */
    private static void correctFileNames(String dirUrl, String extension) {
        File file = new File(dirUrl);

        File[] children = file.listFiles();

        if(children != null){
            for (File child : children) {
                String fileName = child.getName();
                fileName = fileName.replaceAll("\\s+", "");
                fileName = fileName.toUpperCase();
                if (!fileName.endsWith(extension)) {
                    int indexExtension = fileName.lastIndexOf('.');
                    fileName = fileName.substring(0, indexExtension + 1);
                    fileName = fileName.concat(extension);
                }
                String parent = child.getParentFile().getAbsolutePath();
                parent = parent.concat("\\").concat(fileName);
                child.renameTo(new File(parent));
            }
        }
    }

    /***
     * This method manages to create a directory for every request as well as move their required files. If it can not
     * create a directory for the request it will not do anything but saving the error. If it can not move a required file,
     * it will continue to try to move the next one but saving the error in the list.
     * @param request personal data of the one who is requesting a Licence.
     * @param info inserted in the fields of the window.
     * @param isStudent indicates if the person is student or not
     */
    public static void organizeFiles(UniversityPerson request, WindowInfo info, boolean isStudent) throws BusinessException {
        //Creates directory name
        String dirName = "".concat(request.getName()).concat(" ").concat(request.getMiddleName()).concat(" ").
                concat(request.getLastName()).concat("-").concat(request.getId_una());

        File dir = createDirectory(dirName, info.getOutputFileUrl());

        //Move files if possible
        if(dir.exists()) {
            //Move required files if possible
            try {
                if (isStudent) {
                    //Move photo if its student
                    if (moveFile(request.getId_una(), dir, info.getPhotoFileUrl())) {
                        String approvedMessage = request.getName() + " " + request.getMiddleName() + " " + request.getLastName() +
                                ": " + "Se movió correctamente el archivo " + request.getId_una().concat(".jpg");
                        Tools.approvedProcesses.add(approvedMessage);
                    }
                }
                //Move pdf for any request
                if (moveFile(request.getId_una(), dir, info.getPdfFileUrl())) {
                    String approvedMessage = request.getName() + " " + request.getMiddleName() + " " + request.getLastName() +
                            ": " + "Se movió correctamente el archivo " + request.getId_una().concat(".pdf");
                    Tools.approvedProcesses.add(approvedMessage);
                }
                //Move xlsx files
                String tempDir = System.getProperty("java.io.tmpdir");
                tempDir = tempDir.concat("tempReq").concat("\\");
                if(moveFile(request.getId_una(), dir, tempDir)){
                    String approvedMessage = request.getName() + " " + request.getMiddleName() + " " + request.getLastName() +
                            ": " + "Se movió correctamente el archivo " + request.getId_una().concat(".xlsx");
                    Tools.approvedProcesses.add(approvedMessage);
                }
            } catch (IOException e) {
                Tools.errorList.put(dir.getName(), Protocol.MoveFileError);
            }
        }
    }
    private static File createDirectory(String name, String parent){
        //Creates directory absolute path
        String dirAbsolutePath = parent.concat("\\").concat(name);

        //Creates directory if necessary
        File dir = new File(dirAbsolutePath);
        if(!dir.exists()){
            if(!dir.mkdir()){
                //Save errors for future logger.
                //Error are not process, just skipped.
                Tools.errorList.put(name, Protocol.CreateDirError);
            }
        }
        return dir;
    }
    private static boolean moveFile(String fileName, File newFileParent, String currentFileParent) throws IOException {
        if(newFileParent.exists()){
            File fileDirectory = new File(currentFileParent);
            File[] entries = fileDirectory.listFiles();
            for (File entry : entries){
                if(entry.getName().startsWith(fileName)){
                    String newPath = newFileParent.getAbsolutePath();
                    newPath = newPath.concat("\\").concat(entry.getName());
                    Files.move(entry.toPath(), Paths.get(newPath));
                    return true;
                }
            }
        }
        return false;
    }
}
