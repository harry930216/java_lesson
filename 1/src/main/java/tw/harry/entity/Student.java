package tw.harry.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sid;
	private String sname;

	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Student() {};
	public Student(String sname) { this.sname = sname;};

	//------------------------- 多對多關聯：Student 這一端（owner）-------------------------

	// ===== 自己看過（多對多的 owner 端 + 第三張表）=====
	// Student 是「擁有方 owner」：由它用 @JoinTable 定義「中間表(join table)」。
	// 多對多跟一對多最大不同：一對多的外鍵塞在「多」端的表就好；多對多兩邊都「多」，外鍵塞不進任一張表，
	//   所以一定要「第三張表」sc 來存配對，裡面兩個外鍵：
	//     @JoinTable(name = "sc")                 → 中間表叫 sc
	//     joinColumns = @JoinColumn("sid")        → 指向「自己這端」Student 的外鍵欄位 sid
	//     inverseJoinColumns = @JoinColumn("cid") → 指向「對方」Course 的外鍵欄位 cid
	//   → sc 表每一列 (sid, cid) = 一筆選課紀錄。
	// cascade = ALL、fetch = EAGER：存 student 連動 courses、載入 student 順便載它的 courses。
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "sc",
			joinColumns = {@JoinColumn(name = "sid")},
			inverseJoinColumns = {@JoinColumn(name = "cid")}
				)
	private Set<Course> courses = new HashSet<>();

	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	// ===== 自己看過（雙向一致 + Set.add 的回傳值）=====
	// addCourse：把課加進自己的 courses，同時把自己加進那門課的 students（雙向兩邊都接，跟 addItem 同概念）。
	// if (courses.add(course)) ... → Set.add() 成功加入(原本沒有)才回 true；已存在就回 false、不重複維護對方。
	public void addCourse(Course course) {
		if (courses.add(course)) {
			course.getStudents().add(this);
		}
	}
}
