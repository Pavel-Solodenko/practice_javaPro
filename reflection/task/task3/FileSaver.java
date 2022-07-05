package task3;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class FileSaver {
    private String path;
    private Object obj;
    private final Class<Save> saveCls= Save.class;

    public FileSaver(String path, Object obj) {
        this.path = path;
        this.obj = obj;
    }

    public void serialization() throws IllegalAccessException{
        StringBuilder sb = new StringBuilder();
        ArrayList<Field> fieldsToSave = getFieldsToSave();
        int i = 1;

        for (Field f : fieldsToSave) {
            f.setAccessible(true);
            if (checkInnerObject(f)) {
                Object innerObj = f.get(obj);
                sb.append(f.getName()).append(" : [\n");
                sb.append(getInnerObjectValues(innerObj));
                sb.append("]\n");
            }
            else {
                sb.append("{ parameter ").append(i).append(" : \"").append(f.get(obj)).append("\" }").append("\n");
            }
            ++i;
        }
        write(sb.toString());
    }

    private String getInnerObjectValues(Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Field[] innerObjFields = obj.getClass().getDeclaredFields();
        int i = 1;
        for (Field f : innerObjFields) {
            if (f.isAnnotationPresent(saveCls)) {
                sb.append("\t{ parameter ").append(i).append(" : \"")
                        .append(f.get(obj)).append("\" }\n");
                i++;
            }
        }
        return sb.toString();
    }

    public boolean checkInnerObject(Field field) {
        return !field.getType().isPrimitive() &&
                !field.getType().getName().contains("java.lang");
    }

    public void deserialization() throws IllegalAccessException {
        String text = read(path);
        ArrayList<String> values =  splitText(text);
        ArrayList<Class<?>> classes = getRequiredTypes();

        ArrayList<Field> fieldsToSet = getFieldsToSet();

        int itrValues = 0;
        for (int i = 0; i < fieldsToSet.size();i++) {

            Field workField = fieldsToSet.get(i);
            workField.setAccessible(true);
            Object valueToSet = parser(classes.get(i).getTypeName(),values.get(itrValues));
            if (checkInnerObject(workField)) {
                itrValues = InnerObjectProccess(workField,values,itrValues);
            }
            else {
                workField.set(obj,valueToSet);
                ++itrValues;
            }
        }

    }

    private int InnerObjectProccess(Field workField,ArrayList<String> values,int itrValues) throws IllegalAccessException {
        Object innerObj =  workField.get(obj);

        ArrayList<Field> innerObjFields = InnerObjectParser(innerObj);


        for (int j = 0; j < innerObjFields.size();++j) {
            Field workInnerField = innerObjFields.get(j);

            Object valueToSet = parser(workInnerField.getType().getTypeName(),values.get(itrValues));
            workInnerField.set(innerObj,valueToSet);
            ++itrValues;
        }
        return itrValues;
    }

    private ArrayList<Field> InnerObjectParser(Object innerObj) {
        ArrayList<Field> result = new ArrayList<>();
        Field[] innerObjFields = innerObj.getClass().getDeclaredFields();
        for (Field workInnerField : innerObjFields) {
            if (workInnerField.isAnnotationPresent(saveCls)) {
                workInnerField.setAccessible(true);
                result.add(workInnerField);
            }
        }
        return result;
    }

    private Object parser(String type ,String value) {

        Object result = null;
        switch (type) {
            case "int":
                result = Integer.parseInt(value);
                break;
            case "java.lang.String":
                result = value;
                break;
            case "boolean":
                result = Boolean.parseBoolean(value);
                break;
            case "double":
                result = Double.parseDouble(value);
                break;
                // case ...
        }



        return result;
    }

    private ArrayList<String> splitText(String text) {
        ArrayList<String> result = new ArrayList<>();
        String innerObjectsString = "";

        String[] raws = text.split("\n");
        boolean isInnerObj = false;
        for (String raw : raws) {
            if (raw.contains("[")) {
                isInnerObj = true;
            }
            if (raw.contains("]")) {
                innerObjectsString += raw;
                isInnerObj = false;
                continue;
            }
            if (isInnerObj == true) {
                innerObjectsString += raw;
                continue;
            }
            String value = raw.substring(raw.indexOf(":")+1).replaceAll(" ","")
                    .replaceAll("}","").replaceAll("\"","");
            result.add(value);
        }

        if (!innerObjectsString.isEmpty()) {
            result.addAll(processArrayListWithInnerObj(innerObjectsString));
        }

        return result;
    }

    private ArrayList<String> processArrayListWithInnerObj(String innerObjectsString) {
        ArrayList<String> result = new ArrayList<>();
        innerObjectsString = innerObjectsString.replaceAll("\n","")
                .replaceAll("\\[","\n").replaceAll("]","\n");

        String [] raws = innerObjectsString.split("\n\t");
        for (String s : raws) {
            if (s.contains("{")) {
                result.add(s.substring(s.indexOf("{"),s.lastIndexOf("}")+1).replace("\t","\n"));
            }
        }
        result = splitText(result.toString().replaceAll("\\[","")
                .replaceAll("]","")
                .replaceAll(",","\n"));
//        System.out.println(result);
//        System.exit(0);
        return result;
    }

    private ArrayList<Field> getFieldsToSet() {
        Field[] fields = obj.getClass().getDeclaredFields();
        ArrayList<Field> result = new ArrayList<>();
        for (Field f : fields) {
            if (f.isAnnotationPresent(saveCls)) {
                result.add(f);
            }
        }
        return result;
    }

    private ArrayList<Class<?>> getRequiredTypes() {
        ArrayList<Class<?>> classes = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if(f.isAnnotationPresent(saveCls)) {
                classes.add(f.getType());
            }
        }


        return classes;
    }

    private String read(String path) {
        String text = "";
        try {
            InputStream is = new FileInputStream(path);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }


    private void write(String str) {
        try (OutputStream os = new FileOutputStream(path)) {
            os.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Field> getFieldsToSave() {
        ArrayList<Field> fields = new ArrayList<>();
        Class<?> objCls = obj.getClass();
        Field[] allFields = objCls.getDeclaredFields();
        for (Field f : allFields) {
            if (f.isAnnotationPresent(saveCls)) {
                fields.add(f);
            }
        }
        return fields;
    }
}
