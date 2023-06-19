package una.filesorganizeridoffice.viewmodel;

import javafx.beans.property.*;
import javafx.util.StringConverter;

/***
 * Class as ViewModel of the window. This class will observe any change made on the window by the user and will
 * storage the window fields values on its properties. This class contains a converter from String to Number that can
 * be used to bind an IntegerProperty to a TextField configured for numbers only.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public final class WindowInfo {
    private final StringProperty pdfFileUrl = new SimpleStringProperty("");
    private final StringProperty photoFileUrl = new SimpleStringProperty("");
    private final StringProperty excelFileUrl = new SimpleStringProperty("");
    private final StringProperty outputFileUrl = new SimpleStringProperty("");
    private final IntegerProperty initialRow = new SimpleIntegerProperty(0);
    private final IntegerProperty finalRow = new SimpleIntegerProperty(0);
    private final StringConverter<Number> converter;

    /***
     * Constructor of the object.
     */
    public WindowInfo() {
        converter = new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                if ((number == null) || (number.intValue() == 0)) {
                    return "";
                }
                return number.toString();
            }
            @Override
            public Number fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
                return Long.valueOf(s);
            }
        };
    }

    /***
     * Setter
     * @param pdfFileUrl value to be set
     */
    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl.set(pdfFileUrl);
    }

    /***
     * Setter
     * @param photoFileUrl value to be set
     */
    public void setPhotoFileUrl(String photoFileUrl) {
        this.photoFileUrl.set(photoFileUrl);
    }

    /***
     * Setter
     * @param excelFileUrl value to be set
     */
    public void setExcelFileUrl(String excelFileUrl) {
        this.excelFileUrl.set(excelFileUrl);
    }

    /***
     * Setter
     * @param outputFileUrl value to be set
     */
    public void setOutputFileUrl(String outputFileUrl) {
        this.outputFileUrl.set(outputFileUrl);
    }

    /***
     * Setter
     * @param initialRow value to be set
     */
    public void setInitialRow(int initialRow) {
        this.initialRow.set(initialRow);
    }

    /***
     * Setter
     * @param finalRow value to be set
     */
    public void setFinalRow(int finalRow) {
        this.finalRow.set(finalRow);
    }

    /***
     * Getter
     * @return StringProperty value
     */
    public String getPdfFileUrl() {
        return pdfFileUrl.get();
    }

    /***
     * Getter
     * @return pdfFileUrlProperty
     */
    public StringProperty pdfFileUrlProperty() {
        return pdfFileUrl;
    }

    /***
     * Getter
     * @return StringProperty value
     */
    public String getPhotoFileUrl() {
        return photoFileUrl.get();
    }

    /***
     * Getter
     * @return photoFileUrlProperty
     */
    public StringProperty photoFileUrlProperty() {
        return photoFileUrl;
    }

    /***
     * Getter
     * @return StringProperty value
     */
    public String getExcelFileUrl() {
        return excelFileUrl.get();
    }

    /***
     * Getter
     * @return excelFileUrlProperty
     */
    public StringProperty excelFileUrlProperty() {
        return excelFileUrl;
    }

    /***
     * Getter
     * @return StringProperty value
     */
    public String getOutputFileUrl() {
        return outputFileUrl.get();
    }

    /***
     * Getter
     * @return outputFileUrlProperty
     */
    public StringProperty outputFileUrlProperty() {
        return outputFileUrl;
    }

    /***
     * Getter
     * @return IntegerProperty value
     */
    public int getInitialRow() {
        return initialRow.get();
    }

    /***
     * Getter
     * @return initialRowProperty
     */
    public IntegerProperty initialRowProperty() {
        return initialRow;
    }

    /***
     * Getter
     * @return IntegerProperty value
     */
    public int getFinalRow() {
        return finalRow.get();
    }

    /***
     * Getter
     * @return finalRowProperty
     */
    public IntegerProperty finalRowProperty() {
        return finalRow;
    }

    /***
     * Getter
     * @return converter. Utility to bind bidirectional StringProperty to IntegerProperty
     */
    public StringConverter<Number> getConverter() {
        return converter;
    }
}
