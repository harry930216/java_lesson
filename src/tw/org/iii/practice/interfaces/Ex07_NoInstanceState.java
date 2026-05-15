package tw.org.iii.practice.interfaces;

// 題 7 — 沒有 instance state 根定理（踩雷型）
// 目標：實際撞編譯錯,內化「interface 沒有 instance state」這條根定理衍生的所有規則。
//
// 情境：
//   下面 Conflict07 介面有 3 個故意踩雷的寫法,目前全部註解掉。
//   一個一個取消註解,觀察編譯訊息,記下來再重新註解掉。
//
//   雷 1：宣告一個沒給初始值的欄位
//   雷 2：宣告一個建構子
//   雷 3：在 default 方法裡修改介面欄位的值
//
// 然後寫一個正確版的 Config07 介面：只放常量 + 一個方法簽章。
// MyApp07 實作 Config07,把方法寫出來。
//
// 在 main:
//   - 印出 Config07.MAX_USERS（從介面名直接讀常量）
//   - 嘗試寫 Config07.MAX_USERS = 999;（觀察編譯訊息）
//   - 嘗試寫 Config07 c = new Config07();（觀察編譯訊息）
//   - new MyApp07() 然後呼叫方法
//
// 觀察重點（寫完回答）：
//   1. 雷 1 的編譯訊息是什麼？為什麼介面欄位必須給初始值？
//   2. 雷 2 的編譯訊息是什麼？為什麼介面不能有建構子？
//   3. 雷 3 的編譯訊息是什麼？這跟「介面欄位隱式 final」有什麼關係？
//   4. 為什麼 Config07.MAX_USERS = 999 編譯炸？
//   5. 為什麼 new Config07() 編譯炸？
//   6. 用一句話總結「沒有 instance state」這 5 個字怎麼推出上`面所有規則。

public class Ex07_NoInstanceState {

	public static void main(String[] args) {
		System.out.println(Config07.MAX_USERS);
		
	}
}

//interface Conflict07 {
//
//	// === 雷 1 === 取消註解觀察錯誤,觀察完再註解回去
//	int noInitValue;
//
//	// === 雷 2 === 取消註解觀察錯誤,觀察完再註解回去
//	Conflict07() { }
//
//	// === 雷 3 === 取消註解觀察錯誤,觀察完再註解回去
//	int counter = 0;
//
//	default void increment() {
//		counter = counter + 1;
//	}
//}

interface Conflict07 {

	// === 雷 1 === 取消註解觀察錯誤,觀察完再註解回去
	int MAX_USERS = 500;

	// === 雷 2 === 取消註解觀察錯誤,觀察完再註解回去
	void Conflict07();

	// === 雷 3 === 取消註解觀察錯誤,觀察完再註解回去
	int counter = 0;

	default void increment() {
//		counter = counter + 1;
	}
	// 這邊怎麼修都沒用 本來就不能修改到欄位 因為final
}

 
interface Config07 {
	int MAX_USERS = 500;
	void test();
}

class MyApp07 implements Config07 {
	@Override
	public void test() {
		// TODO 自動產生的方法 Stub
	System.out.println("test");	
	}
}

