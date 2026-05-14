package tw.org.iii.practice.interfaces;

// 題 4 — 多重 default 衝突 + InterfaceName.super.method()
// 目標：兩個介面有同名 default 方法，實作類必須明確選邊，學 super 前冠介面名的語法。
// 情境：
//   InterfaceA 有 default void greet() { 印 "A: hello" }
//   InterfaceB 有 default void greet() { 印 "B: hi" }
//   AmbiguousClass 同時實作 A 和 B
//
//   光寫 implements A, B 會編譯錯（Duplicate default methods）
//   解法：AmbiguousClass 自己覆寫 greet()，內部用：
//         InterfaceA.super.greet();
//         InterfaceB.super.greet();
//   兩個都叫一次，或選其中一個。
//
// 在 main new 一個 AmbiguousClass 呼叫 greet()，應該看到 A 和 B 的兩行輸出。
//
// 觀察重點（寫完回答）：
//   1. 為什麼這個語法要叫 InterfaceA.super.greet() 而不能寫 super.greet()？
//   2. 這是 Java 唯一會把 super 前面冠類別/介面名稱的情境。回想 Ex01-03 super，
//      你之前寫的 super 永遠是「父類」一種選項。為什麼介面這裡需要「指定哪個」？

public class Ex04_DiamondConflict {

	public static void main(String[] args) {

	}
}

interface InterfaceA {

}

interface InterfaceB {

}

class AmbiguousClass implements InterfaceA, InterfaceB {

}

/* === 觀察結果（寫完填這裡）===
greet() 輸出：

Q1 為什麼要寫 InterfaceA.super.greet() 而不能寫 super.greet()：

Q2 為什麼介面這裡需要指定，類別繼承不用指定：

*/
