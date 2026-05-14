package tw.org.iii.practice.polymorphism;

// 題 5 — 欄位 vs 方法的多型差異（雷題）
// 目標：親手證明「方法會多型、欄位不會多型」這件事。
// 情境：
//   Parent 有 name 欄位（值："父"）和 say() 方法（印 "父說話"）
//   Child 重新宣告 name（值："子"）並覆寫 say()（印 "子說話"）
//
// 在 main 裡分別用兩種角度看同一個 Child 物件，把四個輸出填到觀察結果區。

public class Ex05_FieldVsMethod {

	public static void main(String[] args) {
		Parent p1 = new Child();
		Child p2 = new Child();
		
		System.out.println(p1.name); // 父
		System.out.println(p2.name); // 子
		p1.say(); // 都是子說話
		p2.say();
	}
}

class Parent {
	String name = "父";
	
	void say() {
		System.out.println("父說話");
	}
}

class Child extends Parent {
	String name = "子";
	void say() {
		System.out.println("子說話");
	}
}

/* === 觀察結果（寫完填這裡）===
p.name  =
p.say() =
c.name  =
c.say() =

一句話解釋為什麼欄位跟方法表現不一樣：

因為靜態只看型別 有instance的method才有動態分發
主要是效能考量

*/
