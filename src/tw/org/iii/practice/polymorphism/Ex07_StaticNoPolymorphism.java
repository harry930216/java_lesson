package tw.org.iii.practice.polymorphism;

// 題 7 — static 方法不會多型（隱藏 vs 覆寫的差別）
// 目標：親手證明「static 方法是隱藏 (hide),不是覆寫 (override)」,所以不走動態分派。
//
// 情境：
//   Animal07 有兩個方法：
//     static void info()      印 "[Animal07 static] 我是動物分類學"
//     void describe()         印 "[Animal07 instance] 我是動物個體"
//
//   Dog07 extends Animal07,**兩個方法都重新宣告**：
//     static void info()      印 "[Dog07 static] 我是狗分類學"   ← 這不是覆寫,是隱藏
//     void describe()         印 "[Dog07 instance] 我是狗個體"   ← 這才是覆寫
//
//   在 main：
//     - Animal07 a = new Dog07();
//     - a.info();         ← 印什麼?跟 a.describe() 比較
//     - a.describe();     ← 印什麼?
//     - Animal07.info();  ← 直接從類別呼叫
//     - Dog07.info();     ← 直接從類別呼叫
//
//     ⚠️ 注意：a.info() 在大多數 IDE（Eclipse / VSCode）會跳黃色警告：
//     "The static method info() from the type Animal07 should be accessed in a static way"
//     這個警告本身就是答案的一部分。
//
// 觀察重點（寫完回答）：
//   1. a.info() 跟 a.describe() 跑出來的「主體」（動物 / 狗）哪個一樣、哪個不一樣？
//   2. 為什麼 a 明明指向 Dog07 物件,a.info() 卻跑 Animal07.info()？
//   3. IDE 的警告為什麼建議用「Animal07.info()」這種寫法？這暗示 static 方法綁定到誰？
//   4. 「隱藏 (hide)」跟「覆寫 (override)」差在哪？口訣：static 方法叫隱藏、instance 方法叫覆寫。
//   5. 為什麼 static 沒有多型？（提示：多型發生在「物件層級」,static 屬於誰？）

public class Ex07_StaticNoPolymorphism {

	public static void main(String[] args) {

	}
}

class Animal07 {

}

class Dog07 extends Animal07 {

}

/* === 觀察結果（寫完填這裡）===
a.info()        輸出：
a.describe()    輸出：
Animal07.info() 輸出：
Dog07.info()    輸出：

Q1 info 跟 describe 哪個主體一樣哪個不一樣：

Q2 為什麼 a 指 Dog07 但 a.info() 跑 Animal07.info：

Q3 IDE 警告暗示 static 方法綁定到誰：

Q4 隱藏 vs 覆寫差別：

Q5 為什麼 static 沒有多型（static 屬於誰）：

*/
