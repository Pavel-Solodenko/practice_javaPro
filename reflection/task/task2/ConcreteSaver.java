package task2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConcreteSaver implements Saver {
    private Object obj;
    private final Class<SaveTo> saveToClass = SaveTo.class;
    private final Class<Saver> saverClass = Saver.class;

    public ConcreteSaver(Object obj) {
        this.obj = obj;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return saverClass;
    }

    @Override
    public boolean save() {
        Class<?> objCls = obj.getClass();

        String text = getText(objCls);
        String path = "";


        if (objCls.isAnnotationPresent(saveToClass)) {
            path = objCls.getAnnotation(saveToClass).path();
        }

        if (!text.isEmpty() && !path.isEmpty()) {
            Method[] methods = objCls.getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(saverClass)) {
                    try {
                        m.invoke(obj,path);
                        return true;
                    } catch (IllegalAccessException | InvocationTargetException e){
                        e.printStackTrace();
                    }

                }

            }

        }

        return false;
    }

    private String getText(Class objCls) {
        Field[] fields = objCls.getDeclaredFields();
        for(Field f : fields) {
            if (f.getType().equals(String.class)) {
                f.setAccessible(true);
                try {
                    return  (String) f.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }
        return "";
    }

}
