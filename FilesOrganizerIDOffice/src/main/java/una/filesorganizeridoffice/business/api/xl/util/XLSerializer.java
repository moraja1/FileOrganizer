package una.filesorganizeridoffice.business.api.xl.util;

import una.filesorganizeridoffice.business.api.xl.XLCell;
import una.filesorganizeridoffice.business.api.xl.XLRow;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellGetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A generic implementation to serialize and de-serialize any Type who is annotated as XLSerializable.
 * @param <T> any Type class who has been annotated as XLSerializable.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public class XLSerializer<T> {
    /***
     * This method transforms a XLRow into an object of any type.
     * The object passed has to be annotated correctly in the Class and methods to be serialized.
     * @param row XLRow
     * @param obj T
     * @param processOf String
     */
    public void rowToType(XLRow row, T obj, Integer processOf) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Verifies if its XLSerializable
        Class<?> paper = obj.getClass();
        if(!paper.isAnnotationPresent(XLSerializable.class)){
            throw new XLSerializableException();
        }

        for (XLCell cell : row.asList()){
            String cellColumn = cell.getColumnName();
            String processColumn;
            //Look for process column name for each cell in each method of the XLSerializable class.
            for (Method m : paper.getMethods()){
                if (m.isAnnotationPresent(XLCellSetValue.class)){
                    boolean done = false;
                    XLCellSetValue annotationSet = m.getAnnotation(XLCellSetValue.class);
                    for (XLCellColumn annotationCellColumn : annotationSet.value()){
                        if (annotationCellColumn.processOf() == processOf){
                            processColumn = annotationCellColumn.column();
                            if (cellColumn.equals(processColumn)){
                                try{
                                    m.invoke(obj, cell.getValue());
                                }catch (IllegalArgumentException e){
                                    throw new XLSerializableException("No se puede pasar un " + cell.getValue().getClass() + " a " + m.getParameterTypes()[0]
                                            + " en el metodo " + m.getName());
                                }
                                done = true;
                            }
                        }
                    }
                    if (done){
                        break;
                    }
                }
            }
        }
    }

    public void typeToRow(XLRow row, T obj, Integer processOf) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Verifies Serializable class
        Class<?> paper = obj.getClass();
        if(!paper.isAnnotationPresent(XLSerializable.class)){
            throw new XLSerializableException();
        }

        for (Method m : paper.getMethods()){
            if (m.isAnnotationPresent(XLCellGetValue.class)){
                XLCellGetValue annotationGet = m.getAnnotation(XLCellGetValue.class);
                for (XLCellColumn annotationCellColumn : annotationGet.value()){
                    if (annotationCellColumn.processOf() == processOf){
                        String cellColumn = annotationCellColumn.column();
                        Object value = m.invoke(obj);
                        if(value == null){
                            value = 0;
                        }
                        row.addXlCell(new XLCell<>(cellColumn, row.getRowNum(), value));
                    }
                }
            }
        }
    }

    private void verifiesSerializable(Class<?> paper) throws XLSerializableException {

    }
}
