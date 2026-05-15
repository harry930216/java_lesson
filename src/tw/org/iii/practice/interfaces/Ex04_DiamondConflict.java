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
		AmbiguousClass a = new AmbiguousClass();
		a.greet();
	}
}

interface InterfaceA {
	default void greet() { System.out.println("A: hello");}
}

interface InterfaceB {
	default void greet() { System.out.println("B: hi");}
}

class AmbiguousClass implements InterfaceA, InterfaceB {
	@Override
	public void greet() {
		InterfaceA.super.greet();
		InterfaceB.super.greet();
	}
}

/* === 觀察結果 ===
greet() 輸出：
A: hello
B: hi

Q1 為什麼要寫 InterfaceA.super.greet() 而不能寫 super.greet()：
   super會跑去object

   [Claude 補充] — kernel 對，但缺「為什麼這就無解」
   - super 這個關鍵字「永遠」指 class hierarchy 的上一層（父 class）
   - AmbiguousClass 沒寫 extends → 預設父 class = Object
   - super.greet() 就是去 Object 找 greet()，找不到 → 編譯錯
   - 更關鍵：就算 Object 真有 greet()，那也是 Object 的，不是 InterfaceA 的
     → super 從來不會走進 interface hierarchy
   - 所以 Java 為了讓你「跨進 interface 那邊」呼叫某個 default，特地多設計
     XXX.super.method() 這個語法
   完整版：「因為 super 只能走 class hierarchy（會跑去 Object），
            無法到達 interface hierarchy。要呼叫 interface 的 default，
            Java 另外設計了 XXX.super.method() 語法。」

Q2 為什麼介面這裡需要指定，類別繼承不用指定：
   單/多繼承差異

   [Claude 補充] — kernel 對，展開才看出邏輯
   | 維度          | class 繼承 | interface 多實作        |
   |---------------|-----------|------------------------|
   | 上一層數量     | 永遠 1 個  | 可以 N 個              |
   | super 會不會歧義| 不會      | 會                     |
   | Java 怎麼處理   | 不需處理   | 強制要你報名字（不偷選） |
   為什麼不偷選 → C++ 鑽石爆炸的歷史教訓。Java 哲學：有歧義 → 強制 explicit。

[Claude 補充 — 衍生問題] 為什麼是 XXX.super.X，不是 super.XXX.X？
   1. Java 早就有「Qualifier.this / Qualifier.super」pattern（Java 1.0 就有）
      - Outer.this  = 外層類別的「我自己」（inner class 用）
      - Outer.super = 外層類別的「父」
      - InterfaceA.super = 透過 InterfaceA 視角的「父」
      → 同一條規則：Qualifier 永遠在前，被修飾的關鍵字在後
   2. Parser 要先知道「視角」才能解 super
      - XXX.super.method() → 先告訴編譯器「用 XXX 視角」，再解 super
      - super.XXX.method() → 編譯器先讀到 super 已經要決定意義，來不及重解
   3. super.XXX 已被「父 class 的成員」語法佔走（super.fieldName / super.method()）
      → 改設計成 super.InterfaceName 會撞名歧義
   一句話：不是任性決定，是「super.XXX 撞既有語法 + Qualifier.this 早有的 pattern」

[Claude 修正 — 「視角」這詞會誤導]
   先前用「用 XXX 視角來看 super」描述會讓人誤會「跳 2 層」（先到 XXX 再到 XXX 的 super）。
   正確心智模型：
     XXX.super  ≠ XXX 的 super （= 跳 2 層 ❌）
     XXX.super  =  我的 super 之中、名字是 XXX 的那一個 （= 1 層，只是點名 ✅）

   對照同 pattern：
     Outer.this  = 我的 this 之中、外層那一個（不是「Outer 的 this」）
     XXX.super   = 我的 super 之中、叫 XXX 的那一個（不是「XXX 的 super」）

   口語比喻：你有 2 個爸爸（多重實作）。喊「爸」沒人知道指誰，
            所以說「我爸 Bob」(`Bob.super`) — Bob 不是爸的爸，Bob 就是爸本人，
            前面加名字只是幫他點名。
   → 不是跳 2 層，是「從多個 1 層中指定哪個」。

[Claude 補充 — 設計本質：命名妥協]
   `XXX.super.method()` 整個語法是 Java 8 在「現有語法都被佔光」的情況下，
   找出最後一個沒被佔的格位塞進去的：

   | 想用的寫法            | 為什麼用不了                                    |
   |----------------------|-----------------------------------------------|
   | XXX.method()         | 被 static 呼叫佔走（Math.sqrt()）                |
   | super.method()       | 被 class hierarchy 的 super 佔走                 |
   | super.XXX.method()   | 撞 super.fieldName（會被解讀成「父 class 的 field」） |
   | XXX::default.method()| Java 8 已用 :: 做 method reference（String::length）|
   | default(XXX).method()| 全新關鍵字組合，要動 lexer/parser，破壞向後相容       |
   | XXX.super.method() ✅| 借用早已存在的 Outer.this/Outer.super pattern → 唯一沒撞名的格位 |

   Java 不是「這語法最美」，是「這是唯一還沒被用掉的縫」。
   順便還能宣稱「這跟 1.0 就有的 Outer.super 同 pattern，不算新規則」一舉兩得。

   成熟語言的通病：
     新功能加進去時，不是從空白設計，而是「還剩什麼語法槽？哪個塞進去最不破壞既有 code？」
     - C++ << 拿來當 stream 輸出 → 因為位元左移可 overload，剛好沒撞名
     - Python yield 變 generator → 因為它原本完全沒語法意義
     - JS let / const → 不能用 var2，但 let 在當時沒被當關鍵字
     - Java lambda 用 -> → 因為 => 太像賦值會混淆

   下次看到怪語法先想兩件事：
     1. 同個位置有沒有別的東西已經佔了？
     2. 有沒有更早期的相似 pattern 它在沿用？
   通常答案是「是」+「是」。語法設計 99% 是「約束滿足」問題，不是美感問題。
*/
