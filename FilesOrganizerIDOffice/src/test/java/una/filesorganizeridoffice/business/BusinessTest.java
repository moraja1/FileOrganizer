package una.filesorganizeridoffice.business;

import org.junit.jupiter.api.Test;
import una.filesorganizeridoffice.business.exceptions.ExceptionBusiness;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {
    public static void main(String[] args) {
        Business b = new Business();
        WindowInfo w = new WindowInfo();
        w.setExcelFileUrl("C:\\Users\\jeiso\\Downloads\\Solicitud de Carné-Tarjeta UNA-BNCR Estudiante (respuestas).xlsx");
        try {
            b.readExcel(w, true);
        } catch (ExceptionBusiness e) {
            throw new RuntimeException(e);
        }
    }
}