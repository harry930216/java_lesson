package tw.harry.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;

import tw.harry.entity.Order;

// OrderDao：訂單的資料存取「介面」。這次跟 TestDao/MemberDao 不一樣 —— 先定義介面，實作放在 OrderDaoImp。
//
// ===== 自己看過（兩個跟之前 DAO 不同的設計）=====
// 1) 介面 + 實作分離：對著「介面」寫程式(program to interface)，實作可抽換、好測試。
//    TestDao/MemberDao 直接寫成具體類別；這裡升級成「介面 OrderDao + 實作 OrderDaoImp」。
// 2) Session 由「外面傳進來」當參數：之前 TestDao 每個方法自己 openSession() 開關；
//    這裡 session 交給呼叫端管 → 呼叫端能把「多個 DAO 操作」包進「同一個交易」。誰開誰關，責任換人了。
public interface OrderDao {
	// 存一張訂單，回傳產生的主鍵 id
	Long save(Session session, Order order);

	// 用主鍵查一張訂單。回傳 Optional<Order>：用 Optional 包起來，逼呼叫端處理「查不到」的情況，
	// 而不是回 null 讓人忘了檢查就 NPE（呼應 Harry07 那個 null 順序 bug 的教訓）
	Optional<Order> findById(Session session, Long id);

	// 查訂單「並且一起把它的 items 載入」（之後實作大概會用 JOIN FETCH，避免 items 是 LAZY 時的 N+1／載入問題）
	Optional<Order> findByIdWithItems(Session session, Long id);

	// 刪除訂單
	void delete(Session session, Order order);

	// 查全部，start/size = 分頁（從第幾筆開始、抓幾筆）。第二個 order 參數看起來當查詢條件用，等實作出來才明朗
	List<Order> findAll(Session session, Order order, int start, int size);
}
