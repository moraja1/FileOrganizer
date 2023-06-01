package una.filesorganizeridoffice.business.services;

import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.business.util.Tools;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * A class that performs some verifications before proceeding with the business operations. This class is extensible
 * and should be improved to protect the environment from any attack.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public class Security {
    /**
     * An array of keywords that has to do exist in the Excel filename in order to be taken as correct.
     */
    private static final String[] EXCEL_NAMES = {"Estudiante", "Funcionarios"};
    /**
     * An array of extensions allowed for ID documents.
     */
    private static final String[] ID_EXTENSIONS = {"pdf"};
    /**
     * An array for extensions allowed for photo documents.
     */
    private static final String[] PHOTO_EXTENSIONS = {"jpg", "png", "jpeg"};

    /**
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
                Tools.errorList.put("Directorio de Salida", Protocol.CreateDirError);
            }
            info.setOutputFileUrl(outDirPath);
        }

        //Verifies excel name
        File excel = new File(info.getExcelFileUrl());
        if(excel.getName().endsWith("xlsx")){
            if(isStudent){
                if(!excel.getName().contains(EXCEL_NAMES[0])){
                    Tools.errorList.put("Excel", Protocol.UrlError);
                }
            }else{
                if(!excel.getName().contains(EXCEL_NAMES[1])){
                    Tools.errorList.put("Excel", Protocol.UrlError);
                }
            }
        }else{
            Tools.errorList.put("Excel", Protocol.UrlError);
        }

        //Verifies error list to accept or refuse the activity
        for (Protocol p : Tools.errorList.values()) {
            if (p.equals(Protocol.UrlError) || p.equals(Protocol.CreateDirError) || p.equals(Protocol.FileEmpty) || p.equals(Protocol.OtherFilesError)) {
                return Protocol.Refused;
            }
        }

        return Protocol.Accepted;
    }

    /**
     * This method verifies if a directory contains only the allowed file extensions, if there is at least one
     * file with a different extension an error will be placed on list.
     * @param fileUrl directory to verify
     * @param dirFileType the type of the files that should be in the directory. Example: Pdf, images, txt, etc.
     * @param fileExtensions the only extensions authorized to be in the directory.
     */
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
                Tools.errorList.put(dirFileType, Protocol.OtherFilesError);
            }
        }else{
            Tools.errorList.put(dirFileType, Protocol.FileEmpty);
        }
    }
}
