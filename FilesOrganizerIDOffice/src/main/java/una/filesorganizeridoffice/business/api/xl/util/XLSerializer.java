package una.filesorganizeridoffice.business.api.xl.util;

import org.jetbrains.annotations.NotNull;
import una.filesorganizeridoffice.business.api.xl.XLCell;
import una.filesorganizeridoffice.business.api.xl.XLRow;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellGetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;

import java.lang.annotation.Annotation;
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
    public void rowToType(XLRow row, @NotNull T obj, int processOf) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Verifies if its XLSerializable
        Class<?> paper = verifiesSerializable(obj);

        for (XLCell cell : row.asList()){
            Method m = null;
            try {
                m = getRequiredMethod(cell, paper, XLCellSetValue.class, processOf);
            } catch (NoSuchMethodException e) {
                e.printStackTrace(System.out);
            }
            if(m != null){
                try{
                    m.invoke(obj, cell.getValue());
                }catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("You are passing a " + cell.getValue().getClass() +
                            " as a parameter that should be " + m.getParameterTypes()[0]
                            + " int the method " + m.getName() + ". Please check your model "
                            + "or the XLCell valueType and try again.");
                }
            }
        }
    }

    public void typeToRow(XLRow row, T obj, int processOf) throws XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Verifies if its XLSerializable
        Class<?> paper = verifiesSerializable(obj);

        for (XLCell cell : row.asList()){
            Method m = null;
            try {
                m = getRequiredMethod(cell, paper, XLCellGetValue.class, processOf);
            } catch (NoSuchMethodException e) {
                e.printStackTrace(System.out);
            }
            if(m != null){
                try{
                    cell.setValue(m.invoke(obj));
                }catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("You are passing a " + cell.getValue().getClass() +
                            " as a parameter that should be " + m.getParameterTypes()[0]
                            + " int the method " + m.getName() + ". Please check your model "
                            + "or the XLCell valueType and try again.");
                }
            }
        }
    }

    private Method getRequiredMethod(XLCell cell, Class<?> paper, Class<? extends Annotation> annotation, int processOf)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String cellColumn = cell.getColumnName();
        String processColumn;
        //Look for process column name for each cell in each method of the XLSerializable class.
        for (Method m : paper.getMethods()){
            if (m.isAnnotationPresent(annotation)){
                var annotationSet = m.getAnnotation(annotation);
                Method mx = annotationSet.annotationType().getMethod("value", null);
                for (XLCellColumn annotationCellColumn : (XLCellColumn[]) mx.invoke(annotationSet)){
                    if (annotationCellColumn.processOf() == processOf){
                        processColumn = annotationCellColumn.column();
                        if (cellColumn.equals(processColumn)){
                            return m;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Class<?> verifiesSerializable(T obj) throws XLSerializableException {
        Class<?> paper = obj.getClass();
        if(!paper.isAnnotationPresent(XLSerializable.class)){
            throw new XLSerializableException();
        }
        return paper;
    }


}
