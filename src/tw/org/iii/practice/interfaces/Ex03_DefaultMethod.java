package tw.org.iii.practice.interfaces;

// 題 3 — default method（Java 8+ 必懂）
// 目標：理解介面為什麼能有「方法 body」，以及實作類何時要覆寫。
// 情境：
//   Greetable 介面：
//     - void hello()    ← 抽象，必須實作
//     - default void bye() { 印 "再見" }   ← 預設實作
//
//   PoliteRobot 實作 Greetable，只實作 hello()，不覆寫 bye()
//   RudeRobot   實作 Greetable，hello() 自己寫，並「覆寫」bye() 改印 "滾"
//
// 在 main 各 new 一個出來，呼叫 hello() 和 bye() 比較差異。
//
// 觀察重點（寫完回答）：
//   1. 為什麼 PoliteRobot 沒寫 bye() 也能編譯通過？
//   2. default method 在介面演進（加新方法）時解決什麼問題？
//      （想想：如果介面突然加一個抽象方法，所有實作類會發生什麼事？）

public class Ex03_DefaultMethod {

	public static void main(String[] args) {

	}
}

interface Greetable {

}

class PoliteRobot implements Greetable {

}

class RudeRobot implements Greetable {

}

/* === 觀察結果（寫完填這裡）===
PoliteRobot.hello() =
PoliteRobot.bye()   =
RudeRobot.hello()   =
RudeRobot.bye()     =

Q1 PoliteRobot 沒寫 bye() 為什麼能編譯：

Q2 default method 解決什麼問題：

*/
