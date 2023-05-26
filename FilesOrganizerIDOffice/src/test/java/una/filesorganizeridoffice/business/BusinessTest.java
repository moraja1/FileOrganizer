package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.lang.reflect.InvocationTargetException;

class BusinessTest {
    public static void main(String[] args) {
        Business b = new Business();
        WindowInfo w = new WindowInfo();
        w.setExcelFileUrl("C:\\Users\\jeiso\\Downloads\\Solicitud de Carné-Tarjeta UNA-BNCR (respuestas).xlsx");
        w.setInitialRow(3);
        w.setFinalRow(5);
        try {
            b.readExcel(w, true);
        } catch (BusinessException | XLSerializableException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}