package task1;

public class DifferentMethods {
    private int a;
    private int b;

    public DifferentMethods(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public DifferentMethods() {}

    public int test3(int a,int b) {
        return a/b;
    }

    @Test(param1=10,param2=2)
    public void test(int a, int b) {
        StringBuilder sb = new StringBuilder("a = ");
        sb.append(a).append("\n").append("b = ").append(b).append("\n");
        sb.append("sum = ").append(a+b);
        System.out.print(sb);
    }

    public void test2(int a,int b) {
        System.out.println(a*b);
    }
}

