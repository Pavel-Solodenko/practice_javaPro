package task2;

import task2.SaveTo;
import task2.Saver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@SaveTo(path="file.txt")
public class TextContainer {
    private String text;
    private int a;
    private boolean b;

    public TextContainer(String text,int a,boolean b) {
        this.text = text;
        this.a = a;
        this.b = b;
    }

    @Saver(save=true)
    public void save(String path) {
        try (OutputStream os = new FileOutputStream(path)) {
             os.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (OutputStream os = new FileOutputStream("text.txt")) {
            os.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
