package tw.harry.tutor;

import java.util.List;

import tw.harry.entity.Order;
import tw.harry.entity.OrderItem;
import tw.harry.service.OrderService;
import tw.harry.service.OrderServiceImp;

// Harry13：測 OrderServiceImp.test1 —— 撈訂單並印出每個品項 pname。
// 自己看過：下面註解掉的是「另一種寫法」(在 main 自己撈 order.getItems() 印)，
//   List/Order/OrderItem 那幾個 import 是給它用的；現用版把邏輯收進 service.test1 裡，呼叫端乾淨。
public class Harry13 {

	public static void main(String[] args) {
		OrderServiceImp service = new OrderServiceImp();

//		Order order = service.getOrderById(1L);
//		List<OrderItem> items = order.getItems();
//		for (OrderItem item : items) {
//			System.out.println(item.getPname());
//		}
		service.test1(1L);
	}

}
