package una.filesorganizeridoffice.business;

/**
 * Enumeration of cases reported to business from the services.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public enum Protocol {
    Accepted(""),
    Refused("Gestión rechazada.\n"),
    UrlError("Error en la ubicación o nombre del archivo.\n"),
    CreateDirError("Error al crear el directorio.\n"),
    FileEmpty("La carpeta no tiene archivos.\n"),
    OtherFilesError("Existen archivos de otros tipos en la carpeta.\n"),
    MoveFileError("No se pudo mover un documento.\n");
    private final String message;
    Protocol(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
