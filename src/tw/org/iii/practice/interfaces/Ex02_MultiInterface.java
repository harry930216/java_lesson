package tw.org.iii.practice.interfaces;

// 題 2 — 多重實作（interface 比 abstract 多出來的核心能力）
// 目標：一個 class 同時實作兩個不相關的介面，獲得兩種能力。
// 情境：
//   Swimmable 介面有 swim() 方法（印 "游泳"）
//   Runnable2 介面有 run() 方法（印 "跑步"）   ← 名稱避開 java.lang.Runnable
//   Triathlete（三鐵選手）同時實作 Swimmable + Runnable2
//
// 在 main：
//   - 用 Triathlete 型別呼叫 swim() 和 run()
//   - 用 Swimmable 型別變數裝同一個物件，試試能呼叫到哪些方法
//   - 用 Runnable2 型別變數裝同一個物件，試試能呼叫到哪些方法
//
// 觀察重點：用不同「介面視角」看同一個物件，能看到的方法集合不一樣。

public class Ex02_MultiInterface {

	public static void main(String[] args) {

	}
}

interface Swimmable {

}

interface Runnable2 {

}

class Triathlete implements Swimmable, Runnable2 {

}

/* === 觀察結果（寫完填這裡）===
Triathlete 視角能呼叫：
Swimmable 視角能呼叫：
Runnable2 視角能呼叫：

一句話解釋為什麼介面視角能看到的方法不同：

*/
