package una.filesorganizeridoffice.business;

import org.junit.jupiter.api.Test;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {

    @Test
    void verifyInformation() {
        WindowInfo w = new WindowInfo();
        w.setPdfFileURL("G:\\Mi unidad\\FORMULARIO SOLICITUD CARNE TARJETA UNA BNCR\\Formulario de Carné-Tarjeta UNA-BNCR (File responses)\\Documento de identificación vigente (en formato .PDF) por ambos lados, SI ES MENOR DE EDAD DEBE VENIR ACOMPAÑADO DE CÉDULA D~E (SI EL ENCARGADO LEGAL NO ES EL PADRE O MADRE, DEBE VENIR UNA DECLARACIÓN JURADA). Tamaño máximo de 10Mb. (File responses)");
        w.setExcelFileURL("C:\\Users\\N00148095\\Downloads\\INFORME DE TELETRABAJO DE MARZO 2023-Jaison Mora Víquez");
        w.setOutputFileURL("C:\\Users\\N00148095");
        w.setPhotoFileURL("G:\\Mi unidad\\FORMULARIO SOLICITUD CARNE TARJETA UNA BNCR\\Formulario de Carné-Tarjeta UNA-BNCR (File responses)\\FOTOS");
        w.setInitialRow(0);
        w.setFinalRow(2);
        Security.verifyInformation(w, true);
        System.out.println(Security.getErrorList().toString());
    }

    @Test
    void getErrorList() {
    }
}