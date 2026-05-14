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

	}

	static void move(Object animal) {

	}
}

interface Flyable11 {

}

interface Swimmable11 {

}

interface Walkable11 {

}

class Duck11 implements Flyable11, Swimmable11, Walkable11 {

}

class Eagle11 implements Flyable11, Walkable11 {

}

class Fish11 implements Swimmable11 {

}

/* === 觀察結果（寫完填這裡）===
move(Duck11)  輸出：
move(Eagle11) 輸出：
move(Fish11)  輸出：
move("字串")  輸出：

Q1 為什麼 instanceof 後要強轉（編譯 vs 執行）：

Q2 一物件能同時被 3 個 instanceof 判斷 true 嗎為什麼：

Q3 為什麼 move 參數寫 Object 不寫共同父類：

Q4 字串那個 case 編譯不會炸嗎：

Q5 instanceof 對應 interface 5 個身份中的哪個：

*/
