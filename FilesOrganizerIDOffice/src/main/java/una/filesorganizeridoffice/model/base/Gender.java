package una.filesorganizeridoffice.model.base;

import java.io.Serializable;

public enum Gender {
    Masculine("Masculino"), Feminine("Femenino");

    private final String value;
    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
