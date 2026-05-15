package tw.org.iii.practice.interfaces;

// 題 8 — 方法參數抽象化（避免重載地獄）
// 目標：對比「具體型別當參數要重載 N 個方法」vs「抽象型別當參數一個方法吃全部」。
//
// 情境：
//   Eater08 介面：void eat();
//   三個實作：
//     Dog08  → eat() 印 "狗啃骨頭"
//     Cat08  → eat() 印 "貓吃魚"
//     Bird08 → eat() 印 "鳥啄蟲"
//
//   寫法 A（重載地獄）：在 Ex08 類別內寫三個 static 方法
//     feedBadly(Dog08 d)   → d.eat()
//     feedBadly(Cat08 c)   → c.eat()
//     feedBadly(Bird08 b)  → b.eat()
//
//   寫法 B（抽象參數）：在 Ex08 類別內寫一個 static 方法
//     feedNicely(Eater08 e) → e.eat()
//
//   在 main：
//     - 各 new 一隻動物
//     - 用寫法 A 餵三隻
//     - 用寫法 B 餵同樣三隻
//     - 觀察兩種寫法的結果是否一樣
//
//   進階觀察：
//     假設業主突然要你加一個新動物 Fish08 implements Eater08（eat 印 "魚吃藻"）
//     - 寫法 A 需要做什麼？
//     - 寫法 B 需要做什麼？
//
// 觀察重點（寫完回答）：
//   1. 兩種寫法輸出一樣嗎？為什麼？
//   2. 加 Fish08 後,哪種寫法不用改原本的 feed 方法？為什麼？
//   3. 寫法 B 為什麼能「一個方法吃多種動物」？編譯期看誰、執行期看誰？
//   4. 用一句話說明「方法參數寫介面型別」解鎖了什麼能力。

public class Ex08_ParameterAbstraction {

	public static void main(String[] args) {
		Dog08 d = new Dog08();
		Cat08 c = new Cat08();
		Bird08 b = new Bird08(); 
		Fish08 f = new Fish08();
		
		feedBadly(d);
		feedBadly(c);
		feedBadly(b);
		feedBadly(f);
		
		System.out.println("-----------------");
		
		feedNicely(d);
		feedNicely(c);
		feedNicely(b);
		feedNicely(f);
		
	}

	// 寫法 A：重載地獄（請補三個 feedBadly 方法）
	//  在 Ex08 類別內寫三個 static 方法
	//  feedBadly(Dog08 d)   → d.eat()
	//  feedBadly(Cat08 c)   → c.eat()
	//  feedBadly(Bird08 b)  → b.eat()

	static void feedBadly(Dog08 d) {
		d.eat();
	}
	
	static void feedBadly(Cat08 c) {
		c.eat();
	}
	
	static void feedBadly(Bird08 b) {
		b.eat();
	}
	
	static void feedBadly(Fish08 f) {
		f.eat();
	}
	
	// 寫法 B：抽象參數（請補一個 feedNicely 方法）
	static void feedNicely(Eater08 e) {
		e.eat();
	}
}

interface Eater08 {
	void eat();
}

class Dog08 implements Eater08 {
	@Override
	public void eat() {
		System.out.println("狗啃骨頭");
		
	}
}

class Cat08 implements Eater08 {
	@Override
	public void eat() {
		System.out.println("貓吃魚");
		
	}
}

class Bird08 implements Eater08 {
	@Override
	public void eat() {
		System.out.println("鳥啄蟲");
		
	}
}

class Fish08 implements Eater08 {
	@Override
	public void eat() {
		System.out.println("魚吃餌");
		
	}
}
// 進階：請自己加一個 Fish08 implements Eater08

