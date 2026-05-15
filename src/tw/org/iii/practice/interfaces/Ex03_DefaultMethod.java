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
		Greetable p = new PoliteRobot();
		Greetable r = new RudeRobot();
		
		p.bye();
		p.hello();
		
		r.bye();
		r.hello();
	}
}

interface Greetable {
	void hello();    
	default void bye() { System.out.println("再見");}   
}

class PoliteRobot implements Greetable {
	public void hello() {
		System.out.println("你好");
	}
}

class RudeRobot implements Greetable {
	public void hello() {
		System.out.println("你好");
	}
	@Override
	public void bye() {
		System.out.println("滾");
	}
}

/* === 觀察結果 ===
PoliteRobot.hello() = 你好
PoliteRobot.bye()   = 再見    ← 沒覆寫，繼承 Greetable 的 default 實作
RudeRobot.hello()   = 你好
RudeRobot.bye()     = 滾      ← 覆寫了，跑 RudeRobot 自己的版本

Q1 PoliteRobot 沒寫 bye() 為什麼能編譯：
   我沒辦法講出完整理論 只知道Greetable 有先做了

   [Claude 補充]
   契約有沒有被滿足，看「介面要求的方法 PoliteRobot 是否都拿得出來」。
   - hello() 是抽象的（沒 body），介面沒提供實作 → PoliteRobot 必須自己寫，不寫就編譯錯
   - bye() 是 default 的（有 body），介面已經提供了一份預設實作 → PoliteRobot 沒覆寫
     時，自動「繼承」介面那份 default 來用，契約照樣滿足，所以編譯通過
   一句話：default method 讓介面從「純契約」變成「契約 + 預設備案」，沒覆寫就用備案。

Q2 default method 解決什麼問題：
   大家都有共同功能時 看自己要不要改

   [Claude 補充] — 這是「怎麼用」的描述，題目問的是「為什麼存在」
   核心：介面演進 (interface evolution) 問題。
   Java 8 以前的痛點：interface 一旦發布出去，幾乎不能再加方法。
     場景：你寫了一個 interface，100 個第三方 class 已經實作了它。
     半年後想加一個新方法（例如 Collection 想加 stream()）→
       - 如果新方法是抽象的：那 100 個 class 全部編譯不過（少實作新契約）
       - 結果：你不敢加，介面演化卡死
   Java 8 default method 的解法：
     新增方法時直接給 default 實作 → 舊 class 不改 code 也能編譯通過（用 default）
     想用更專屬版本的 class 可以選擇覆寫 → 既不破壞既有實作，又能擴展新功能
   經典案例：Collection.stream() / forEach() 都是 Java 8 用 default 加進去的，
            所以舊的 ArrayList、LinkedList、自訂 List 一行 code 都不用改就能用。
*/
