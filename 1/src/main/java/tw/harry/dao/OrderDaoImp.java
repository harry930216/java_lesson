package tw.harry.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;

import tw.harry.entity.Order;

// OrderDaoImp：OrderDao 介面的實作（implements OrderDao）。
// 注意：方法裡「沒有」openSession/beginTransaction/commit —— 因為 session 由呼叫端傳進來、交易也由呼叫端控制。
public class OrderDaoImp implements OrderDao {

	@Override
	public Long save(Session session, Order order) {
		session.persist(order);   // 存進去；persist 之後 order 的 id 會被 DB 回填
		return order.getId();     // 把產生的主鍵回傳給呼叫端
	}

	@Override
	public Optional<Order> findById(Session session, Long id) {
		Order order = session.get(Order.class, id);  // 查不到會是 null
		return Optional.ofNullable(order);           // ofNullable：是 null 就變 Optional.empty()，不是 null 就包起來
	}

	@Override
	public Optional<Order> findByIdWithItems(Session session, Long id) {
		// ===== 自己看過（JOIN FETCH = 解 N+1 的關鍵）=====
		// 這就是介面裡那個「WithItems」：用一條 HQL 把 order「連同它的 items」一次撈回來。
		//   LEFT JOIN FETCH o.items → 不只 JOIN，還「FETCH」= 把關聯的 items 一起載入（即使 items 標 LAZY 也立刻載）。
		//   對照 Harry08 的 N+1：那邊撈 N 筆各自再查；這邊一條 SQL 解決，沒有 N+1。
		//   LEFT(外連接)：就算這張單一個 item 都沒有，order 本身還是撈得到（INNER JOIN 會把沒 item 的單濾掉）。
		// 三個 """ 是 Java 15+ 的「文字區塊(text block)」：多行字串不用一直 + "\n"，HQL/SQL 排版好讀。
		String hql = """
				SELECT o
				FROM Order o
				LEFT JOIN FETCH o.items
				WHERE o.id = :id
				""";
		Order order = session.createQuery(hql, Order.class)
				.setParameter("id", id)
				.uniqueResult();  // uniqueResult：預期最多一筆（用主鍵查），回單一物件或 null
		return Optional.ofNullable(order);
	}

	@Override
	public void delete(Session session, Order order) {
		session.remove(order);  // 刪整張 order；因 Order.items 是 cascade=ALL，底下 items 會連帶被刪
	}

	@Override
	public List<Order> findAll(Session session, Order order, int start, int size) {
		// 分頁查詢：ORDER BY id DESC（新到舊）。注意 order 這個參數老師這版沒用到（之後可能拿來當篩選條件）
		String hql = """
				SELECT o
				FROM Order o
				ORDER BY o.id DESC
				""";
		return session.createQuery(hql, Order.class)
				.setFirstResult(start)   // 跳過前 start 筆（= SQL 的 OFFSET）
				.setMaxResults(size)     // 最多抓 size 筆（= SQL 的 LIMIT）
				.list();
	}

	//-------------------------- 老師的 HQL 練習方法（不在 OrderDao 介面裡，純練手）--------------------------
	// 自己看過：這幾個沒有 @Override —— 介面 OrderDao 沒宣告它們，是直接加在實作類別上的額外方法。

	// test1：用 WHERE 依客戶名查訂單
	public List<Order> test1(Session session, String cname) {
		String hql = """
				SELECT o
				FROM Order o
				WHERE o.customer = :cname
				""";
		return session.createQuery(hql, Order.class).setParameter("cname", cname).list();
	}

	// test2：依下單時間 odate 排序，全撈
	public List<Order> test2(Session session) {
		String hql = """
				SELECT o
				FROM Order o
				ORDER BY o.odate
				""";
		return session.createQuery(hql, Order.class).list();
	}

	// test3：JOIN 到 items、用品名過濾。自己看過：老師只寫了 HQL 字串，還沒拿去 createQuery 執行，是半成品
	public void test3() {
		String hql = """
				SELECT o
				FROM Order o
				JOIN o.items i
				WHERE i.pname = :pname
				ORDER BY o.id
				""";
	}

}
