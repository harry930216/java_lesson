package tw.org.iii.practice.interfaces;

// 題 11 — instanceof 當能力檢查（interface 身份 4：能力標籤）
// 目標：理解「同一個物件可以同時擁有多個介面標籤」,以及 instanceof 怎麼判斷。
//
// 情境：
//   三個介面（各自一個方法）：
//     Flyable11   void fly();    印 "起飛"
//     Swimmable11 void swim();   印 "下水"
//     Walkable11  void walk();   印 "走路"
//
//   三隻動物（implements 組合不同）：
//     Duck11   implements 三個全部（會飛、會游、會走）
//     Eagle11  implements Flyable11 + Walkable11（會飛、會走,不會游）
//     Fish11   implements Swimmable11 only（只會游）
//
//   一個 static 方法 move(Object animal) 寫在 Ex11 類別裡：
//     用 instanceof 依序檢查 Flyable11 / Swimmable11 / Walkable11,
//     檢查到哪個就呼叫對應方法（強轉後呼叫）。
//     例如 Duck11 進去會印「起飛/下水/走路」三行,Fish11 只印「下水」。
//
//   在 main：
//     - move(new Duck11());   ← 預期三個動作全跑
//     - move(new Eagle11());  ← 預期兩個動作
//     - move(new Fish11());   ← 預期一個動作
//     - move("這是字串");      ← 預期沒任何動作（字串不 implements 任何能力）
//
// 觀察重點（寫完回答）：
//   1. 為什麼 instanceof 後要強制轉型才能呼叫方法？（編譯期 vs 執行期）
//   2. 一個物件能同時被三個 instanceof 判斷成 true 嗎？為什麼？
//   3. 為什麼 move 的參數型別寫 Object,不寫 Animal 之類的共同父類？
//      （提示：Duck11/Eagle11/Fish11 有共同父類嗎？）
//   4. 為什麼字串那個 case 一個方法都不會呼叫到？編譯不會炸嗎？
//   5. interface 的 5 個身份中,instanceof 對應的是哪個身份？

public class Ex11_InstanceofCapability {

	public static void main(String[] args) {
		move(new Duck11());
		move(new Eagle11());
		move(new Fish11());
		move("這是字串");
	}

	static void move(Object animal) {
		if (animal instanceof Flyable11) ((Flyable11)animal).fly();
		if (animal instanceof Swimmable11) ((Swimmable11)animal).swim();
		if (animal instanceof Walkable11) ((Walkable11)animal).walk();
	}
}

interface Flyable11 {
	default void fly() { System.out.println("起飛"); }
}

interface Swimmable11 {
	default void swim() { System.out.println("下水"); }
}

interface Walkable11 {
	default void walk() { System.out.println("走路"); }
}

class Duck11 implements Flyable11, Swimmable11, Walkable11 {
	
}

class Eagle11 implements Flyable11, Walkable11 {
	
}

class Fish11 implements Swimmable11 {
	
}

/* === 觀察結果 ===
move(Duck11)  輸出：起飛 / 下水 / 走路（三個能力標籤都中）
move(Eagle11) 輸出：起飛 / 走路（沒 Swimmable，swim 那個 if 跳過）
move(Fish11)  輸出：下水（只有 Swimmable）
move("字串")  輸出：（無 —— 三個 instanceof 全 false，什麼都不做）

Q1 為什麼 instanceof 後要強轉（編譯 vs 執行）：
	因為原本是object 強制轉型(先if判定確認實體後能轉)
	編譯器才能呼叫方法

	[Claude 補充] ✅ 方向對 —— 講精準：
	- 編譯期：編譯器只看 animal 的「宣告型別」= Object，Object 身上沒有 fly()
	          → 直接寫 animal.fly() 編譯就炸。
	          強轉 (Flyable11)animal = 跟編譯器說「這個當 Flyable11 看」，它才放行。
	- 執行期：instanceof 是執行期的安全確認 —— 先確認它真的是 Flyable11，
	          強轉才不會丟 ClassCastException。
	→ 一句話：要強轉，是因為「編譯期」編譯器只認宣告型別；instanceof 是「執行期」把關。

Q2 一物件能同時被 3 個 instanceof 判斷 true 嗎為什麼：
	不行 他看的是實體 但我有點忘記instance of看的是hashcode or 實體

	[Claude 解答] 答案是「可以」，你答反了 —— 而這正是本題的核心。
	一個 Duck11 物件，instanceof Flyable11 / Swimmable11 / Walkable11 三個全 true。
	instanceof 看的不是 hashcode —— 它看「這物件的實際 class，以及那個 class
	的整條繼承/實作鏈」。Duck11 這個 class implements 三個介面
	→ 一個 Duck11 物件就同時掛三個能力標籤。
	物件只有一個 class，但那個 class 可以掛很多介面。
	題目第 4 行自己就寫了「同一個物件可以同時擁有多個介面標籤」。
	→ 這就是為什麼 move() 要用三個「獨立 if」，不能用 else if（else 代表互斥）。

Q3 為什麼 move 參數寫 Object 不寫共同父類：
	其實我也不知道 但是object是共同祖先 拿來當插件很不錯

	[Claude 解答] 關鍵：Duck11 / Eagle11 / Fish11 之間「根本沒有共同父類」。
	它們各自只 implements 介面、沒有 extends 任何 class，彼此是兄弟，
	唯一的共同祖先就是 Object。
	→ 所以你「寫不出」Animal 那種共同父類型別 —— 那個 type 不存在。
	  Object 不是「比較好」的選擇，是「唯一」的選擇。
	  而且寫 Object 連字串都收得進來（題目第 26 行就是要測字串 case）。

Q4 字串那個 case 編譯不會炸嗎：
	我不知道 我猜自動轉物件了

	[Claude 解答] 不會炸，分兩段看：
	- 進得去：String 是 Object 的子型別（一切都是 Object）→ "字串" 塞進
	          Object animal 參數，自動 upcast（你猜「自動轉物件」這半對）。
	- 不會炸：進 move() 後，"字串" instanceof 三個介面全 false
	          → 三個 if 都不進 → 什麼都不做。
	          instanceof 對不符合的型別只是回傳 false，不會丟例外。
	          而且因為 instanceof false，那行的強轉 (Flyable11)animal
	          根本不會被執行到 → 不可能 ClassCastException。
	→ 這就是「先 instanceof、再強轉」這個順序的價值：instanceof 當關卡，
	  把危險的轉型擋在門外。

Q5 instanceof 對應 interface 5 個身份中的哪個：
	不知道

	[Claude 解答] 身份 4 —— 能力標籤。
	你檔案第 3 行的標題自己就寫了「interface 身份 4：能力標籤」。
	instanceof 在問的是「這物件有沒有被打上『會飛』這個標籤」
	—— 站型別系統的角度看，就是身份 4。
	你筆記 interface-完整深度講解 §3.1 身份 4 的範例
	if (obj instanceof Flyable) { ((Flyable)obj).fly(); }
	就是 Ex11 move() 這個模式本身。
*/
