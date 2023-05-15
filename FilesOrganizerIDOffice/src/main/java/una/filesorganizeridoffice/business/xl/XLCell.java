package una.filesorganizeridoffice.business.xl;

public class XLCell<T> {
    private String columnName;
    private Integer rowNumber;
    private T value;
    public XLCell() {
        columnName = "";
        rowNumber = 0;
        value = null;
    }
    public XLCell(String columnName, Integer rowNumber, T value) {
        this.columnName = columnName;
        this.rowNumber = rowNumber;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public T getValue() {
        return value;
    }
}
