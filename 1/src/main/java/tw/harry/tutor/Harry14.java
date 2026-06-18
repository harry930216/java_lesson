package tw.harry.tutor;

import tw.harry.dao.SCDao;
import tw.harry.entity.Student;

// Harry14：建 4 個學生存進 DB（多對多的資料準備）
public class Harry14 {

	public static void main(String[] args) {
		SCDao dao = new SCDao();

		dao.save(new Student("harry"));
		dao.save(new Student("eric"));
		dao.save(new Student("tony"));
		dao.save(new Student("mark"));

	}

}
