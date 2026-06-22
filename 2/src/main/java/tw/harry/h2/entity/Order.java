package tw.harry.h2.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Order：對映 north 的 orders 表。這支開始玩「實體之間的關聯」（不再是單表查詢）
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "OrderID")
	private int orderId;

	@Column(name = "OrderDate")
	private String orderDate;   // 自己看過：日期先用 String 接（簡化），正式可換 LocalDate/Date

	// ===== 自己看過（多對一 @ManyToOne）=====
	// 「多筆訂單」對到「一個客戶」：外鍵 (FK) 欄位 CustomerID 放在 orders 表（多的這一邊）
	@ManyToOne
	@JoinColumn(name = "CustomerID")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "EmployeeID")
	private Employee employee;   // 同理：多筆訂單對到一個負責員工

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	//--------------------
	// ===== 自己看過（一對多 @OneToMany，mappedBy）=====
	// 「一筆訂單」有「多筆明細」。mappedBy = "order" → 這段關聯由 OrderDetail.order 那邊維護（owner 在「多」的那邊）
	// 對照 hi1 的 Order↔OrderItem，是同一個觀念
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails = new ArrayList<>();

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
