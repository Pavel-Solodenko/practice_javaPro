import task1.DifferentMethods;
import task1.Test;
import task2.Saver;
import task2.ConcreteSaver;
import task2.TextContainer;
import task3.A;
import task3.B;
import task3.File;
import task3.FileSaver;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();

        try {
            task3();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void task3() throws IllegalAccessException {
        A aa = new A(10,true);
        B bb = new B(5.14,"Ahaha");

        File file = new File(2,false,"Hello",3.14,aa,bb);
        FileSaver fileSaver = new FileSaver("file.txt",file);
        fileSaver.serialization();

        aa = new A(0,false);
        bb = new B(0.0,"");
        File file1 = new File(11,true,"bye",1.11,aa,bb);

        System.out.println("\nBefore: \n"+file1);
        FileSaver fileSaver1 = new FileSaver("file.txt",file1);
        fileSaver1.deserialization();

        System.out.println("\nAfter:\n"+file1+"\n\n");
    }

    public static void task2() {
        TextContainer textContainer = new TextContainer("Hello",3,false);
        Saver saver = new ConcreteSaver(textContainer);
        saver.save();
    }

    public static void task1() {
        final Class<Test> annotationTestClass = Test.class;
        final Class<?> clsTask1 = DifferentMethods.class;
        java.lang.reflect.Method[] methodsTask1 = clsTask1.getMethods();
        for (java.lang.reflect.Method method : methodsTask1) {
            if (method.isAnnotationPresent(annotationTestClass)) {
                int a = method.getAnnotation(annotationTestClass).param1();
                int b = method.getAnnotation(annotationTestClass).param2();
                try {
                    method.invoke(new DifferentMethods(),a, b);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

