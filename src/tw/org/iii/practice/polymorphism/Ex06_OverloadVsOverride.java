package tw.org.iii.practice.polymorphism;

// 題 6 — Overload 跟 Override 的本質差別
// 目標：證明 Overload 是編譯期決定（不是多型）、Override 是執行期決定（才是多型）。
//
// 情境：
//   Overload 範例：
//     Calculator06 有兩個 add 方法
//       add(int a, int b)       印 "[int 版] 結果=" + (a+b)
//       add(double a, double b) 印 "[double 版] 結果=" + (a+b)
//
//   Override 範例：
//     Animal06         有 eat() 印 "動物吃"
//     Dog06 extends Animal06  覆寫 eat() 印 "狗吃骨頭"
//
//   在 main：
//     - Calculator06 c = new Calculator06();
//       c.add(1, 2);          ← 觀察跑哪個版本
//       c.add(1.0, 2.0);      ← 觀察跑哪個版本
//       c.add(1, 2.0);        ← 混合型別,觀察跑哪個版本
//
//     - Animal06 a = new Dog06();
//       a.eat();              ← a 宣告型別 Animal06,但跑哪個版本?
//
// 觀察重點（寫完回答）：
//   1. c.add(1, 2.0) 為什麼會選 double 版？是編譯期決定還是執行期決定？
//   2. a.eat() 為什麼跑 "狗吃骨頭"？是編譯期決定還是執行期決定？
//   3. 一句話分別說明 Overload 跟 Override 的「決定時機」。
//   4. 為什麼說「Overload 不是多型,Override 才是多型」？多型的定義是什麼？
//   5. 如果把 Dog06.eat() 拿掉（不覆寫）,a.eat() 會跑誰？為什麼？

public class Ex06_OverloadVsOverride {

	public static void main(String[] args) {

	}
}

class Calculator06 {

}

class Animal06 {

}

class Dog06 extends Animal06 {

}

/* === 觀察結果（寫完填這裡）===
c.add(1, 2)     輸出：
c.add(1.0, 2.0) 輸出：
c.add(1, 2.0)   輸出：
a.eat()         輸出：

Q1 c.add(1, 2.0) 為什麼選 double 版（編譯期 vs 執行期）：

Q2 a.eat() 為什麼跑狗吃（編譯期 vs 執行期）：

Q3 Overload 跟 Override 的決定時機分別：

Q4 為什麼 Overload 不是多型 Override 才是：

Q5 拿掉 Dog06.eat() 後 a.eat() 跑誰為什麼：

*/
