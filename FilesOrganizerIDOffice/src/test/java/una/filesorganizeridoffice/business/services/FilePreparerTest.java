package una.filesorganizeridoffice.business.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilePreparerTest {

    @Test
    void prepareFiles() {
        FilePreparer fp = new FilePreparer();
        fp.correctFileNames("C:\\Users\\N00148095\\Documents\\prueba", "pdf");
    }
}