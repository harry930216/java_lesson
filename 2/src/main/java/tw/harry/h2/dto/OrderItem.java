package tw.harry.h2.dto;

import java.util.List;

// OrderItem：一張訂單的 DTO，內含多筆 DetailItem。Harry07 把 JSON 反序列化成這個型別
public class OrderItem {
	public String date;
	public String employee;
	public String customer;
	public List<DetailItem> details;
}
