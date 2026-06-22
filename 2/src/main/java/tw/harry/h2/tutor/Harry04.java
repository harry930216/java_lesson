package tw.harry.h2.tutor;

import java.util.List;

import org.hibernate.Session;

import tw.harry.h2.entity.Employee;
import tw.harry.h2.utils.HibernateUtil;

// Harry04：跟 Harry03 同一張表、同一招原生 SQL，但結果「不對映 entity」，改用 Object[] 接原始欄位
public class Harry04 {
	public static void main(String[] args) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			String sql = """
				SELECT EmployeeID, FirstName, LastName, Title
				FROM employees
				ORDER BY Title ASC
				""";
			// ===== 自己看過：Object[].class → 每列回傳一個 Object 陣列，e[0]=EmployeeID、e[1]=FirstName... 用索引讀 =====
			// 對照 Harry03 的 Employee.class：一個是「塞進物件」（用 getter 讀，有型別）、一個是「原始欄位陣列」（用索引讀，沒型別）
			List<Object[]> employees =
				session.createNativeQuery(sql, Object[].class).getResultList();
			System.out.println(employees.size());
			for (Object[] e : employees) {
				System.out.printf("%d:%s:%s:%s\n",
						e[0],
						e[1],
						e[2],
						e[3]);
			}


		}
	}
}
