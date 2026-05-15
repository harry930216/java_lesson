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
		Flyable b = new Bird();
		b.fly();

	}
}

interface Flyable {
	void fly();
}

class Bird implements Flyable {
	public void fly() {
		System.out.println("鳥在飛");
	}
}

/*
 * === 觀察結果（寫完填這裡）=== fly() 輸出：鳥在飛
 *
 * Q1 fly() 為什麼必須 public： 必須公開 不寫會差一點
 *   [Claude 提示]
 *   - 你 interface 裡 void fly(); 沒寫 public，但編譯器其實偷加了一個修飾子（兩個英文字）
 *   - 子類別實作時，可見性能不能比契約規定的還低？
 *   - 把這兩點接起來 → 真正原因
 *
 * Q2 為什麼不能 new Flyable()： Flyable() 不是 instance
 *   [Claude 提示]
 *   - 用詞要修：instance 就是 new 出來的東西，講「不是 instance」邏輯繞
 *   - void fly(); 結尾是 ; 不是 {} → 代表方法有沒有「具體內容」？
 *   - 假設真的能 new Flyable()，呼叫 f.fly() 時 JVM 要去執行誰寫的 code？
 *   - 換方向：interface 是「契約」還是「成品」？契約能直接拿來用嗎？
 *
 * [Claude 對 code 的回饋]
 *   - Flyable b = new Bird(); 這寫法很重要 ✅
 *     介面型別裝實作物件 = interface 多型核心，後面題目會一直用到
 */
