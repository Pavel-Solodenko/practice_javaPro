package task3;

public class A {
    @Save
    int a;
    @Save
    boolean b;

    public A(int a, boolean b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "a = "+a+"\nb = "+b;
    }
}
