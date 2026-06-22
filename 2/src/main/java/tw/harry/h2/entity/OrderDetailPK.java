package tw.harry.h2.entity;

import java.io.Serializable;
import java.util.Objects;

// OrderDetailPK：OrderDetail 的「複合主鍵類別」。OrderDetail 上的 @IdClass 指的就是它。
// ===== 自己看過（複合主鍵類別的 3 個硬規定，少一個都會出事）=====
// ① 要 implements Serializable（JPA 規定，主鍵要可序列化）
// ② 欄位「名稱 + 型別」要跟 OrderDetail 裡那兩個 @Id 欄位完全對得上（orderId:int、productId:int）
// ③ 一定要自己覆寫 equals() 與 hashCode()（JPA 靠它判斷「兩筆是不是同一個主鍵」）
public class OrderDetailPK implements Serializable {
	private int orderId;
	private int productId;

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof OrderDetailPK)) return false;
		OrderDetailPK that = (OrderDetailPK)obj;
		return this.orderId == that.orderId && this.productId == that.productId;
	}

}
