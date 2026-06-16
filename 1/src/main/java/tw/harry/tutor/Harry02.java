package tw.harry.tutor;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import tw.harry.utils.HibernateUtil;


public class Harry02 {

	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
		 Transaction transaction = session.beginTransaction();
		 
		 String sql = """
		 			SELECT * FROM test
		 			""";
		 NativeQuery query = session.createNativeQuery(sql);
		 List list = query.getResultList();
		 System.out.println(list.size());
		 for (Object obj : list) {
			 Object[] data = (Object[])obj;
			 System.out.printf("%s:%s:%s:%s\n", data[0], data[1], data[2], data[3]);
		 }
		 
		 transaction.commit();
		}
	}

}
