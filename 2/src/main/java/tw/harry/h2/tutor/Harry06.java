package tw.harry.h2.tutor;

import java.util.List;

import org.hibernate.Session;

import tw.harry.h2.entity.Employee;
import tw.harry.h2.utils.HibernateUtil;

// Harry06：第 4 種查法 —— HQL (Hibernate Query Language)。對照 Harry03(native SQL) / Harry05(Criteria)
public class Harry06 {
	public static void main(String[] args) {
		System.out.println("Harry06");
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			// ===== 自己看過：HQL 用的是「entity 名 / 屬性名」(Employee、e.title、e.lastName)，不是 DB 表名/欄位名 =====
			// 對照 Harry03 的原生 SQL：那邊寫 employees / Title / LastName（DB 真名）；這邊寫 Employee / title / lastName（Java 名）
			// 所以 HQL 跨資料庫、且欄位打錯比較好抓；native SQL 則綁定特定 DB 語法
			String hql = """
					FROM Employee e
					ORDER BY e.title ASC, e.lastName DESC
				""";
			List<Employee> employees =
				session.createQuery(hql, Employee.class).getResultList();
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
