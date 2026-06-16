package tw.harry.tutor;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import tw.harry.entity.Test;
import tw.harry.utils.HibernateUtil;

// 拿泛型來用

public class Harry03 {
	
	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
		 Transaction transaction = session.beginTransaction();  // 註：純查詢其實不需要交易，這裡開了也無妨
		 
		 String sql = """
		 			SELECT * FROM test
		 			""";
		 NativeQuery<Test> query = session.createNativeQuery(sql, Test.class);  // 多給 Test.class → Hibernate 自動把每一列「塞進一個 Test 物件」
		 System.out.println(Test.class);
		 List<Test> list = query.getResultList();  // 直接拿到 List<Test>，不再是 Object[]
		 System.out.println(list.size());
		 for (Test test : list) {
			 System.out.printf("%s:%s:%s:%s\n", test.getId(), test.getCname(), test.getTel(), test.getBirthday());  // 用 getter 取值：型別安全、看得懂，不用記欄位順序（對比 Harry02 的 data[0..3]）
		 }
		 
		 transaction.commit();
		}
	}

}


/*

createNativeQuery(sql, Test.class)   // 你交出 Test 這個型別
        │
        ▼
NativeQuery<Test>                    // 查詢物件就被標記成「會吐 Test」
        │  .getResultList()
        ▼
List<Test>                           // 結果清單自然是「一串 Test」
        │  for (Test test : list)
        ▼
test  →  已經是 Test，直接 .getCname()

*/

/*
 為什麼又要 <Test> 又要 Test.class？關鍵是「兩個不同的時間點」
 ──────────────────────────────────────────────
 編譯期(compile-time)：你打字、build 的時候，由「編譯器」把關
 執行期(runtime)    ：程式真的跑起來，Hibernate 連 DB、建物件都在這

 <Test>（泛型標籤）
   - 只活在編譯期：給編譯器檢查型別、讓你免轉型
   - 編譯完就被「擦掉」→ 型別擦除 (type erasure)
   - 證據：List<String> 和 List<Test> 在 runtime 是同一種 List，分不出來
           (a.getClass() == b.getClass() 會印 true)

 Test.class（Class 物件）
   - 是真實物件，執行期一直都在
   - 帶著 Test 的完整身分(欄位、註解…)，是執行期的「設計圖 / 身分證」

 為什麼 Hibernate 非要 Test.class：
   - 它在執行期才建物件，那一刻 <Test> 早被擦掉、它根本看不到
   - 只給 <Test>   → 它不知道要 new 什麼
   - 給 Test.class → 它才有依據 new Test()、再把每一欄塞進去
     (拿 Test.class new 物件、塞欄位用的技術就是 反射 reflection = Harry04 主題)

 分工：<Test> 顧「編譯期的型別安全、免轉型」；
       Test.class 顧「執行期 Hibernate 真的建得出物件」。
       同一個 Test，兩個時間點各要一份。
*/

/*
 這要會到哪？（反射 + Hibernate 的學習深度）

 1. 反射(reflection) 分兩層，知道這個就夠，不用會自己手寫反射：
    - Class 物件 = 架構圖本身(入口)；用「名字.class」取得(如 Test.class)
      (也可 obj.getClass()、Class.forName("...")，但知道 .class 即可)
    - 反射 = 拿著這張圖，能「讀它 + 照它動態操作」(建物件、讀寫欄位、呼叫方法) 的能力
    → 一句話：.class 拿到圖，反射就是「照圖動態做事」

 2. Hibernate 當黑箱(black box)用就好：
    - 要知道它「做什麼(what)」：拿 entity + Test.class + 設定，用反射把「物件 ↔ 資料表」
      來回搬，省掉你手寫一堆 JDBC/SQL
    - 不用知道它「怎麼做(how)」：內部 cache、怎麼產 SQL、反射細節… 當黑箱
    - 守住界線的理由：它出錯/變慢時，知道「大概哪一層在做事」才 debug 得動
      (這是「會用」和「會修」的差別；現在會用就夠，踩到坑再往內挖一層)

 結論：知道它「做什麼」，不用知道「怎麼做」—— 這就是用框架的正確深度。
*/
