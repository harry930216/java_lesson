package tw.harry.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import tw.harry.entity.Member;
import tw.harry.entity.Test;   // 自己看過：整支檔案沒用到 Test，這是老師複製貼上留下的「未使用 import」，可刪
import tw.harry.utils.HibernateUtil;

public class MemberDao {
	// 整體模式跟你寫過的 TestDao 一模一樣：開 session → 開交易 → 操作 → commit；出錯就 rollback。
	// 所以重複的不再逐行解釋，下面只標「跟 TestDao 不同 / 要特別注意」的點。

	// 新增一筆（INSERT）
	public void addMember(Member member) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.persist(member);  // persist：把新的 member 存進 DB

			// ===== 自己看過 =====
			// 注意：這裡「只」persist(member) 一個物件，並沒有另外去存 memberinfo。
			// 但因為 Member.memberinfo 標了 cascade = CascadeType.ALL，
			// 只要先前有用 member.setMemberinfo(...) 把關聯接好，Hibernate 會「連同 memberinfo 一起 INSERT」。
			// 驗收：跑起來看主控台印出的 SQL，應該會看到「兩條 INSERT」(member 一條、memberinfo 一條)。自己跑跑看。
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// 刪除一筆（DELETE）
	public void delMember(Member member) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.remove(member);  // remove：刪掉這筆 member（cascade = ALL → 它的 memberinfo 也會一起被刪）

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// 更新一筆（UPDATE）
	public void updateMember(Member member) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.merge(member);  // merge：把傳進來的物件狀態合併回 DB

			transaction.commit();
		} catch (Exception e) {
			System.out.println(e);  // 自己看過：只有這個方法多印了例外(其他 4 個方法沒有)，是老師除錯臨時加的。要不要統一風格自己決定
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// 用主鍵查單筆（SELECT by PK）
	public Member findById(long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			return session.get(Member.class, id);  // get：用主鍵抓一筆 Member，查不到回 null
		} catch (Exception e) {
		}
		return null;
	}

	// 查全部（SELECT all，用 HQL）
	public List<Member> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {

			String hql = "FROM Member";  // HQL：FROM 後面接「entity 類別名 Member」(大小寫同 Java 類別)，不是資料表名
			Query<Member> query = session.createQuery(hql, Member.class);
			return query.getResultList();

		} catch (Exception e) {
		}
		return null;
	}
	
	// 模糊查詢：回傳 account「包含」key 的會員清單。示範 HQL 帶條件 + 具名參數
	public List<Member> findByAccountLike(String key){
		try (Session session = HibernateUtil.getSessionFactory().openSession();){
			// ===== 自己看過 =====
			// :key 是「具名參數(named parameter)」佔位符，值用下面 setParameter 綁，不直接拼進字串。
			// → 這就是防 SQL injection 的關鍵：使用者輸入永遠被當「資料」，不會被當「SQL 語法」執行。
			//   對照 JDBC 的 PreparedStatement 用 ? 佔位，同一個道理。
			String hql = "FROM Member WHERE account LIKE :key";
			Query<Member> query = session.createQuery(hql, Member.class);
			// %key% = 包含；key% = 開頭符合；%key = 結尾符合。% 加在 Java 這邊，HQL 字串裡不放值
			query.setParameter("key", "%" + key + "%");
			return query.getResultList();
		}catch (Exception e) {
		}
		return null;
	}
}
