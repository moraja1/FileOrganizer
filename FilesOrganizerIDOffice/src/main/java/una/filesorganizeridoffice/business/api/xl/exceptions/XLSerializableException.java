package una.filesorganizeridoffice.business.api.xl.exceptions;

public class XLSerializableException extends Exception{
    public XLSerializableException() {
        super("Object is not XLSerializable, process is not allowed.");
    }
    public XLSerializableException(String message) {
        super(message);
    }
}
