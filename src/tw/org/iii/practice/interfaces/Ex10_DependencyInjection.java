package tw.org.iii.practice.interfaces;

// 題 10 — 依賴注入（DI）：interface 當「插槽」
// 目標：自己手寫 Spring @Autowired 背後的概念——透過建構子塞實作,讓 Service 不認識具體 DB。
//
// 情境（這是 5/13 你讀過的 UserDB 範例,現在自己寫一遍）：
//
//   第 1 層 — 介面：
//     UserDB10 有 User10 findById(Long id);
//
//   第 2 層 — 三個實作：
//     JpaUserDB10   implements UserDB10
//       findById 印 "[JPA] 查 user id=" + id,並回傳 new User10("Jpa王", id)
//     MongoUserDB10 implements UserDB10
//       findById 印 "[Mongo] 查 user id=" + id,並回傳 new User10("Mongo王", id)
//     FakeUserDB10  implements UserDB10（測試用）
//       findById 不印任何 log,直接回傳 new User10("假人", id)
//
//   第 3 層 — Service：
//     UserService10
//       - 欄位 UserDB10 db;         ← 注意：欄位型別必須寫介面,不是寫具體類
//       - 建構子 UserService10(UserDB10 db) { this.db = db; }
//       - 方法 User10 getUser(Long id) { return db.findById(id); }
//
//   第 0 層 — 資料載體：
//     User10 有 name 欄位 + id 欄位 + 建構子 + toString() 印 "User(name, id)"
//
//   在 main：
//     - new UserService10(new JpaUserDB10()) 然後 getUser(1L) → 印 User
//     - new UserService10(new MongoUserDB10()) 然後 getUser(2L) → 印 User
//     - new UserService10(new FakeUserDB10()) 然後 getUser(3L) → 印 User
//
//     觀察：UserService10 程式碼一個字都沒改,搭三種 DB 跑出三種結果。
//
// 觀察重點（寫完回答）：
//   1. 為什麼 UserService10 的 db 欄位**必須**寫 UserDB10（介面）而不是 JpaUserDB10？寫成具體類會失去什麼？
//   2. 為什麼建構子要從外面收 UserDB10,而不是 UserService10 自己 new JpaUserDB10()？
//   3. FakeUserDB10 在什麼情境用？真實工作中對應的是什麼工具？（提示：跟測試有關）
//   4. db.findById(id) 這行,編譯期 JVM 看誰、執行期 JVM 看誰？
//   5. 如果業主明天要加 RedisUserDB10,UserService10 程式碼要改嗎？為什麼？
//   6. 這個模式對應 interface 的 5 個身份中的哪幾個？

public class Ex10_DependencyInjection {

	public static void main(String[] args) {
		UserService10 j = new UserService10(new JpaUserDB10());
		UserService10 m = new UserService10(new MongoUserDB10());
		UserService10 f = new UserService10(new FakeUserDB10());
		
		System.out.println(j.getUser(1L));
		System.out.println(m.getUser(2L));
		System.out.println(f.getUser(3L));
	
		
		
//		getUser(1L); // → 印 User
//	     - new UserService10(new MongoUserDB10()) 然後 getUser(2L) → 印 User
//	     - new UserService10(new FakeUserDB10()) 然後 getUser(3L) → 印 User
	}
}

interface UserDB10 {
	User10 findById(Long id);
}

class User10 {
	String name;
	Long id;
	
	public User10(String n,Long i) {
		name = n;
		id = i;
		
	}
	
	@Override
	public String toString() {
		return "User(" + name + ", " + id + ")";
	}
}

class JpaUserDB10 implements UserDB10 {
	@Override
	public User10 findById(Long id) {
		System.out.println("[JPA] 查 user id=" + id);
		return (new User10("Jpa王", id));
	}
}

class MongoUserDB10 implements UserDB10 {
	@Override
	public User10 findById(Long id) {
		System.out.println("[Mongo] 查 user id=" + id);
		return (new User10("Mongo王", id));
	}
}

class FakeUserDB10 implements UserDB10 {
	@Override
	public User10 findById(Long id) {
		return (new User10("假人", id));
	}
}

class UserService10 {
	UserDB10 db;
	public UserService10(UserDB10 db) { this.db = db; }
	public User10 getUser(Long id) { return db.findById(id); }
	
}

