package tw.harry.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;
	private String cname;

	public Course() {}
	public Course(String cname) {
		this.cname = cname;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}

	//----------------------- 多對多關聯：Course 這一端 -----------------------

	// ===== 自己看過（多對多的 inverse 端）=====
	// Course 是「非擁有方 inverse」：mappedBy = "courses" → 關聯由 Student 類別裡那個 courses 欄位(@JoinTable)管。
	//   跟你看過的 Member/Order 的 mappedBy 同概念：有 @JoinTable/@JoinColumn 的那邊才是 owner。
	// 為什麼用 Set 不用 List？多對多用 Set 較自然：同一個學生不該重複選同一門課，Set 自動去重。
	// fetch = EAGER：載入 course 時順便把「選了這門課的學生」一起載入。
	@ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
	private Set<Student> students = new HashSet<>();

	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
}
