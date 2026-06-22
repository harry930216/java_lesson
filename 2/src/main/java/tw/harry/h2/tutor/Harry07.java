package tw.harry.h2.tutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import tools.jackson.databind.ObjectMapper;   // 自己看過：Jackson 3.x 套件名改成 tools.jackson（舊版是 com.fasterxml.jackson）
import tw.harry.h2.dto.DetailItem;
import tw.harry.h2.dto.OrderItem;
import tw.harry.h2.utils.HibernateUtil;

// Harry07：HQL 投影查詢（只挑欄位、跨 JOIN）+ Jackson 把結果轉 JSON、再轉回物件
public class Harry07 {
	// ===== 自己看過：HQL 的 JOIN 用「物件導覽」(o.orderDetails、d.product)，不寫 ON 條件 =====
	// SELECT 直接挑欄位 → 回傳 Object[]（不是整個 entity），這叫「投影 (projection)」
	private static final String hql = """
			SELECT
				o.customer.cname,
				o.employee.lastName,
				o.orderDate,
				p.productName,
				d.unitPrice,
				d.quantity
			FROM Order o
			JOIN o.orderDetails d
			JOIN d.product p
			WHERE o.orderId = :orderId
			""";
	public static void main(String[] args) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			List<Object[]> result =
				session.createQuery(hql, Object[].class)
					.setParameter("orderId", 10249)   // 查 Northwind 第 10249 號訂單
					.getResultList();
			//---------------------------------
			for (Object[] row : result) {
				System.out.println(row[0]);
				System.out.println(row[1]);
				System.out.println(row[2]);
			}
			//----------------------
			System.out.println("----------JSON(1)----------");
			// ===== 自己看過：ObjectMapper = Jackson 的主角，物件 ↔ JSON 互轉 =====
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(result);   // List<Object[]> 直接轉 JSON（純陣列，不好讀）
			System.out.println(json);
			System.out.println("----------JSON(2)----------");
			String json2 = toObjectJson(result);   // 手動整理成「有名字的」結構再轉 JSON

			System.out.println("----------Object----------");
			toOrderItem(json2);   // 反方向：JSON 字串 → Java 物件

		}
	}

	// 把 Object[] 結果手動組成 Map 結構（customer/employee/date + details 清單），再轉 JSON
	private static String toObjectJson(List<Object[]> result) {
		Map<String,Object> output = new HashMap<>();
		output.put("customer", result.get(0)[0]);
		output.put("employee", result.get(0)[1]);
		output.put("date", result.get(0)[2]);

		List<Map<String,Object>> details = new ArrayList<>();
		for (Object[] row : result) {
			Map<String,Object> detail = new HashMap<>();
			detail.put("pname", row[3]);
			detail.put("price", row[4]);
			detail.put("qty", row[5]);
			details.add(detail);
		}

		output.put("details", details);
		//-------------------------------------

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(output);
		return json;
	}

	// ===== 自己看過：JSON → 物件。readValue(json, OrderItem.class) 把 JSON 欄位塞進 OrderItem 的 public 欄位 =====
	private static void toOrderItem(String json) {
		ObjectMapper mapper = new ObjectMapper();
		OrderItem order = mapper.readValue(json, OrderItem.class);
		System.out.println(order.customer);
		System.out.println(order.employee);
		System.out.println(order.date);

		for (DetailItem detail : order.details) {
			System.out.println(detail.pname);
			System.out.println(detail.price);
			System.out.println(detail.qty);
			System.out.println("----");
		}


	}

}
