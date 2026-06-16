package tw.harry.tutor;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import tw.harry.entity.Test;
import tw.harry.utils.HibernateUtil;

// 拿泛型來用

public class Harry03 {
	
	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
		 Transaction transaction = session.beginTransaction();
		 
		 String sql = """
		 			SELECT * FROM test
		 			""";
		 NativeQuery<Test> query = session.createNativeQuery(sql, Test.class);
		 List<Test> list = query.getResultList();
		 System.out.println(list.size());
		 for (Test test : list) {
			 System.out.printf("%s:%s:%s:%s\n", test.getId(), test.getCname(), test.getTel(), test.getBirthday());
		 }
		 
		 transaction.commit();
		}
	}

}
