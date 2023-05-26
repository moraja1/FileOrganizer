package una.filesorganizeridoffice.model.base;

import java.io.Serializable;

public enum IdentificationType {
    Cedula("Cédula de Identidad"),
    DIMEX("DIMEX"),
    Passport("Pasaporte"),
    DIDI("DIDI");

    private final String value;
    IdentificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
