package tw.org.iii.practice.interfaces;

// 題 5 — 同需求兩種寫法（決策練習）
// 目標：同一個需求分別用 abstract class 和 interface 各寫一遍，比較差異。
// 情境：需求是「Shape 形狀有 area() 方法計算面積，Circle 和 Square 是具體形狀」。
//
//   寫法 A（abstract class）：
//     abstract class ShapeA 有：
//       - double size 欄位
//       - 建構子 ShapeA(double size)
//       - abstract double area()
//     CircleA extends ShapeA，area() = size * size * 3.14
//     SquareA extends ShapeA，area() = size * size
//
//   寫法 B（interface）：
//     interface ShapeB 有：
//       - double area();
//     CircleB implements ShapeB，自己宣告 size 欄位、自己寫建構子、area() = size * size * 3.14
//     SquareB implements ShapeB，同理
//
// 兩種寫法都在 main 各 new 一次計算面積，印出結果。
//
// 觀察重點（寫完回答）：
//   1. 哪一種寫法重複程式碼比較多？為什麼？
//   2. 如果需求變成「Circle 和 Square 還要能 implements Drawable」，哪種寫法比較靈活？
//   3. 結論：什麼情況該選 abstract、什麼情況該選 interface？

public class Ex05_AbstractVsInterface {

	public static void main(String[] args) {

	}
}

// === 寫法 A：abstract class ===
abstract class ShapeA {

}

class CircleA extends ShapeA {

}

class SquareA extends ShapeA {

}

// === 寫法 B：interface ===
interface ShapeB {

}

class CircleB implements ShapeB {

}

class SquareB implements ShapeB {

}

/* === 觀察結果（寫完填這裡）===
CircleA 面積 =
SquareA 面積 =
CircleB 面積 =
SquareB 面積 =

Q1 哪種寫法重複程式碼比較多：

Q2 如果還要能 implements Drawable，哪種比較靈活：

Q3 結論（什麼時候用 abstract、什麼時候用 interface）：

*/
