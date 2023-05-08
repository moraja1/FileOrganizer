package una.filesorganizeridoffice.viewmodel;

import javafx.beans.property.*;
import javafx.util.StringConverter;

public class WindowInfo {
    private StringProperty pdfFileURL = new SimpleStringProperty("");
    private StringProperty photoFileURL = new SimpleStringProperty("");
    private StringProperty excelFileURL = new SimpleStringProperty("");
    private StringProperty outputFileURL = new SimpleStringProperty("");
    private IntegerProperty initialRow = new SimpleIntegerProperty(0);
    private IntegerProperty finalRow = new SimpleIntegerProperty(0);
    private StringConverter<Number> converter;

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

    public void setPdfFileURL(String pdfFileURL) {
        this.pdfFileURL.set(pdfFileURL);
    }

    public void setPhotoFileURL(String photoFileURL) {
        this.photoFileURL.set(photoFileURL);
    }

    public void setExcelFileURL(String excelFileURL) {
        this.excelFileURL.set(excelFileURL);
    }

    public void setOutputFileURL(String outputFileURL) {
        this.outputFileURL.set(outputFileURL);
    }

    public void setInitialRow(int initialRow) {
        this.initialRow.set(initialRow);
    }

    public void setFinalRow(int finalRow) {
        this.finalRow.set(finalRow);
    }

    public String getPdfFileURL() {
        return pdfFileURL.get();
    }

    public StringProperty pdfFileURLProperty() {
        return pdfFileURL;
    }

    public String getPhotoFileURL() {
        return photoFileURL.get();
    }

    public StringProperty photoFileURLProperty() {
        return photoFileURL;
    }

    public String getExcelFileURL() {
        return excelFileURL.get();
    }

    public StringProperty excelFileURLProperty() {
        return excelFileURL;
    }

    public String getOutputFileURL() {
        return outputFileURL.get();
    }

    public StringProperty outputFileURLProperty() {
        return outputFileURL;
    }

    public int getInitialRow() {
        return initialRow.get();
    }

    public IntegerProperty initialRowProperty() {
        return initialRow;
    }

    public int getFinalRow() {
        return finalRow.get();
    }

    public IntegerProperty finalRowProperty() {
        return finalRow;
    }
    public StringConverter<Number> getConverter() {
        return converter;
    }
}
