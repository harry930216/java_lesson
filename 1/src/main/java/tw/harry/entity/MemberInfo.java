package tw.harry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "memberinfo")  // 明確指定對映的表名為 memberinfo（不指定的話，預設會用類別名 MemberInfo 當表名）
public class MemberInfo {
	@Id  // 主鍵
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// ===== 自己看過（今天最重要、最容易誤會的一點）=====
	// 這裡看起來矛盾：上面說「@GeneratedValue(IDENTITY) → id 交給 DB 自己 auto_increment」，
	// 但下面 member 欄位又標了 @MapsId「我的 id 直接抄 Member 的 id」。兩個規則衝突時 → @MapsId 贏。
	//   結果：MemberInfo 的 id 不會自己遞增，而是「等於它對應的那個 Member 的 id」。
	//   永遠成立：memberInfo.getId() == member.getId()。這就是「共用主鍵 (shared primary key)」。
	// 自己想清楚一題：那這行 @GeneratedValue 在這裡其實有沒有作用？能不能拿掉？(先想，再驗證)
	private long id;

	private String tel;

	private String birthday;  // 自己看過：生日存成 String（不是 LocalDate/Date），老師先用字串簡化，之後可換型別

	@Column(name = "gender")  // Java 變數叫 isMale，但對映到 DB 的欄位名是 gender（變數名與欄位名可以不同）
	private boolean isMale;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	// ===== 自己看過 =====
	// boolean 的 getter/setter 命名跟一般型別不一樣：
	//   getter 是 isMale()（不是 getIsMale()），setter 是 setMale()（把開頭的 is 去掉）。
	//   這是 JavaBeans 對 boolean 的命名慣例，IDE 自動產生時就長這樣，不是打錯。
	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	//------------------------ 一對一關聯：MemberInfo 這一邊 ------------------------

	// ===== 自己看過 =====
	// MemberInfo 是「擁有方 (owner side，外鍵在它這張表)」，所以三個註解都長在這邊：
	//   @OneToOne                → 一對一。
	//   @MapsId                  → 把「指向 Member 的外鍵」同時拿來「當自己的主鍵」用 = 共用主鍵，省一個欄位。
	//   @JoinColumn(name = "id") → 這個外鍵(同時也是主鍵)的欄位名叫 id。
	// 對照 Member 那邊寫的是 mappedBy（沒有外鍵）→ 一個關聯只有「一方」擁有外鍵，記住誰是 owner。
	// 結果：memberinfo 表只會有一個 id 欄位，它「既是主鍵、又是指向 member.id 的外鍵」，一欄兩用。
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
