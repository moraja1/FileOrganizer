package una.filesorganizeridoffice.business.xl.util;

import una.filesorganizeridoffice.business.xl.XLCell;
import una.filesorganizeridoffice.business.xl.XLRow;
import una.filesorganizeridoffice.business.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.model.Adult;
import una.filesorganizeridoffice.model.base.UniversityPerson;

public class XLSerializer {
    /***
     * This method serialize an XLRow to a UniversityPerson
     * @param row XLRow
     * @param isStudent Boolean
     * @return UniversityPerson
     */
    public static UniversityPerson rowToRequest(XLRow row, Boolean isStudent){
        if (isStudent){
            boolean isAdult = false;
            //Verifies if the student is adult
            for (int i = 0; i < row.getCellCount(); i++){
                if(row.getCell(i).getValue() instanceof String) {
                    if(row.getCell(i).getValue().equals("Mayor de edad")){
                        isAdult = true;
                        i = row.getCellCount();
                    } else if (row.getCell(i).getValue().equals("Menor de edad")) {
                        i = row.getCellCount();
                    }
                }
            }
            return serializeStudent(row, isAdult);
        }
        return new Adult();
    }

    public static XLRow requestToRow(UniversityPerson a){
        /*
        EN DESARROLLO
         */
        return new XLRow();
    }

    private static UniversityPerson serializeStudent(XLRow row, boolean isAdult) {
        UniversityPerson student;
        if(isAdult){
            for (int i = 0; i < row.getCellCount(); i++) {
                student = new Adult();
                XLCell<?> cell = row.getCell(i);
                Class<?> stdnt = student.getClass();

                if(stdnt.isAnnotationPresent(XLCellSetValue.class)){
                    System.out.println("Yeahhhh");
                }


            }
        }
        return null;
    }

}
