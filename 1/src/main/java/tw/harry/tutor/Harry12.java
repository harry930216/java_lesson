package tw.harry.tutor;

import java.util.List;

import tw.harry.entity.Order;
import tw.harry.entity.OrderItem;
import tw.harry.service.OrderService;
import tw.harry.service.OrderServiceImp;

// Harry12｜第4棒：讀一張訂單，連它底下的品項一起拿出來印
// 鏡像老師 Brad12。這支是前面 N+1 / 載入時機 那條線的「收尾驗收」，最值得停下來想。
public class Harry12 {

	public static void main(String[] args) {
		OrderService service = new OrderServiceImp();

		Order order = service.getOrderWithItems(1L);   // 讀 id=1 的訂單(內部用 JOIN FETCH 連 items 一起載)
		List<OrderItem> items = order.getItems();
		for (OrderItem item : items) {
			System.out.println(item.getPname());        // 逐筆印出品名
		}

		// ===== 自己看過（這支的「梗」：session 都關了，為什麼還能 getItems() 不爆掉？）=====
		// 注意這個 for 迴圈跑在 service.getOrderWithItems(...) 回傳「之後」—— 那時 session 早關了(try-with-resources)。
		// 一般情況下，session 關了再去碰 LAZY 的關聯，會丟 LazyInitializationException。這裡為什麼沒事？
		//   → 對照 OrderServiceImp.getOrderWithItems 的註解，用「JOIN FETCH」「載入時機」這兩個詞自己講一遍。
		// 反向再想(這題才是重點)：若改成呼叫「沒有 JOIN FETCH 的 findById」，這個迴圈會發生什麼？
		//   這正是 Harry08 在追的 N+1 / 載入時機的另一面 —— 把 09→12 連起來看就完整了。
	}

}
