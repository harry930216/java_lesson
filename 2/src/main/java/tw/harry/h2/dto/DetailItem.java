package tw.harry.h2.dto;

// DetailItem：一筆明細的 DTO (Data Transfer Object) —— 純資料載體。
// 欄位設 public，是為了讓 Jackson 反序列化 (JSON→物件) 時能直接塞進來（沒寫 getter/setter）
public class DetailItem {
	public String pname;
	public Double price;
	public Integer qty;
}