/*
 * === 觀察結果 ===
 * 雷 1 編譯訊息： The blank final field noInitValue may not have been initialized
 * 雷 2 編譯訊息： Interfaces cannot have constructors
 * 雷 3 編譯訊息： The final field Conflict07.counter cannot be assigned
 *
 * Config07.MAX_USERS 印出 = 500
 * Config07.MAX_USERS = 999 編譯訊息： The final field Config07.MAX_USERS cannot be assigned
 * new Config07() 編譯訊息：           Cannot instantiate the type Config07
 *
 * Q1 為什麼介面欄位必須給初始值：
 *    因為沒其他地方可給了 不給就無值可用(或是錯的)
 *
 *    [Claude 補充] ✅ kernel 對 — 完整邏輯鏈
 *    1. 一般 class 的 field 可以在 constructor 裡指派
 *    2. interface 沒 constructor（雷 2 證明）
 *    3. 也沒 instance init block
 *    4. 唯一機會 = 宣告當下直接給
 *    5. 加上自動 final → 之後也不能改
 *    → 「宣告時不給 = 永遠沒機會給」
 *
 * Q2 為什麼介面不能有建構子：
 *    因為final寫死了 建構子沒必要
 *
 *    [Claude 補充] ❌ 概念混了 — final 是 field 的事，跟「能不能 new」是兩回事
 *    真正原因（兩條獨立的）：
 *    1. 介面是「契約」，不是「可實例化的型別」
 *       → 不能 new → 不需要 constructor 來「建造」實例
 *    2. 介面欄位都是 static
 *       → class load 時就初始化好了，不需要 constructor 走流程
 *    跟 final 沒直接關係。final 是「值不能改」，constructor 是「建實例的流程」。
 *    是兩件事。
 *
 * Q3 雷 3 的編譯訊息跟隱式 final 的關係：
 *    一樣的概念
 *
 *    [Claude 補充] 太籠統 — 沒講出怎麼一樣
 *    明確版：
 *    - 雷 3 訊息: The final field Conflict07.counter cannot be assigned
 *    - 因為 interface field 自動 public static final
 *    - final field 一旦初始化（在 int counter = 0; 那行）就不能再賦值
 *    - default method 內 counter = counter + 1 試圖修改 final field → 編譯錯
 *    → 這個錯誤的根因就是「隱式 final」這條規則
 *    關鍵字：「隱式 final」是因，「不能在 default method 裡改」是果。
 *
 * Q4 為什麼 Config07.MAX_USERS = 999 編譯炸：
 *    The final field Config07.MAX_USERS cannot be assigned
 *
 *    [Claude 補充] 只貼了訊息，沒答 why
 *    完整答：
 *    MAX_USERS 在 interface 內宣告，自動套上 public static final 三個修飾子。
 *    final 已經把它鎖死成「一次設好、不可變」，所以 = 999 試圖修改 → 編譯擋。
 *    → 跟 Q3 同根因（隱式 final）。
 *
 * Q5 為什麼 new Config07() 編譯炸：
 *    Cannot instantiate the type Config07 / 就沒東西讓你建阿
 *
 *    [Claude 補充] ✅ kernel 對 — 講白話一點
 *    - interface 是契約，沒具體實作（method 都 abstract，或 default 不能存狀態）
 *    - new 是「產生實例」，需要實作才能跑
 *    - interface 不夠完整 → 不能 new
 *
 * Q6 「沒有 instance state」怎麼推出所有規則：
 *    不知道這題在問什麼
 *
 *    [Claude 解答] 題目想測：「沒有 instance state」這 5 個字（根定理）
 *                          能不能推出前面所有規則。
 *
 *    完整推導鏈：
 *
 *    根定理：interface「沒有 instance state」
 *         （介面實例不能有自己的狀態欄位）
 *       │
 *       ├─→ 欄位不能屬於實例 → 必須是 static（屬於 class 自己）
 *       │       └─→ Q4 為什麼 MAX_USERS 是 static
 *       │
 *       ├─→ static + 介面當「常量定義」用 → 自動 final
 *       │       ├─→ 雷 1：必須給初始值（沒 constructor 可用、之後改不了）
 *       │       ├─→ 雷 3：default method 不能修改（final 鎖死）
 *       │       └─→ Q4：MAX_USERS = 999 編譯炸（同上）
 *       │
 *       ├─→ 沒 instance state → 不需要 constructor 來初始化實例
 *       │       └─→ 雷 2：不能寫 constructor
 *       │
 *       └─→ method 都 abstract / default 不能存狀態 → 不夠完整
 *               └─→ Q5：不能 new Config07()
 *
 *    一句話總結：
 *      「沒有 instance state」→ 欄位變 static → static 變 final
 *                            → 沒 constructor → 不能 new
 *      這條鎖鏈中只要拒絕第一條，後面所有規則一個一個自然倒下。
 *
 *    → 「沒有 instance state」就是 interface 整套規則的最上游根因。
 *      記住這 5 個字，下面所有規則都不用背。
 */
