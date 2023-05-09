package una.filesorganizeridoffice.business;

import org.junit.jupiter.api.Test;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

class SecurityTest {

    @Test
    void verifyInformation() {
        WindowInfo w = new WindowInfo();
        w.setPdfFileUrl("G:\\Mi unidad\\FORMULARIO SOLICITUD CARNE TARJETA UNA BNCR\\Formulario de Carné-Tarjeta UNA-BNCR (File responses)\\Documento de identificación vigente (en formato .PDF) por ambos lados, SI ES MENOR DE EDAD DEBE VENIR ACOMPAÑADO DE CÉDULA D~E (SI EL ENCARGADO LEGAL NO ES EL PADRE O MADRE, DEBE VENIR UNA DECLARACIÓN JURADA). Tamaño máximo de 10Mb. (File responses)");
        w.setExcelFileUrl("C:\\Users\\N00148095\\Downloads\\Solicitud de Carné-Tarjeta UNA-BNCR Estudiante (respuestas)");
        w.setOutputFileUrl("C:\\Users\\N00148095\\Desktop\\Git\\ProyectsPlatzi");
        w.setPhotoFileUrl("G:\\Mi unidad\\FORMULARIO SOLICITUD CARNE TARJETA UNA BNCR\\Formulario de Carné-Tarjeta UNA-BNCR (File responses)\\FOTOS");
        w.setInitialRow(0);
        w.setFinalRow(2);
        Security.verifyInformation(w, false);
        System.out.println(Security.getErrorList().toString());
    }

    @Test
    void getErrorList() {
    }
}