package tw.harry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity  // 宣告這是 JPA entity（會對映到資料表的物件）
@Table(name = "test")  // 對映到資料庫名為 test 的表
public class Test {
	
	// 遞增可以分程式跟資料庫
	// hibernate也可以做 但遞增方法怪
	
	@Id  // 主鍵 (primary key)
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 主鍵生成策略：IDENTITY = 交給 DB auto_increment 自動遞增
	private long id;
	
	@Column(name = "cname")  // @Column：把這個欄位對映到資料表的 cname 欄
	private String cname;     // 欄位全設 private（封裝 encapsulation），靠下面 getter/setter 存取
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "birthday")
	private String birthday;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
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
	
	
}
