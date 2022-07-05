package task3;

public class B {
    @Save
    double a;
    @Save
    String b;

    public B(double a, String  b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "a = "+a+"\nb = "+b;
    }
}
