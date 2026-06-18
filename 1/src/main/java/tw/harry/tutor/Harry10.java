package tw.harry.tutor;

import tw.harry.service.OrderService;
import tw.harry.service.OrderServiceImp;

// Harry10｜第2棒：先建「空訂單」，事後再分次補品項
// 鏡像老師 Brad10。跟 Harry09「一次到位」相反：這支拆成先 createOrder、再兩次 addItems。
//
// 重點觀念 —— 交易邊界(transaction boundary)：每呼叫一次 service 方法，就「各自開一個 session + 交易、
//   做完就 commit」。所以這支總共開了 3 個交易(1 次 createOrder + 2 次 addItems)，彼此獨立。
public class Harry10 {

	public static void main(String[] args) {
		OrderService service = new OrderServiceImp();

		Long id = service.createOrder("mary");   // 第1個交易：建一張空訂單，拿回自動產生的主鍵 id
		System.out.println(id);

		service.addItems(id, "ETH", 20, 7);      // 第2個交易：把品項加進「剛剛那張」訂單
		service.addItems(id, "Ball", 40, 5);     // 第3個交易：再加一筆

		// ===== 自己看過（addItems 內部沒呼叫 update()，為什麼新品項會進 DB？）=====
		// 翻 OrderServiceImp.addItems：它只做 findById 把 order 撈出來、order.addItem(new OrderItem(...))，
		// 然後直接 commit，全程沒對那個「新 item」呼叫任何 save/update。它是怎麼被 INSERT 的？
		// 提示：跟 changeCustomerName「改名字不用 update 也會進 DB」同一個機制 + Order.items 的 cascade
		//       —— 自己把這條線(persistent 狀態 → dirty checking → cascade)講出來。
	}

}
