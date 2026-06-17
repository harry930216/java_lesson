package tw.harry.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tw.harry.dao.OrderDao;
import tw.harry.dao.OrderDaoImp;
import tw.harry.entity.Order;
import tw.harry.entity.OrderItem;
import tw.harry.utils.HibernateUtil;

// OrderServiceImp：OrderService 的實作。重點：交易在「這一層」開關，DAO 只收 session 做事。
public class OrderServiceImp implements OrderService {
	private OrderDao dao = new OrderDaoImp();  // service 持有一個 DAO（宣告成介面型別 OrderDao，實作可抽換）

	@Override
	public Long createOrder(String customer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();   // 交易由 service 開

			Order order = new Order();
			order.setCustomer(customer);
			Long id = dao.save(session, order);          // 把 session 傳給 DAO 用

			transaction.commit();
			return id;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();                  // 出錯整批回滾
			}
		}
		return -1L;  // 自己看過：用 -1 當「失敗」回傳值不算漂亮，之後可改成丟例外或回 Optional
	}

	@Override
	public Long createOrderWithItems(String customer, List<OrderItem> items) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Order order = new Order();
			order.setCustomer(customer);

			for (OrderItem item : items) {
				order.addItem(item);   // 用 addItem：順手接好雙向關聯（裡面有 item.setOrder(this)）
			}

			Long id = dao.save(session, order);  // 只存 order，items 因 cascade=ALL 會一起 INSERT

			transaction.commit();
			return id;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return -1L;
	}

	@Override
	public void changeCustomerName(Long orderId, String newName) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			// findById 回 Optional；orElseThrow：有值就拿出來，沒值就丟例外（取代「if==null 才...」的寫法）
			Order order = dao.findById(session, orderId)
					.orElseThrow(() -> new IllegalArgumentException("訂單找不到"));
			order.setCustomer(newName);

			// ===== 自己看過（dirty checking 自動更新，全程沒有 update() 呼叫！）=====
			// 注意：改完 setCustomer 後「沒有」呼叫任何 update。為什麼還會進 DB？
			// → 在交易內被查出來的 entity 是「受管理(managed/persistent)」狀態，
			//   Hibernate 在 commit 前會比對它有沒有被改過(dirty checking)，有變就自動發 UPDATE。
			// 這是 Hibernate 跟你之前 JDBC 最不一樣的地方：在交易內「改物件 = 改資料庫」。
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void addItems(Long orderId, String pname, int price, int qty) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Order order = dao.findById(session, orderId)
					.orElseThrow(() -> new IllegalArgumentException("訂單找不到"));
			order.addItem(new OrderItem(pname, price, qty));  // 加品項；靠 cascade + dirty checking 自動 INSERT 新 item

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void updateItemQty(Long orderId, Long itemId, int newQty) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			// 這裡用 findByIdWithItems（JOIN FETCH 連 items 一起載入），因為下面要在 items 裡找一筆
			Order order = dao.findByIdWithItems(session, orderId)
					.orElseThrow(() -> new IllegalArgumentException("訂單找不到"));

			// 老師同時保留了「傳統 for 迴圈」版本(註解掉)跟下面「Stream」版本，對照學：
//			boolean isFind = false;
//			List<OrderItem> items = order.getItems();
//			for (OrderItem item: items) {
//				if (item.getId().equals(itemId) ) {
//					isFind = true;
//					item.setQty(newQty);
//					break;
//				}
//			}
//			if (!isFind) throw new IllegalArgumentException("品項找不到");

			// ===== 自己看過（Stream API：上面那段 for 迴圈的等價精簡寫法）=====
			// 在 items 裡找出 id == itemId 的那一筆：filter 篩 → findFirst 取第一個 → 沒有就丟例外。
			// （這就是為什麼 OrderItem.id 要改成 Long：.equals() 要對「物件」呼叫）
			OrderItem item = order.getItems().stream()
				.filter(i -> i.getId().equals(itemId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("品項找不到"));
			item.setQty(newQty);  // 改 qty；一樣靠 dirty checking 在 commit 時自動 UPDATE
			/*
			 * Stream 操作分兩類（自己記）：
			 * 期間操作(中間，回傳 Stream，可一直串)：filter / map / sorted / limit
			 * 收尾操作(終端，觸發整串執行、回傳結果)：findFirst / toList / sum / count / forEach
			 */

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void removeItem(Long orderId, Long itemId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Order order = dao.findByIdWithItems(session, orderId)
					.orElseThrow(() -> new IllegalArgumentException("訂單找不到"));

			OrderItem item = order.getItems().stream()
				.filter(i -> i.getId().equals(itemId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("品項找不到"));
			// ===== 自己看過（orphanRemoval 在這裡發威）=====
			// order.removeItem(item) 只是把 item 從 items 清單「踢出去」(變孤兒)，並沒有呼叫任何 delete。
			// 但 Order.items 標了 orphanRemoval = true → commit 時 Hibernate 自動把這筆 item 從 DB DELETE。
			// 對照：cascade 是「刪整張單連帶刪」；這裡單只踢一個 item 也會被刪 —— 這就是 orphanRemoval 的用途。
			order.removeItem(item);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public Order getOrderWithItems(Long orderId) {
		// ===== 自己看過（這個方法「沒有」beginTransaction）=====
		// 為什麼？① 純讀取、不改資料，不需要交易；
		//        ② 用 findByIdWithItems(JOIN FETCH) 在 session 還開著時就把 items 一起載入，
		//           所以 return 後 session 關了，外面拿到的 order 仍可安全 getItems()（不會 LazyInitializationException）。
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return dao.findByIdWithItems(session, orderId)
					.orElseThrow(() -> new IllegalArgumentException("訂單找不到"));
		}
	}

	@Override
	public void deleteOrder(Long orderId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Order order = dao.findByIdWithItems(session, orderId)
					.orElseThrow(() -> new IllegalArgumentException("訂單找不到"));

			dao.delete(session, order);  // 刪整張 order；cascade=ALL → 它的 items 一起被 DELETE

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
