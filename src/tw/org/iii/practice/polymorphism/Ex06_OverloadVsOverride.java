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
		Calculator06 c = new Calculator06();
		c.add(1, 2);
		c.add(1.0, 2.0);
		c.add(1, 2.0);

		Animal06 a = new Dog06();
		a.eat();
	}
}

class Calculator06 {
	void add(int a, int b) {
		System.out.println("[int 版] 結果=" + (a + b));
	}

	void add(double a, double b) {
		System.out.println("[double 版] 結果=" + (a + b));
	}
}

class Animal06 {
	void eat() {
		System.out.println("動物吃");
	}
}

class Dog06 extends Animal06 {
	@Override
	void eat() {
		System.out.println("狗吃骨頭");
	}
}

/* === 觀察結果 ===
c.add(1, 2)     輸出：[int 版] 結果=3
c.add(1.0, 2.0) 輸出：[double 版] 結果=3.0
c.add(1, 2.0)   輸出：[double 版] 結果=3.0
a.eat()         輸出：狗吃骨頭

Q1 c.add(1, 2.0) 為什麼選 double 版（編譯期 vs 執行期）：
	因為輸入的參數阿

	[Claude 補充] 方向對（確實看參數），但要補兩個關鍵點：
	- 看的是參數的「編譯期型別」：1 是 int、2.0 是 double。
	- 沒有 add(int, double) 這個多載，編譯器找相容的：
	    add(double, double) → 行（int 的 1 自動 widening 成 double）
	    add(int, int)       → 不行（2.0 是 double，塞不進 int）
	  → 選 double 版。
	- 這是「編譯期」決定 —— 編譯當下就挑好簽名、寫死進 bytecode，執行期不重選。

Q2 a.eat() 為什麼跑狗吃（編譯期 vs 執行期）：
	因為a確實是Dog06() 多型觸發

	[Claude 補充] ✅ 對。a 宣告型別是 Animal06，但實際物件是 Dog06。
	a.eat() 在「執行期」由 JVM 看實際物件 → 跑 Dog06 覆寫的 eat()。
	執行期才決定 = 多型。

Q3 Overload 跟 Override 的決定時機分別：
	編譯/執行 其實前者我沒有很懂

	[Claude 解答] 結論先給：Overload → 編譯期；Override → 執行期。
	你說「沒很懂」的前者（Overload）——
	編譯器在編譯當下，依參數的編譯期型別挑好「呼叫哪個簽名」，
	把這個選擇寫死進 bytecode；執行期不再重選。
	所以「選哪個多載」永遠是編譯期、靜態決定。
	一句話對照：
	- Overload → 編譯期決定「呼叫哪個簽名」
	- Override → 執行期決定「跑哪個 class 的實作」

Q4 為什麼 Overload 不是多型 Override 才是：
	因為跑在執行層 java底層會自動判斷

	[Claude 解答] 你抓到 Override 那半，但漏了題目要的「多型的定義」。
	多型 = 同一個呼叫，執行期依「實際物件」跑出不同行為。
	- Overload：呼叫哪個方法在編譯期就定死、執行期不變
	            → 沒有「執行期才決定」這件事 → 不是多型。
	- Override：同一個 a.eat()，實際物件換了就跑不同版本，執行期才決定
	            → 才是多型。

Q5 拿掉 Dog06.eat() 後 a.eat() 跑誰為什麼：
	動物吃 編譯期沒有override 系統找父類繼承

	[Claude 補充] ✅ 對 —— 跑 "動物吃"。
	Dog06 不覆寫 eat() → Dog06 沒有自己的 eat() → 繼承 Animal06 的。
	執行期 JVM 找 Dog06 的 eat()，Dog06 沒有 → 沿繼承鏈往上找到 Animal06 的。
	小修詞：與其說「編譯期沒有 override」，更準是
	「Dog06 這個 class 根本沒有自己的 eat()，所以用繼承來的那份」。
*/
