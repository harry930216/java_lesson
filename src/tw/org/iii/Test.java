package tw.org.iii;

class A {
    int x = 10;
    static int y = 20;

    void show() {
        System.out.println("A show");
    }

    static void greet() {
        System.out.println("A greet");
    }
}

class B extends A {
    int x = 100;
    static int y = 200;

    void show() {
        System.out.println("B show");
    }

    static void greet() {
        System.out.println("B greet");
    }
}

public class Test {
    public static void main(String[] args) {
        A obj = new B();
        System.out.println(obj.x);    // (1) 印什麼？
        System.out.println(obj.y);    // (2) 印什麼？
        obj.show();                   // (3) 印什麼？
        obj.greet();                  // (4) 印什麼？
    }
}