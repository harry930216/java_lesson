package tw.org.iii.practice.abstractkeyword;

// 題 1 — abstract 踩雷集合（abstract 完整邊界）
// 目標：撞編譯錯,內化 abstract 的所有規則邊界,釐清 abstract class 跟 interface 的差別。
//
// 情境：
//   下面 BadAbstract01 故意踩三個雷,目前全部註解掉。
//   一個一個取消註解,觀察編譯訊息,記下來再註解回去。
//
//   雷 1：abstract private void x();        ← abstract 跟 private 衝突
//   雷 2：abstract void y() { ... }         ← abstract 方法不能有 body
//   雷 3：abstract final void z();          ← abstract 跟 final 衝突
//
//   然後寫正確版 AnimalA1：
//     abstract class AnimalA1：
//       - String name 欄位                 ← abstract class 可以有 instance 欄位
//       - 建構子 AnimalA1(String name) { this.name = name; }   ← abstract class 可以有建構子
//       - abstract void eat();              ← 抽象方法,強迫子類實作
//       - void sleep() { 印 name + " 睡覺" } ← 一般方法,子類繼承後可直接用
//
//   寫 DogA1 extends AnimalA1：
//     - 建構子用 super(name) 把名字丟給父類
//     - 實作 eat() 印 name + " 吃骨頭"
//
//   在 main：
//     - 嘗試 new AnimalA1("XX");           ← 觀察編譯訊息
//     - new DogA1("小白").sleep();
//     - new DogA1("小白").eat();
//
// 觀察重點（寫完回答）：
//   1. 雷 1 的編譯訊息？為什麼 abstract 不能搭 private？（提示：private 不能被子類看到）
//   2. 雷 2 的編譯訊息？為什麼 abstract 方法不能有 body？（提示：abstract 的意思）
//   3. 雷 3 的編譯訊息？為什麼 abstract 不能搭 final？（提示：final 的意思）
//   4. new AnimalA1("XX") 的編譯訊息？為什麼 abstract class 不能 new？
//   5. AnimalA1 有建構子,但既然不能 new,這建構子是給誰用的？
//   6. abstract class 可以完全沒有 abstract 方法嗎？（試試看：把 eat() 拿掉 abstract 改成空 body,看編譯過嗎）
//   7. 比較 abstract class 跟 interface：兩個都不能 new,差別是？（從 instance state 角度回答）

public class Ex01_AbstractTraps {

	public static void main(String[] args) {

	}
}

abstract class BadAbstract01 {

	// === 雷 1 === 取消註解觀察,觀察完再註解回去
	// abstract private void x();

	// === 雷 2 === 取消註解觀察,觀察完再註解回去
	// abstract void y() { System.out.println("我有 body"); }

	// === 雷 3 === 取消註解觀察,觀察完再註解回去
	// abstract final void z();
}

abstract class AnimalA1 {

}

class DogA1 extends AnimalA1 {

}

/* === 觀察結果（寫完填這裡）===
雷 1 編譯訊息：
雷 2 編譯訊息：
雷 3 編譯訊息：

new AnimalA1("XX") 編譯訊息：
new DogA1("小白").sleep() 輸出：
new DogA1("小白").eat()   輸出：

Q1 abstract 不能搭 private 為什麼：

Q2 abstract 方法不能有 body 為什麼：

Q3 abstract 不能搭 final 為什麼：

Q4 abstract class 不能 new 為什麼：

Q5 AnimalA1 建構子既然不能 new 是給誰用的：

Q6 abstract class 可以完全沒有 abstract 方法嗎：

Q7 abstract class 跟 interface 兩個都不能 new 差別在哪（從 instance state 角度）：

*/
