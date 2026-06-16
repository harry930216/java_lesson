package tw.harry.tutor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import tw.harry.utils.HibernateUtil;

// 最原生版本 JDBC

public class Harry01 {

	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {  // try-with-resources：跟工廠要一條 Session，離開 try 自動 close()，不用手動關
		 Transaction transaction = session.beginTransaction();  // 開交易：INSERT/UPDATE/DELETE 必須包在交易裡
		 
		 String sql = """
		 			INSERT INTO test
		 				(cname, tel, birthday)
		 			VALUES
		 				(:cname,:tel,:birthday)
		 			""";
		 NativeQuery query = session.createNativeQuery(sql);  // 建原生 SQL 查詢；上面 :cname/:tel/:birthday 是具名參數，防 SQL injection
		 query.setParameter("cname", "harry1");  // 填值：左=參數位子名(對應 SQL 裡 : 後那串)，右=實際值
		 query.setParameter("tel", "111");
		 query.setParameter("birthday", "1999-01-02");
		 
		 int n = query.executeUpdate();  // 執行寫入；回傳受影響列數（插一筆→n=1）
		 System.out.println(n);
		 
		 transaction.commit();  // 提交交易：到這行資料才真正寫入並永久保存（漏了它資料會被退掉）
		}
	}

}
