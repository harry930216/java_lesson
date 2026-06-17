package tw.harry.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;        // 自己看過：Member 裡沒有任何 @Column，這個 import 其實沒用到（Eclipse 會顯示灰色未使用警告），可刪
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;          // 自己看過：Member 沒寫 @Table，這個 import 也沒用到，可刪

@Entity  // 宣告這是 JPA entity（會對映到資料表的物件）
public class Member {
	@Id  // 主鍵 (primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 主鍵生成策略：IDENTITY = 交給 DB auto_increment 自動遞增
	private long id;

	private String account;  // 帳號（沒寫 @Column，欄位名預設就跟變數名一樣 → account）

	private String passwd;   // 密碼

	private byte[] icon;     // 大頭貼：用 byte[] 存「二進位圖檔」，對映到 DB 會是 BLOB（Binary Large OBject 二進位大物件）

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	//------------------------------ 一對一關聯：Member 這一邊 ------------------------------

	// ===== 自己看過 =====
	// 今天的主角：@OneToOne（一對一）雙向關聯。一個 Member 對應一個 MemberInfo。
	// Member 是「被參考方 / 非擁有方 (inverse side，沒有外鍵的一方)」：
	//   mappedBy = "member" 的意思 →「外鍵不在我(member 表)這邊。關聯由 MemberInfo 類別裡那個叫
	//                                  member 的欄位去管(@JoinColumn 在它身上)。」
	//   規則：mappedBy 永遠寫在「沒有外鍵」的那一方；有 @JoinColumn 的那方才是「擁有方 owner」。
	// cascade = CascadeType.ALL：對 Member 做的動作(persist/remove/merge…)會「連動」套到它的 memberinfo。
	//   → 等一下只要存一個 member，它身上掛的 memberinfo 會被一起存進去，不必再單獨存 MemberInfo。
	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	private MemberInfo memberinfo;

	public MemberInfo getMemberinfo() {
		return memberinfo;
	}

	public void setMemberinfo(MemberInfo memberinfo) {
		this.memberinfo = memberinfo;
		// ===== 自己看過 =====
		// 這一行 memberinfo.setMember(this) 為什麼非寫不可？
		//   雙向關聯有「兩個指標」：member.memberinfo（這邊）和 memberinfo.member（另一邊），
		//   兩邊都要指對才算一致。若只設這邊(this.memberinfo = memberinfo)，
		//   另一邊 memberinfo.member 會是 null。而「外鍵實際在 MemberInfo 那張表」，
		//   存進去外鍵就會是 null → 關聯接不上。所以這裡順手把回頭指標接好。
		//   這是雙向關聯最常見的坑，務必能用嘴講出來「為什麼要多接這一行」。
		memberinfo.setMember(this);
	}
}
