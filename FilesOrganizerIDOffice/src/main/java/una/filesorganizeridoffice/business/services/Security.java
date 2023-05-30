package una.filesorganizeridoffice.business.services;

import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Security {
    private static final HashMap<String, Protocol> errorList = new HashMap<>();
    private static final String[] EXCEL_NAMES = {"Estudiante", "Funcionarios"};
    private static final String[] ID_EXTENSIONS = {"pdf"};
    private static final String[] PHOTO_EXTENSIONS = {"jpg", "png", "jpeg"};

    /***
     * Provides support for verifying the information sent by user. It returns a Protocol to Refuse or Accept the request.
     * @param info WindowInfo object contains window information given by user.
     * @param isStudent boolean information to verify additional information.
     * @return Protocol.Accepted o Protocol.Refused
     */
    public static Protocol verifyInformation(WindowInfo info, Boolean isStudent) {
        //Verifies pdf files
        verifyDirectoryFiles(info.getPdfFileUrl(), "Cédulas", ID_EXTENSIONS);

        //Verifies photo files
        if(isStudent){
            verifyDirectoryFiles(info.getPhotoFileUrl(), "Fotos", PHOTO_EXTENSIONS);
        }

        //Verifies output directory
        File outDir = new File(info.getOutputFileUrl());
        if(outDir.list().length > 0){
            String outDirPath = info.getOutputFileUrl();
            LocalDateTime dt = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            String dateTime = dt.format(formatter);
            outDirPath = outDirPath.concat("\\").concat("CARPETAS NUEVAS-").concat(dateTime);
            outDir = new File(outDirPath);
            if(!outDir.mkdirs()){
                errorList.put("Directorio de Salida", Protocol.CreateDirError);
            }
            info.setOutputFileUrl(outDirPath);
        }

        //Verifies excel name
        File excel = new File(info.getExcelFileUrl());
        if(isStudent){
            if(!excel.getName().contains(EXCEL_NAMES[0])){
                errorList.put("Excel", Protocol.UrlError);
            }
        }else{
            if(!excel.getName().contains(EXCEL_NAMES[1])){
                errorList.put("Excel", Protocol.UrlError);
            }
        }

        //Verifies error list to accept or refuse the activity
        for (Protocol p : errorList.values()) {
            if (p.equals(Protocol.UrlError) || p.equals(Protocol.CreateDirError) || p.equals(Protocol.FileEmpty) || p.equals(Protocol.OtherFilesError)) {
                return Protocol.Refused;
            }
        }

        return Protocol.Accepted;
    }

    private static void verifyDirectoryFiles(String fileUrl, String dirFileType , String[] fileExtensions){
        //Obtains files' name list
        File directory = new File(fileUrl);
        String[] filenames = directory.list();

        //Verifies if there are files on it
        if(filenames.length > 0){
            //verifies if the files are only in required extensions
            boolean areUnique = true;
            for (String filename : filenames) {
                boolean hasExtension = false;
                for (String extension : fileExtensions) {
                    if (filename.toLowerCase().endsWith(extension)) {
                        hasExtension = true;
                        break;
                    }
                }
                if (!hasExtension){
                    areUnique = false;
                    break;
                }
            }
            if (!areUnique){
                errorList.put(dirFileType, Protocol.OtherFilesError);
            }
        }else{
            errorList.put(dirFileType, Protocol.FileEmpty);
        }
    }

    public static HashMap<String, Protocol> getErrorList() {
        HashMap<String, Protocol> errorsCopy = new HashMap<>();
        errorsCopy.putAll(errorList);
        errorList.clear();
        return errorsCopy;
    }
}
