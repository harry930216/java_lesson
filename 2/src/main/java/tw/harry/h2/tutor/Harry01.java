package tw.harry.h2.tutor;

import tw.harry.h2.entity.Customer;

// Harry01：純測 Lombok——建一個 Customer（完全不碰 DB），看 @Data 生出來的 setter/getter/toString 有沒有效
public class Harry01 {

	public static void main(String[] args) {
		System.out.println("OK");

		Customer c1 = new Customer();
		c1.setId("OK2");                 // setId 是 @Data 自動生的（你從沒手寫過這個方法）
		System.out.println(c1.getId());  // getId 同理
		// ===== 自己看過：直接印物件會自動呼叫它的 toString()，也是 @Data 生的 =====
		// 若 Lombok 沒裝好：印出像 tw.harry.h2.entity.Customer@1b6d3586 的預設值
		// 裝好後：印出 Customer(id=OK2, cname=null) —— 用這行驗證 Lombok 到底有沒有生效
		System.out.println(c1);

	}

}
