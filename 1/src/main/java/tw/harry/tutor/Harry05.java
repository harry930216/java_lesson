package tw.harry.tutor;

import java.util.List;

//import java.util.List;   // ← 取消下面「查全部」那段註解時，這行要一起取消
import tw.harry.dao.TestDao;
import tw.harry.entity.Test;

public class Harry05 {

	public static void main(String[] args) {
		Test test = new Test();
		test.setCname("harry5");
		test.setTel("222");
		test.setBirthday("1999-05-05");

		new TestDao().addTest(test);

		TestDao dao = new TestDao();

		// 查單筆：用主鍵查（id 請依你 DB 實際有的值改，例如 1）
//		Test t2 = dao.findById(1);
//		if (t2 != null) {
//			System.out.println(t2.getId());
//			System.out.println(t2.getCname());
//			System.out.println(t2.getTel());
//			System.out.println(t2.getBirthday());
//			t2.setCname("newAAA");
//			// dao.updateTest(t2); // 想測「更新」再打開
//			// dao.delTest(t2); // 想測「刪除」再打開
//		}

		System.out.println("---");

		// 查全部：迴圈印出每筆 cname
		List<Test> tests = dao.findAll();
//		System.out.println(tests.get(0)); //tw.harry.entity.Test@71789580
		for (Test tt : tests) {
			System.out.println(tt.getCname());
		}
	}

}

// 還沒改