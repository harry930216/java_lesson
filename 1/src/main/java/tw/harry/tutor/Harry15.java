package tw.harry.tutor;

import tw.harry.dao.SCDao;
import tw.harry.entity.Course;

// Harry15：建 5 門課程存進 DB
public class Harry15 {

	public static void main(String[] args) {
		SCDao dao = new SCDao();

		dao.save(new Course("Java"));
		dao.save(new Course("JDBC"));
		dao.save(new Course("Tomcat"));
		dao.save(new Course("Hibernate"));
		dao.save(new Course("Spring"));

	}

}
