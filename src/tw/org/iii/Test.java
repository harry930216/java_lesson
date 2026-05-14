package tw.org.iii;

class A {
    String label = "我是A";
    void hi(B b) {
        System.out.println(this.label);
        b.greet(this);
    }
}
class B {
    String label = "我是B";
    void greet(A a) {
        System.out.println(this.label);
        System.out.println(a.label);
    }
}

A a1 = new A();
B b1 = new B();
a1.hi(b1);