/* === 觀察結果 ===
Jpa 服務跑 getUser(1L) 輸出：
	[JPA] 查 user id=1          ← findById 內部印的 log
	User(Jpa王, 1)              ← println 印出 getUser 回傳的 User10
Mongo 服務跑 getUser(2L) 輸出：
	[Mongo] 查 user id=2
	User(Mongo王, 2)
Fake 服務跑 getUser(3L) 輸出：
	(Fake 不印 log)
	User(假人, 3)

→ 觀察兌現：UserService10 程式碼一字未改，搭三種 DB 跑出三種結果。

Q1 為什麼欄位型別必須寫介面：
	因為UserService10(UserDB10 db)才吃得到

	[Claude 補充] 你講的是「語法上吃得到」，但真正的設計理由是「解耦」：
	- 欄位寫 UserDB10（介面）→ db 能裝任何 UserDB10 的 subtype（Jpa/Mongo/Fake/未來的 Redis）
	- 欄位若寫 JpaUserDB10（具體類）→ UserService10 跟 JPA 焊死，會失去兩個能力：
	    (1) 換實作：想改用 Mongo 得改 UserService10 原始碼
	    (2) 注入假物件：測試時塞不進 FakeUserDB10
	→ 對應身份 3（橋梁）：UserService10 只認介面、不認具體類。

Q2 為什麼建構子收進來而不是自己 new：
	我不知道

	[Claude 解答] 假設 UserService10 自己 new JpaUserDB10()：
	- 「用哪個 DB」的決定權，就握在 UserService10 自己手上 → 又焊死 JPA
	- 想換 DB / 想測試，還是得改 UserService10 原始碼
	建構子收進來 = 把「決定用哪個 DB」的權力，交給外面的呼叫方（main，真實世界是 Spring）。
	這個「決定權從裡面移到外面」有正式名字：控制反轉（IoC, Inversion of Control）。
	用建構子收參數這個具體手法，叫建構子注入（Constructor Injection）。
	→ 對應身份 5（插槽）：UserService10 留一個空位，執行期讓外面塞。

Q3 FakeUserDB10 在什麼情境用、對應什麼工具：
	作錯誤測試? 我不知道

	[Claude 解答] 不是「錯誤測試」。FakeUserDB10 用在單元測試（unit test）：
	情境：你要測 UserService10.getUser 的邏輯。若用真的 JPA/Mongo —
	      要先把資料庫架起來、連線（慢），測試還會真的讀寫資料庫（弄髒資料、不穩定）。
	塞 FakeUserDB10 → 不碰真資料庫、回傳固定假資料 → 測試快、乾淨、可重複。
	真實工具：這種「替身物件」統稱 test double，FakeUserDB10 屬於 fake / stub。
	         業界常用 Mockito（自動產生假物件）、Spring 的 @MockBean。
	→ 這就是第 2 層特地放一個「不印 log、回固定值」的 Fake 的原因。

Q4 db.findById 編譯期 vs 執行期看誰：
	編譯看db 也就是UserDB10 執行看實體

	[Claude 補充] ✅ 對 — 補上機制名詞：
	- 編譯期：javac 看 db 的宣告型別 UserDB10 → 檢查「UserDB10 契約裡有沒有 findById」→ 有 → 過。
	- 執行期：JVM 看 db 實際指向的物件（Jpa/Mongo/Fake）→ 用 invokeinterface 指令、
	          透過該物件的 itable 找到那個 class 寫的 findById → 跑那一份。
	一句話：編譯期看型別、執行期看物件。
	跟 class 繼承對稱：class 用 invokevirtual + vtable，介面用 invokeinterface + itable。

Q5 加 RedisUserDB10 後 UserService10 要改嗎為什麼：
	不用 他還是一種UserDB10

	[Claude 補充] ✅ 對。RedisUserDB10 implements UserDB10 → 它是 UserDB10 的 subtype，
	塞得進建構子參數、塞得進 db 欄位。UserService10 從頭到尾只對著介面寫、不認具體類 → 一字不改。
	你只要做兩件事：(1) 新增 class RedisUserDB10 implements UserDB10、
	             (2) main 改成 new UserService10(new RedisUserDB10())。
	這就是開放封閉原則（OCP, Open-Closed Principle）：對擴充開放、對修改封閉。
	⚠️ 用詞提醒：你寫「一種 UserDB10」口語沒問題；但「塞不塞得進去」這層，
	            編譯器在意的精準術語是 subtype。

Q6 這模式對應 interface 5 個身份中的哪幾個：
	不知道

	[Claude 解答] 對照你筆記 3.1 節的 5 身份（規格／視角／橋梁／能力標籤／插槽）：
	主角兩個（你筆記身份 3、5 的範例，用的就是 UserService/UserDB，跟這題一模一樣）：
	- 身份 3 橋梁：UserService10 站中間，只認 UserDB10，不認 Jpa/Mongo/Fake
	               → 呼叫方與實作方不直接認識。
	- 身份 5 插槽：new UserService10(new JpaUserDB10()) vs (new FakeUserDB10())
	               → 同一個空位、執行期塞不同實作，UserService10 程式碼一字不變。
	次要也踩到：
	- 身份 1 規格：UserDB10 規定 findById，三個實作不寫就編譯炸。
	- 身份 2 視角：getUser 裡的 db 只看得到 UserDB10 列的 findById，看不到具體類私有成員。
	唯一沒碰到：身份 4 能力標籤 — 那要用 instanceof，是下一題 Ex11 的主題。

	總結:這題我現在看懂語法 但不知道這為什麼這樣設計

	[Claude 補充] 「為什麼這樣設計」一句話：為了讓 UserService10 不被任何一個具體資料庫綁死。
	整題只在做一件事 — 把「要做什麼」跟「實際誰來做、怎麼做」拆開：
	- UserDB10 介面 = 那條拆開的縫（你筆記說的「解耦縫」）
	- 欄位寫介面 + 建構子收進來 = 確保 UserService10 永遠站在「要做什麼」那側，碰不到「誰來做」
	設計的好處，就兌現在 Q5（換 DB 不改碼）和 Q3（測試能塞 Fake）。
	語法你已經懂了；設計動機就是這一句，其餘都是它的推論。
*/
