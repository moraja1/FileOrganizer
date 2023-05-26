package una.filesorganizeridoffice.viewmodel;

import javafx.beans.property.*;
import javafx.util.StringConverter;

public class WindowInfo {
    private final StringProperty pdfFileUrl = new SimpleStringProperty("");
    private final StringProperty photoFileUrl = new SimpleStringProperty("");
    private final StringProperty excelFileUrl = new SimpleStringProperty("");
    private final StringProperty outputFileUrl = new SimpleStringProperty("");
    private final IntegerProperty initialRow = new SimpleIntegerProperty(0);
    private final IntegerProperty finalRow = new SimpleIntegerProperty(0);
    private final StringConverter<Number> converter;

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

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl.set(pdfFileUrl);
    }

    public void setPhotoFileUrl(String photoFileUrl) {
        this.photoFileUrl.set(photoFileUrl);
    }

    public void setExcelFileUrl(String excelFileUrl) {
        this.excelFileUrl.set(excelFileUrl);
    }

    public void setOutputFileUrl(String outputFileUrl) {
        this.outputFileUrl.set(outputFileUrl);
    }

    public void setInitialRow(int initialRow) {
        this.initialRow.set(initialRow);
    }

    public void setFinalRow(int finalRow) {
        this.finalRow.set(finalRow);
    }

    public String getPdfFileUrl() {
        return pdfFileUrl.get();
    }

    public StringProperty pdfFileUrlProperty() {
        return pdfFileUrl;
    }

    public String getPhotoFileUrl() {
        return photoFileUrl.get();
    }

    public StringProperty photoFileUrlProperty() {
        return photoFileUrl;
    }

    public String getExcelFileUrl() {
        return excelFileUrl.get();
    }

    public StringProperty excelFileUrlProperty() {
        return excelFileUrl;
    }

    public String getOutputFileUrl() {
        return outputFileUrl.get();
    }

    public StringProperty outputFileUrlProperty() {
        return outputFileUrl;
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
