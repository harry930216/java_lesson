package tw.harry.h2.tutor;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import tw.harry.h2.entity.Employee;
import tw.harry.h2.utils.HibernateUtil;

/*
 * Criteria:  標準查詢
 * 		=> CriteriaBuilder: Session 取得 / EntityManager
 *  CriteriaQuery<Employee>:  查詢物件<Entity>
 *  Root<Employee>: 查詢實體
 */
// 補充：Criteria API = 用「Java 程式碼」組查詢，不寫字串 SQL/HQL。
// 好處：欄位名打錯，編譯期就抓得到（型別安全 type-safe）；壞處：簡單查詢比寫 HQL 囉嗦。
public class Harry05 {
	public static void main(String[] args) {
		System.out.println("Harry05");
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder cb = session.getCriteriaBuilder();           // 1. 拿「查詢建造器」
			CriteriaQuery<Employee> cq = cb.createQuery(Employee.class); // 2. 建一個回傳 Employee 的查詢物件
			Root<Employee> root = cq.from(Employee.class);               // 3. FROM Employee（root 代表那張表，之後靠它取欄位）
			//----------------------------
			// ===== 自己看過：multiselect 的「順序」要對應 Employee 實際被呼叫的建構子參數順序 =====
			// 這裡的順序是 employeeId, lastName, firstName, title —— 回頭對照 Employee 的 @AllArgsConstructor（欄位順序）
			cq.multiselect(root.get("employeeId"),  // 注意：這裡用的是「entity 屬性名」employeeId，不是 DB 欄位名 EmployeeID
					root.get("lastName"),
					root.get("firstName"),
					root.get("title"));
			cq.orderBy(cb.asc(root.get("title")), cb.desc(root.get("lastName")));  // ORDER BY title ASC, lastName DESC
			//----------------------------
			List<Employee> employees = session.createQuery(cq).getResultList();
			System.out.println(employees.size());
			for (Employee e : employees) {
				System.out.printf("%d:%s:%s:%s\n",
						e.getEmployeeId(),
						e.getFirstName(),
						e.getLastName(),
						e.getTitle());
			}




		}
	}
}
