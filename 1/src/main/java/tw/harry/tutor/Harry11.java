package tw.harry.tutor;

import tw.harry.service.OrderService;
import tw.harry.service.OrderServiceImp;

// Harry11｜第3棒：對「已經存在的訂單」補品項
// 鏡像老師 Brad11。跟 Harry10 用的是同一個 addItems，差別：這裡訂單 id 直接寫死 1L，
// 代表「order 1 是之前(別次執行)就建好、躺在 DB 裡的」，這支只負責往它身上加東西。
public class Harry11 {

	public static void main(String[] args) {
		OrderService service = new OrderServiceImp();

		service.addItems(1L, "item1", 100, 2);
		service.addItems(1L, "item2", 120, 3);
		service.addItems(1L, "item3", 105, 5);

		// ===== 自己看過（先確認資料，再預測例外路徑）=====
		// (1) 你 DB 裡真的有 id=1 的訂單嗎？沒有的話這支跑起來會怎樣？
		//     跟著 OrderServiceImp.addItems 的 findById(session, 1L).orElseThrow(...) 走一遍，預測結果再跑跑看。
		// (2) 想想 cascade / orphanRemoval 在這支「有沒有」作用 —— 這支只「加」，沒有把任何 item 踢出清單。
	}

}
