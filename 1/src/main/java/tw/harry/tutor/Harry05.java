package tw.harry.tutor;

import tw.harry.dao.TestDao;
import tw.harry.entity.Test;

public class Harry05 {

	public static void main(String[] args) {
		Test test = new Test();
		test.setCname("harry5");
		test.setTel("222");
		test.setBirthday("1999-05-05");
		
		new TestDao().addTest(test);
	}

}

// 還沒改