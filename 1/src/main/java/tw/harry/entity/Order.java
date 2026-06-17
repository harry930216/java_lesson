package tw.harry.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Order：訂單（一對多的「一」端，一張訂單對多個 OrderItem）
@Entity
@Table(name = "orders")  // 自己看過：為什麼不叫 "order"？ORDER 是 SQL 保留字(ORDER BY)，拿來當表名會出問題，所以用 orders
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// 第一次看到的 @Column 屬性：nullable=false → 欄位 NOT NULL；length=100 → VARCHAR(100)
	@Column(name = "customer", nullable = false, length = 100)
	private String customer;

	// LocalDateTime：Java 8 的日期時間型別（比 String/舊 Date 好用）。預設值在 Java 端就給 now()
	// 欄位名用 odate 不用 date（date 也偏保留字，且語意清楚 = order date 下單時間）
	@Column(name = "odate", nullable = false)
	private LocalDateTime odate = LocalDateTime.now();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public LocalDateTime getOdate() {
		return odate;
	}

	public void setOdate(LocalDateTime odate) {
		this.odate = odate;
	}

	//-------------- 一對多關聯：Order 這一端（一張訂單有多個品項）--------------

	// ===== 自己看過（@OneToMany 三個設定）=====
	// Order 是一對多的「一」端，也是「非擁有方 inverse」（外鍵不在 orders 表、在 oitem 表）。
	//   mappedBy = "order"   → 外鍵由對方 OrderItem 的「order」欄位管（跟 Member 的 mappedBy 同概念）。
	//   cascade = ALL        → 存/刪 order 連動到 items（存訂單時，底下品項一起 INSERT）。
	//   orphanRemoval = true → 新概念！把某個 item 從 items 清單「移除」(變孤兒)時，那筆 item 會直接被 DELETE。
	// 自己看過：cascade 的 REMOVE 是「刪掉整張 order 時連帶刪 items」；orphanRemoval 是「order 還在，
	//   只是 item 被踢出清單也照樣刪」。兩者不同 —— 自己想 removeItem(x) 之後 x 在 DB 會怎樣。
	// 註：現在註解齊了，這兩個 entity 已「可以」登記進 cfg.xml；但老師目前還沒登記、也還沒有測試用的 tutor，
	//     所以我先不動 cfg、不建表（跟老師同步）。你想先實測 OrderDao 的話跟我說，我馬上補 cfg + 建表 SQL。
	@OneToMany(mappedBy = "order",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();  // 一開始就 new 好空清單，避免之後 add 時是 null

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	// ===== 自己看過 =====
	// addItem/removeItem 是「方便方法」：加品項時順手 item.setOrder(this)，把雙向關聯兩邊接好。
	// 跟你之前 Member.setMemberinfo 裡多接 setMember(this) 同一個道理 —— 雙向一致，少接一邊外鍵會錯。
	public void addItem(OrderItem item) {
		items.add(item);
		item.setOrder(this);
	}

	public void removeItem(OrderItem item) {
		items.remove(item);
		item.setOrder(null);  // 移除時把對方的 order 指標也清掉
	}
}
