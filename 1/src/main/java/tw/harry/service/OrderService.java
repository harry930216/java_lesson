package tw.harry.service;

import java.util.List;

import tw.harry.entity.Order;
import tw.harry.entity.OrderItem;

// OrderService：訂單的「服務層(service layer)」介面 —— 新的一層，疊在 DAO 之上。
//
// ===== 自己看過（分層：為什麼 DAO 之上還要 Service）=====
// DAO 只負責「跟 DB 講話」(單純存取，session 由外面給)；
// Service 負責「商業邏輯 + 交易邊界」(開 session/交易、組合多個 DAO 操作、出錯整批 rollback)。
// 這就是為什麼 OrderDao 的方法都收 session 參數 —— session/交易由 Service 開好、傳給 DAO 用。
// 之前 TestDao/MemberDao 把存取和交易混在一起；這裡把責任拆乾淨：Service 管交易、DAO 管存取。
public interface OrderService {
	Long createOrder(String customer);                                  // 建一張空訂單
	Long createOrderWithItems(String customer, List<OrderItem> items);  // 建訂單，同時帶一批品項
	void changeCustomerName(Long orderId, String newName);              // 改客戶名
	void addItems(Long orderId, String pname, int price, int qty);      // 加一個品項到既有訂單
	void updateItemQty(Long orderId, Long itemId, int newQty);          // 改某品項的數量
	void removeItem(Long orderId, Long itemId);                         // 移除某品項
	Order getOrderWithItems(Long orderId);                              // 取訂單（含品項）
	void deleteOrder(Long orderId);                                     // 刪訂單
}
