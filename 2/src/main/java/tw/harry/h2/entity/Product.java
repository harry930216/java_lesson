package tw.harry.h2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Product：對映 north 的 products 表（簡單單表，給 OrderDetail 的 @ManyToOne 接過來）
@Entity
@Table(name ="products")
public class Product {

	@Id
	@Column(name = "ProductID")
	private int productId;

	@Column(name = "ProductName")
	private String productName;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
