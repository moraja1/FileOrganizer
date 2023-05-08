package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Security {
    private static List<String> errorList = new ArrayList<>();
    public static Protocol verifyInformation(WindowInfo info, Boolean isStudent) {
        verifyDirectoryContent(info.getPdfFileURL(), "cédulas", new String[]{"pdf"});
        verifyDirectoryContent(info.getPhotoFileURL(), "fotos", new String[]{"jpg", "png", "jpeg"});



        return Protocol.excelURLWrong;
    }

    private static void verifyDirectoryContent(String fileUrl, String dirFileType , String[] fileExtensions){
        //Obtains files' name list
        File directory = new File(fileUrl);
        String[] filenames = directory.list();

        //Verifies if there are files on it
        if(filenames.length > 0){
            //verifies if the files are only in required extensions
            Boolean areUnique = true;
            for (String filename : filenames) {
                boolean hasExtension = false;
                for (String extension : fileExtensions){
                    if (filename.toLowerCase().endsWith(extension)){
                        hasExtension = true;
                    }
                }
                if (!hasExtension){
                    areUnique = false;
                    break;
                }
            }
            if (!areUnique){
                errorList.add("El directorio de " + dirFileType + " tiene otro tipo de archivos.");
            }
        }else{
            errorList.add("El directorio de " + dirFileType + " no tiene archivos.");
        }
    }

    public static List<String> getErrorList() {
        List<String> errorsCopy = List.copyOf(errorList);
        errorList.clear();
        return errorsCopy;
    }
}
