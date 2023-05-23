package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

class BusinessTest {
    public static void main(String[] args) {
        Business b = new Business();
        WindowInfo w = new WindowInfo();
        w.setExcelFileUrl("C:\\Users\\N00148095\\Downloads\\Solicitud de Carné-Tarjeta UNA-BNCR Estudiante (respuestas).xlsx");
        w.setInitialRow(2);
        w.setFinalRow(5);
        try {
            b.readExcel(w, true);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }
}