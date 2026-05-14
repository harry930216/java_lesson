package tw.org.iii.practice.interfaces;

// 題 1 — 單一 interface 實作（純語法入門）
// 目標：用 interface 宣告「能力契約」，讓一個 class 實作它。
// 情境：
//   Flyable 介面有一個 fly() 方法（無 body）
//   Bird 實作 Flyable，提供 fly() 的具體行為（印 "鳥在飛"）
//
// 在 main 用「介面型別變數」裝 Bird 物件，呼叫 fly()。
//
// 觀察重點（寫完回答）：
//   1. fly() 方法在 Bird 裡為什麼一定要 public？
//   2. 能不能寫 Flyable f = new Flyable(); ？為什麼？

public class Ex01_BasicInterface {

	public static void main(String[] args) {

	}
}

interface Flyable {

}

class Bird implements Flyable {

}

/* === 觀察結果（寫完填這裡）===
fly() 輸出：

Q1 fly() 為什麼必須 public：

Q2 為什麼不能 new Flyable()：

*/
