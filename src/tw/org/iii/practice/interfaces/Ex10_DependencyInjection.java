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
		new UserService10(new JpaUserDB10());
		getUser(1L); // → 印 User
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

/* === 觀察結果（寫完填這裡）===
Jpa 服務跑 getUser(1L) 輸出：
Mongo 服務跑 getUser(2L) 輸出：
Fake 服務跑 getUser(3L) 輸出：

Q1 為什麼欄位型別必須寫介面：

Q2 為什麼建構子收進來而不是自己 new：

Q3 FakeUserDB10 在什麼情境用、對應什麼工具：

Q4 db.findById 編譯期 vs 執行期看誰：

Q5 加 RedisUserDB10 後 UserService10 要改嗎為什麼：

Q6 這模式對應 interface 5 個身份中的哪幾個：

*/
