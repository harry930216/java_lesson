package tw.harry.tutor;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import tw.harry.utils.HibernateUtil;


public class Harry02 {

	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
		 Transaction transaction = session.beginTransaction();  // 註：純查詢其實不需要交易，這裡開了也無妨
		 
		 String sql = """
		 			SELECT * FROM test
		 			""";
		 NativeQuery query = session.createNativeQuery(sql);  // 沒指定型別 → 查詢結果是「原始列」，不對映成物件
		 List list = query.getResultList();  // 查詢用 getResultList()（回傳清單）；沒泛型，每一筆其實是 Object[]
		 System.out.println(list.size());  // 印出查到幾筆
		 for (Object obj : list) {
			 Object[] data = (Object[])obj;  // 每一列是一個 Object[]，要自己轉型
			 System.out.printf("%s:%s:%s:%s\n", data[0], data[1], data[2], data[3]);  // data[0~3] 依序對應 SELECT * 的 id/cname/tel/birthday（靠「位置」取，欄位一改就錯，易踩雷）
		 }
		 
		 transaction.commit();
		}
	}

}

/*
 getResultList() 之後發生什麼？（這支「沒給型別」的版本）

 1. getResultList() 才真正把 SELECT * FROM test 送進 MySQL，一次把所有列抓回記憶體存進 list。
    （前面 createNativeQuery、setParameter 只是準備，還沒送 SQL）

 2. 因為沒給型別（對比 Harry03 給了 Test.class），每一列都被包成一個 Object[]。
    所以 list 是「裝 Object[] 的 List」：
        list[0] ──► Object[]{ id, cname, tel, birthday }
        list[1] ──► Object[]{ ... }

 3. list.size() = 幾列。
    for-each 拿出的 obj，編譯器只當它是 Object，Object 不能用 [] 取值，
    所以要 (Object[])obj 轉型（downcast）回真正身分，才能 data[0]。

 4. data[0~3] 靠「欄位順序」對應 id / cname / tel / birthday；
    表結構一改順序，這裡就默默對錯欄位（靠位置取的缺點）。

 坑：若只查一個欄位（SELECT cname FROM test），每筆回傳的是「單一值」不是 Object[]，
     這時 (Object[])obj 會炸 ClassCastException。多欄才是 Object[]。

 對比 Harry03：給了 Test.class → 回傳 List<Test>，每筆已是 Test 物件，
              不用轉型、不用記順序，直接 test.getCname()。
*/
