package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import static org.junit.jupiter.api.Assertions.*;
class BusinessTest {
    //Complete test
    public static void main(String[] args) {
        Business b = new Business();
        WindowInfo w = new WindowInfo();
        w.setPdfFileUrl("G:\\Mi unidad\\FORMULARIO SOLICITUD CARNE TARJETA UNA BNCR\\Formulario de Carné-Tarjeta UNA-BNCR (File responses)\\Documento de identificación vigente (en formato .PDF) por ambos lados, SI ES MENOR DE EDAD DEBE VENIR ACOMPAÑADO DE CÉDULA D~E (SI EL ENCARGADO LEGAL NO ES EL PADRE O MADRE, DEBE VENIR UNA DECLARACIÓN JURADA). Tamaño máximo de 10Mb. (File responses)");
        w.setPhotoFileUrl("G:\\Mi unidad\\FORMULARIO SOLICITUD CARNE TARJETA UNA BNCR\\Formulario de Carné-Tarjeta UNA-BNCR (File responses)\\FOTOS");
        w.setExcelFileUrl("C:\\Users\\N00148095\\Downloads\\Solicitud de Carné-Tarjeta UNA-BNCR Estudiante (respuestas).xlsx");
        w.setInitialRow(128);
        w.setFinalRow(129);
        w.setOutputFileUrl("G:\\Unidades compartidas\\Proceso de Carné\\4. CARPETAS NUEVAS");
        try {
            b.startOrganization(w, true);
        } catch (BusinessException e) {
            e.printStackTrace(System.out);
        }
    }
}