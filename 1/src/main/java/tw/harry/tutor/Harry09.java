package tw.harry.tutor;

import java.util.List;

import tw.harry.entity.OrderItem;
import tw.harry.service.OrderService;
import tw.harry.service.OrderServiceImp;

// Harry09｜把 OrderService 真的跑起來 第1棒：一次交易「建單 + 帶一批品項」
// 鏡像老師 Brad09。前面 Harry01-08 都在玩 Test/Member，這四支(09-12)開始實跑你寫的訂單 service。
//
// 這支示範：createOrderWithItems(客戶, 一批品項) —— 新訂單 + 多筆品項，整批在「同一個交易」內進 DB。
//   背後靠 Order 的 @OneToMany(cascade = ALL)：service 只 save(order)，底下 items 會被「連動 INSERT」，
//   不需要、也沒有對每個 item 各存一次。(機制細節在 OrderServiceImp.createOrderWithItems 的註解)
public class Harry09 {
	public static void main(String[] args) {
		OrderService service = new OrderServiceImp();

		// 對照組(先關起來)：createOrder 只建一張「空訂單」、沒帶任何品項。想單獨試就打開這兩行。
		//Long id = service.createOrder("harry");
		//System.out.println(id);

		// 一次準備 3 筆品項。OrderItem(品名 pname, 單價 price, 數量 qty)
		List<OrderItem> items = List.of(new OrderItem("BTC", 100, 2),
				new OrderItem("Mouse", 200, 3),
				new OrderItem("NB", 1000, 1));
		Long id2 = service.createOrderWithItems("andy", items);

		// ===== 自己看過（跑之前先預測，再對 console 的 SQL 數量）=====
		// 把 hibernate 的 show_sql 打開跑這支：Hibernate 一共發了幾筆 INSERT？分別寫進哪兩張表(orders / oitem)？
		// 想清楚：那 3 筆 item 是「我各自存的」，還是「存 order 那一下被 cascade 連帶帶進去的」？
		// (id2 老師原碼沒印出來；想看訂單編號就自己補一行 System.out.println(id2);)
	}
}
