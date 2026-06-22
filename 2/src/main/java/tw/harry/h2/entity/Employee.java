package tw.harry.h2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Employee：對映 north 的 employees 表
// 這支故意用「拆開的」Lombok 註解，對照 Customer 的 @Data（一次包好）——兩種風格都看過
@Entity
@Table(name = "employees")
@Getter             // 產生所有欄位的 getter
@Setter             // 產生所有欄位的 setter
@NoArgsConstructor  // ===== 自己看過 =====：產生「無參數建構子」。JPA 用反射 (reflection) 建立 entity 時一定要有它，沒有啟動就報錯
@AllArgsConstructor // ===== 自己看過 =====：產生「全欄位建構子」，參數順序 = 下面「欄位宣告順序」(employeeId, lastName, firstName, title)
public class Employee {
	@Id
	@Column(name = "EmployeeID")
	private int employeeId;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "Title")
	private String title;

	// ===== 自己看過（為什麼下面這段被「註解掉」？跟 Harry05 的 multiselect 有關）=====
	// 下面手寫建構子的參數順序是 (employeeId, firstName, lastName, title)。
	// 但 @AllArgsConstructor 產生的是「欄位宣告順序」(employeeId, lastName, firstName, title)——兩者不同！
	// Harry05 的 cq.multiselect(...) 取欄位的順序，必須對得上「實際被呼叫的那個建構子」的參數順序。
	// → 自己對照：Harry05 multiselect 的順序，是配哪一個建構子？所以這支手寫的才被停用、改靠 @AllArgsConstructor。
	// Harry05 的 multiselect 順序
//	public Employee(int employeeId, String firstName, String lastName, String title) {
//		this.employeeId = employeeId;
//		this.lastName = lastName;
//		this.firstName = firstName;
//		this.title = title;
//	}


}
