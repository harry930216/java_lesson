package tw.harry.h2.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// OrderDetail：對映 north 的 orderdetails 表（訂單明細）。重點 = 複合主鍵
@Entity
@Table(name = "orderdetails")
// ===== 自己看過（複合主鍵 @IdClass）=====
// 這張表的主鍵不是單一欄，而是 (OrderID, ProductID) 兩欄「合起來」當主鍵。
// @IdClass(OrderDetailPK.class) → 指定一個「主鍵類別」來代表這組複合鍵（去看 OrderDetailPK 的 3 個規定）
@IdClass(OrderDetailPK.class)
public class OrderDetail {
	@Id
	@Column(name = "OrderID")
	private int orderId;
	@Id                          // 兩個欄位都標 @Id → 複合主鍵
	@Column(name = "ProductID")
	private int productId;

	@Column(name = "UnitPrice")
	private BigDecimal unitPrice;   // 自己看過：金額用 BigDecimal、不用 double（避免浮點誤差）

	@Column(name = "Quantity")
	private int quantity;

	// ===== 自己看過（OrderID / ProductID 既是「主鍵」也是「外鍵」）=====
	// 上面 orderId/productId 是 @Id 主鍵欄；下面 product/order 又用 @JoinColumn 把同名欄位當 FK 接關聯。
	// 明細表的主鍵剛好就是「兩個外鍵的組合」(PK = FK1 + FK2)，這是「連接表帶複合主鍵」的典型長相。
	// （老師實際有跑成功 → 有 order.json 輸出，所以 Hibernate 6 接受這種同欄位兼主鍵與外鍵的寫法）
	@ManyToOne
	@JoinColumn(name = "ProductID")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "OrderID")
	private Order order;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
