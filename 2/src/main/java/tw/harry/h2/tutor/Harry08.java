package tw.harry.h2.tutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/*
 * ObjectMapper
 * 	Object -> JSON: Map, List
 * 	JSON -> Object
 * 	write / read
 * 	writeValueAsString
 * 	writeValue -> JSON File
 *
 *  readTree
 *
 */
// Harry08：純練 Jackson（不碰 DB，可直接跑）。Map→JSON、JSON→Map 兩個方向
public class Harry08 {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Harry");
		map.put("age", 18);

		String json = mapper.writeValueAsString(map);   // Map → JSON 字串
		System.out.println(json);

		System.out.println("-----");
		// ===== 自己看過：手寫一段 JSON 字串，等下把它讀回成 Java 的 Map =====
		String json2 = """
				{
					"date": "1996-07-04 00:00:00",
					"customer": "Vins et alcools Chevalier",
					"employee": "Buchanan",
					"details": [
						{
							"pname": "Queso Cabrales1",
							"price": 14.0000,
							"qty": 12
						},
						{
							"pname": "Queso Cabrales2",
							"price": 14.0000,
							"qty": 12
						},
						{
							"pname": "Queso Cabrales3",
							"price": 14.0000,
							"qty": 12
						}
					]
				}
				""";

		// ===== 自己看過：TypeReference 用來「保住泛型型別」（Java 會型別抹除，Map<String,Object>.class 寫不出來）=====
		Map<String,Object> result =
				mapper.readValue(json2, new TypeReference<Map<String,Object>>(){});
		System.out.println(result.get("date"));
		System.out.println(result.get("customer"));
		System.out.println(result.get("employee"));

		List<Map<String,Object>> details =  (List<Map<String,Object>>)(result.get("details"));
		System.out.println(details.size());
		for (Map<String,Object> detail : details) {
			System.out.println(detail.get("pname"));
		}







	}

}
