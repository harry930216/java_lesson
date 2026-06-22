package tw.harry.h2.tutor;

import java.util.List;

import org.hibernate.Session;

import tw.harry.h2.entity.Employee;
import tw.harry.h2.utils.HibernateUtil;

// Harry03：原生 SQL 查詢 (native query)，且把結果「對映成 Employee entity」
public class Harry03 {
	public static void main(String[] args) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			// ===== 自己看過：這是「原生 SQL」不是 HQL =====
			// 直接寫資料庫真正的 SQL：表名 employees、欄位名 EmployeeID 都是「DB 的真名」，不是 entity 的屬性名
			String sql = """
				SELECT EmployeeID, FirstName, LastName, Title
				FROM employees
				ORDER BY Title ASC, LastName DESC
				""";
			// createNativeQuery(sql, Employee.class)：把每一列結果「塞進」一個 Employee 物件（欄位名要對得上 @Column）
			List<Employee> employees =
				session.createNativeQuery(sql, Employee.class).getResultList();
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
