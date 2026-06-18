package tw.harry.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;   
import tw.harry.entity.Test;
import tw.harry.utils.HibernateUtil;

public class TestDao {
	// 新增一筆（INSERT）
	public void addTest(Test test) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.persist(test); // persist：把新物件存進 DB（INSERT）

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// 刪除一筆（DELETE）
	public void delTest(Test test) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.remove(test); // remove：刪除這筆（DELETE）

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// 更新一筆（UPDATE）
	public void updateTest(Test test) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.merge(test); // merge：把傳進來的物件狀態合併回 DB（UPDATE）

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// 用主鍵查單筆（SELECT by PK）；注意：查詢類不一定要開交易
	public Test findById(long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			return session.get(Test.class, id); // get：用主鍵抓一筆，查不到回 null
			// 回傳一個 Test 物件（就一個，不是清單）
			
			// 1 harry1 111 1999-01-02

			/*
			 * Test t = session.get(Test.class, 1); 
			 * t.getId(); // 1 
			 * t.getCname(); // "harry1" 
			 * t.getTel(); // "111" 
			 * t.getBirthday(); // "1999-01-02"
			 */

		} catch (Exception e) {

		}
		return null;
		// 這裡要去了解
	}

	// 查全部（SELECT all，用 HQL）
	public List<Test> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {

			 String hql = "FROM Test";
			 Query<Test> query = session.createQuery(hql, Test.class);
			 return query.getResultList();
			 
		} catch (Exception e) {

		}
		return null;
	}
}

/*
 為什麼 HQL「FROM Test」設計得像 SQL，但其實是查物件(entity)？

 HQL = Hibernate Query Language：長得像 SQL，但作用在「物件」上的查詢語言。

 為什麼故意像 SQL：
   1. 零學習成本：大家都會 SQL，看 FROM Test 立刻懂，不用學新語法
   2. 不重新發明：過濾/排序/聚合/關聯，SELECT-FROM-WHERE 本來就是最成熟的查詢表達方式
   3. 最後要翻成 SQL：HQL 會被 Hibernate 翻成真 SQL 送 DB，語法相近好翻、好預期

 關鍵差別(像歸像，對象不同)：
                NativeQuery(真SQL, Harry02/03)   HQL(findAll)
   作用在        資料表、DB 欄位                   entity 類別、Java 欄位
   FROM 後接     表名 test                         類別名 Test (大小寫跟 Java 類別)
   回傳          列(rows)                          物件 List<Test>
   → 所以這裡寫 "FROM Test" 不是 "FROM test"：HQL 認的是 entity 名

 那為什麼不直接寫 SQL？HQL 多兩個好處：
   1. 跨資料庫可攜：同一句 HQL，Hibernate 依連的 DB 翻成各自方言(MySQL/Oracle/PostgreSQL)
   2. 物件導向：用類別/欄位名思考，回來直接是物件，不用像 Harry02 自己對映 Object[]

 一句話：HQL 借 SQL 的外觀和表達力，但把對象從「表」換成「物件」，再加跨 DB + 物件導向。
 
 他還是作用在DB上 他還是作用在DB上 他還是作用在DB上
 他還是作用在DB上 他還是作用在DB上 他還是作用在DB上
 他還是作用在DB上 他還是作用在DB上 他還是作用在DB上
 
*/

/*
 延伸討論：JPQL vs HQL、SQL 的份量、DB vs 應用層責任邊界

 ① HQL 比較強，為什麼還需要 JPQL？→ 標準 vs 廠商綁定(vendor lock-in)
   - JPQL = JPA 標準，換任何實作(Hibernate/EclipseLink)都能用 → 可攜
   - HQL  = Hibernate 私有擴充，用了就綁死 Hibernate
   - 原則：能用 JPQL 表達就用 JPQL，真需要 Hibernate 特有功能再用 HQL
   - (像標準插座 vs 特規插座：特規多功能但只配那家電器)

 ② 「會 SQL 就會這些」對一半
   - 對：SQL 是地基、最保值(活得比任何 ORM 久)，最該扎實
   - 但 ORM 多了「物件層」觀念與坑：entity 生命週期、persist/merge、
     lazy/eager 載入、N+1 query、cascade、dirty checking
   - 會 SQL ≠ 自動會用好 ORM

 ③ 責任邊界：DB 該做多少？(經典 trade-off，兩派各有道理)
   資料庫派(老師：能做就放DB，用 view/SP)    應用層派(現代後端主流)
   優點 靠近資料快/多應用一致/權限安全         好測試/版控/重構/易水平擴展/不綁DB
   缺點 難測試版控/DB難擴展/SP綁特定DB          重運算可能慢/ORM陷阱(N+1)

   實務切法(Spring Boot 路線偏這個)：
   - DB 顧：資料完整性(constraint/unique/外鍵)、索引、交易 →「資料不能壞」是DB本職
   - 應用層顧：商業邏輯(business logic)、流程怎麼跑 → 放 Java 好測試維護
   - View 溫和常用；Stored Procedure 現代少用，只在重效能大量批次才下放

   一刀切判準：
   「資料該長怎樣、不能壞」→ DB ；「業務規則怎麼算、流程怎麼跑」→ 應用層

 結論：SQL=地基(都要會)；JPQL/HQL=物件層查詢(需要時會用)；
       邊界沒絕對，你走 Spring Boot 偏應用層派，但「資料完整性歸 DB」這條線要守。
*/
