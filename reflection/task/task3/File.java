package task3;

public class File {
    @Save
    private int a;
    private boolean b;
    @Save
    private String text;
    private double c;
    @Save
    private A aa;
    @Save
    private B bb;

    public File(int a, boolean b, String text, double c,A aa,B bb) {
        this.a = a;
        this.b = b;
        this.text = text;
        this.c = c;
        this.aa = aa;
        this.bb = bb;
    }

    @Override
    public String toString() {
        return "a = "+a+"\nb = "+b+"\ntext = "+text+"\nc = "+c+"\nInnerObj:\n"+aa.toString()+"\n"+bb.toString();
    }
}
