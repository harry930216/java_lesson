package tw.harry.entity;

import java.util.ArrayList;  // 自己看過：OrderItem 沒有集合欄位，這兩個 import 沒用到（從 Order 複製來的），可刪
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// OrderItem：訂單品項（一對多的「多」端，多個品項屬於同一張 Order）
@Entity
@Table(name = "oitem")  // 對映表名 oitem（自己指定一個乾淨的短名）
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// ===== 自己看過（id 從 long 改成 Long）=====
	// 原因：① service 裡要用 item.getId().equals(itemId) 比對，.equals() 只能對「物件」呼叫，primitive long 不行；
	//       ② Long 可為 null，還沒存進 DB 的新 item，id 是 null 比較合理（primitive long 只能是 0）。
	private Long id;

	@Column(nullable = false)  // 沒給 name，欄位名預設就用變數名 pname
	private String pname;      // product name 品名

	@Column(nullable = false)
	private int price;         // 單價

	@Column(nullable = false)
	private int qty;           // 數量 quantity

	// ===== 自己看過（兩個建構子，缺一不可）=====
	// 一旦你自己定義了「有參數的建構子」，Java 就不再自動送你「無參數建構子」。
	// 但 JPA/Hibernate 是用反射(reflection)先 new 出空物件再塞值，一定要有「無參建構子」，
	// 所以這裡手動補回 OrderItem() {}。
	// 對照 Member/MemberInfo/Test 都沒寫任何建構子 → 自動有預設無參建構子，就不必補。
	public OrderItem() {}
	public OrderItem(String pname, int price, int qty) {
		this.pname = pname; this.price = price; this.qty = qty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	//------------------------ 一對多關聯：OrderItem 這一端（多個品項指向一張訂單）------------------------

	// ===== 自己看過（owner 端；fetch 老師這版改過了）=====
	// OrderItem 是一對多的「多」端，也是「擁有方 owner」：外鍵就在這張 oitem 表，所以這邊出現 @JoinColumn。
	//   @JoinColumn(name = "oid", nullable = false) → 外鍵欄位叫 oid、不可為 null（每個品項一定要屬於某張單）。
	// fetch：老師「改過一次」—— 原本 LAZY，這版改回 EAGER（把 LAZY 那行註解留著對照）。
	//   LAZY＝只有呼叫 getOrder() 才查 order；EAGER＝載入 item 時就連 order 一起查。@ManyToOne 預設本來就是 EAGER。
	//   自己看過：想一下為什麼改回 EAGER —— 跟「item 撈出來後 session 若已關，還能不能 getOrder()」有關：
	//   EAGER 在 session 內就載好、關了也能用；LAZY 關掉後再 getOrder() 會丟 LazyInitializationException。
	@ManyToOne(fetch = FetchType.EAGER)
	//@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oid", nullable = false)
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
