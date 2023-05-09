package una.filesorganizeridoffice.business.services;

import una.filesorganizeridoffice.business.Protocol;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;

public class FilePreparer {
    public FilePreparer() {
    }

    public void prepareFiles(WindowInfo info, Boolean isStudent) {
        //Correct pdf names if necessary
        correctFileNames(info.getPdfFileUrl(), "pdf");

        //Correct photo names if necessary
        if(isStudent){
            correctFileNames(info.getPhotoFileUrl(), "jpg");
        }
    }

    public void correctFileNames(String dirUrl, String extension) {
        File file = new File(dirUrl);

        File[] children = file.listFiles();

        if(children != null){
            for (File child : children) {
                String fileName = child.getName();
                fileName = fileName.replaceAll("\\s+", "");
                fileName = fileName.toLowerCase();
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
}
