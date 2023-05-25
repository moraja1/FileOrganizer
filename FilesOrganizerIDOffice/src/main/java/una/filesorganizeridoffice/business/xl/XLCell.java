package una.filesorganizeridoffice.business.xl;

import una.filesorganizeridoffice.business.xl.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class XLCell<T> {
    private String columnName;
    private Integer rowNumber;
    private T value;
    public XLCell() {
        columnName = "";
        rowNumber = 0;
        value = null;
    }

    /***
     * Creates an XLCell containing the row number, column name and a value of the proper Type.
     * @param cellColumn String
     * @param cellRow Integer
     * @param cellValue Object
     * @return XLCell
     */
    public XLCell(String cellColumn, Integer cellRow, String cellValue) {
        Integer intValue;
        LocalDate dateValue;
        if(isScientificNotation(cellValue)){
            intValue = new BigDecimal(cellValue).intValue();
            this.value = (T) intValue;
        }else if(DateUtil.isDate(cellValue)){
            dateValue = DateUtil.toDate(cellValue);
            this.value = (T) dateValue;
        }else if(cellValue.matches("\\d*") && !cellValue.isEmpty()){
            this.value = (T) Integer.valueOf(cellValue);
        }else this.value = (T) cellValue;
        this.rowNumber = cellRow;
        this.columnName = cellColumn;
    }

    /***
     * Returns the column name
     * @return String
     */
    public String getColumnName() {
        return columnName;
    }

    /***
     * Returns the row number
     * @return Integer
     */
    public Integer getRowNumber() {
        return rowNumber;
    }

    /***
     * Return the cell value
     * @return Type
     */
    public T getValue() {
        return value;
    }

    /***
     * Detects whether a String contains a Scientific Notation Number
     * @param numberString String vale
     * @return Boolean
     */
    private boolean isScientificNotation(String numberString) {

        // Validate number
        try {
            new BigDecimal(numberString);
        } catch (NumberFormatException e) {
            return false;
        }

        // Check for scientific notation
        return numberString.toUpperCase().contains("E") && (numberString.charAt(1)=='.' || numberString.charAt(2)=='.');
    }

    @Override
    public String toString() {
        return "XLCell{" +
                "columnName='" + columnName + '\'' +
                ", rowNumber=" + rowNumber +
                ", value=" + value.toString() +
                '}';
    }
}