/* === 觀察結果 ===
寫法 A 四隻動物輸出：
   狗啃骨頭
   貓吃魚
   鳥啄蟲
   魚吃餌

寫法 B 四隻動物輸出：（與 A 完全一樣）
   狗啃骨頭
   貓吃魚
   鳥啄蟲
   魚吃餌

加 Fish08 後寫法 A 要改：
   ✅ 我實際做的：必須新增第 4 個 overload
      static void feedBadly(Fish08 f) { f.eat(); }
   → 這就是「重載地獄」的實證 — 每加一種動物都要回頭改 Ex08 類別

加 Fish08 後寫法 B 要改：
   完全不用改 feedNicely
   只要新增 Fish08 class（implements Eater08）
   → feedNicely(Eater08 e) 自動接受 Fish08（因為 Fish08 是 Eater08 一員）

Q1 兩種寫法輸出一樣嗎為什麼：
   一樣 因為引用的方法一樣 都寫好了

   [Claude 補充] ✅ kernel 對
   兩種寫法最終呼叫的都是同一個物件身上的同一個 eat()。
   差別只在「接收型別」不同（Dog08 vs Eater08），但呼叫到的實作是同一個 → 輸出一樣。
   接收型別差異不改變物件本身的能力。

Q2 加 Fish08 後哪種寫法不用改 feed：
   B

   [Claude 補充] ✅ 對 — 而且你用實作親自證明了
   B (feedNicely) 用 Eater08 抽象參數 → Fish08 是 Eater08 一員 → 自動接受
   A (feedBadly) 用具體型別 → Fish08 不在原本三個範圍 → 必須新增 overload
   你實際幫 A 加了 feedBadly(Fish08 f) — 這就是「重載地獄」的實證。

Q3 寫法 B 為什麼能吃多種動物（編譯期 vs 執行期）：
   我真不知道 但我記得是這樣
   編譯的時候 介面做了連結 有點像看宣告那樣
   這我真的不懂 看記憶體6大區塊 也太細

   [Claude 解答] 不用扯 6 大區塊，兩階段機制就夠

   階段 1（編譯期，javac）
   - 看 feedNicely(Eater08 e) 的參數宣告：型別是 Eater08
   - 看呼叫 feedNicely(d) 中 d 的型別：Dog08
   - 檢查：Dog08 是 Eater08 的 subtype 嗎？（型別相容檢查，JLS §4.10.2）
       Dog08 implements Eater08 → 是 ✅ → 編譯通過

   ※「subtype」≠ 教科書狹義「is-a」(taxonomic)
     JLS §4.10.2 規定：direct supertype 包含 direct superinterface。
     所以 implements 也創造 subtype 關係（與 extends 同等地位）。
     設計語意上 Dog08「能 eat」(can-do)，但型別系統層面 Dog08 是 Eater08 的 subtype。
     編譯器只認 subtype，不在意你語意上叫它 is-a 或 can-do。

   - 進到方法體內，編譯器只記得 e 是 Eater08（不知道實際是 Dog08）
   - 看到 e.eat() → 編譯成 bytecode: invokeinterface Eater08.eat:()V

   階段 2（執行期，JVM）
   - JVM 進到 feedNicely 內
   - 看 e 實際指向的物件（heap 上的 Dog08 實例）
   - 找 Dog08 class 的 eat() 實作（透過 itable / vtable）
   - 跳轉執行 → 印「狗啃骨頭」

   對照表：
   | 階段     | 看哪個型別                | 決定什麼                           |
   |----------|--------------------------|-----------------------------------|
   | 編譯期   | 宣告型別 (Eater08 e)     | 能不能呼叫（語法檢查 + 型別相容）    |
   | 執行期   | 實際物件 (Dog08/Cat08...)| 跑哪份 eat()（動態分派）            |

   → 編譯期擋「不是 Eater08 的東西」，執行期決定「實際跑誰的 eat()」。
   → 這跟 Ex02 練過的「Flyable b = new Bird() 兩階段」是同一個機制，
     只是場景從變數宣告搬到方法參數。

Q4 一句話：方法參數寫介面型別解鎖什麼能力：
   傳入參數(reference type 決定實作方法)

   [Claude 補充] 方向對，精準版：
   「方法參數寫介面型別」解鎖「一個方法吃任意實作」的能力
   — 未來新增實作不用回頭改這個方法。

   這是 OOP 的 OCP (Open-Closed Principle，開放封閉原則)：
     對擴充開放（可以加新動物），對修改封閉（不用改舊方法）

   你今天加 Fish08：
     A 違反 OCP：要回去改舊 code（加新 overload）
     B 符合 OCP：完全不用改舊 code，只新增 Fish08 class 即可

   口訣：「Program to interface, not implementation.」
        對抽象寫程式，不對具體寫程式。

[Claude 補充 — 衍生問題：extends 和 implements 在編譯器看來等價嗎？]
   答：對。在「subtype 替代性」這層完全通用。

   編譯器不在意你寫 extends 還是 implements，只在意：
     「目標位置需要 X 型別，你給的東西是不是 X 的 subtype？」

   要分清「subtype 關係通用」≠「能繼承到的東西通用」：

   |                       | extends                | implements                       |
   |-----------------------|------------------------|----------------------------------|
   | subtype 關係          | ✅ 創造                | ✅ 創造（同等地位，JLS §4.10.2）  |
   | 帶 instance state     | 可以（field 繼承）      | 不行（interface 沒 instance state）|
   | 帶 method body        | 可以（除非 abstract）   | 只能透過 default method           |
   | 數量限制              | 只能 1 個（單繼承）      | 多個（多實作）                     |

   → 機制有差，但 subtype 那層完全平等。
   → 多型、方法參數型別、變數宣告型別、return 型別 — 這些情境 extends 和 implements 通用。

   實際例子（兩段 code 輸出完全一樣）：

   情境 A：用 extends
     abstract class Animal { abstract void sound(); }
     class Dog extends Animal { void sound() { ... } }
     void play(Animal a) { a.sound(); }
     play(new Dog());          ← OK，Dog 是 Animal 的 subtype

   情境 B：用 implements
     interface SoundMaker { void sound(); }
     class Dog implements SoundMaker { public void sound() { ... } }
     void play(SoundMaker s) { s.sound(); }
     play(new Dog());          ← OK，Dog 是 SoundMaker 的 subtype

   兩段在「play 方法接收 Dog」這層的判斷邏輯完全一樣。
*/
