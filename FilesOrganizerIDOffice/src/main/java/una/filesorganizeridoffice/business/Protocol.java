package una.filesorganizeridoffice.business;

public enum Protocol {
    Accepted(""),
    Refused(""),
    UrlError("Error en la ubicación o nombre del archivo.\n"),
    FileNotFound("No se pudo encontrar el archivo.\n"),
    CreateDirError("Error al crear el directorio.\n"),
    FileEmpty("La carpeta no tiene archivos.\n"),
    OtherFilesError("Existen archivos de otros tipos en la carpeta.\n");
    private String message;
    Protocol(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
