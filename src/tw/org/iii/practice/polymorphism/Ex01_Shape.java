package tw.org.iii.practice.polymorphism;

// 題 1 — 基本多型
// 目標：用父類型別的變數裝子類物件，呼叫同名方法跑出不同行為。
// 情境：Shape 有 draw() 方法，Circle 和 Rectangle 各自覆寫它，用 Shape[] 統一呼叫。

public class Ex01_Shape {

    public static void main(String[] args) {
    	Shape[] shapes = {new Shape(), new Circle(), new Rectangle()};
    	for (Shape s : shapes) {
    		s.draw();
    	}
    }
}

class Shape {
	void draw() {
		System.out.println("畫形狀");
	}
}

class Circle extends Shape {
	void draw() {
		System.out.println("畫圓形");
	}
}

class Rectangle extends Shape {
	void draw() {
		System.out.println("畫矩形");
	}
}
