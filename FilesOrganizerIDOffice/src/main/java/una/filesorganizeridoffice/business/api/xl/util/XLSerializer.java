package una.filesorganizeridoffice.business.api.xl.util;

import una.filesorganizeridoffice.business.api.xl.XLCell;
import una.filesorganizeridoffice.business.api.xl.XLRow;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XLSerializer<T> {
    /***
     * This method transforms a XLRow into an object of any type.
     * The object passed has to be annotated correctly in the Class and methods to be serialized.
     * @param row XLRow
     * @param request T
     * @param processOf String
     */
    public void rowToRequest(XLRow row, T request, String processOf) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Verifies if its XLSerializable
        Class<?> clazz = request.getClass();
        if(!clazz.isAnnotationPresent(XLSerializable.class)){
            throw new XLSerializableException();
        }
        for (XLCell cell : row.asList()){
            String cellColumn = cell.getColumnName();
            String processColumn;
            //Look for process column name required
            for (Method m : clazz.getMethods()){
                if (m.isAnnotationPresent(XLCellSetValue.class)){
                    boolean done = false;
                    XLCellSetValue annotationSet = m.getAnnotation(XLCellSetValue.class);
                    for (XLCellColumn annotationCellColumn : annotationSet.value()){
                        if (annotationCellColumn.processOf().equals(processOf)){
                            processColumn = annotationCellColumn.column();
                            if (cellColumn.equals(processColumn)){
                                try{
                                    m.invoke(request, cell.getValue());
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

}
