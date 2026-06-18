package tw.harry.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tw.harry.entity.Course;
import tw.harry.entity.Student;
import tw.harry.utils.HibernateUtil;

// SCDao：Student / Course 的資料存取（多對多用）。
// ===== 自己看過（兩個點）=====
// 1) 這支回到「DAO 自己開關 session」的寫法（像 TestDao/MemberDao），不是 OrderDao 那種「session 由外面傳入」。
//    老師這個新主題先用簡單版；兩種風格你都看過了，知道差在「交易控制權在誰手上」即可。
// 2) save(Student) 和 save(Course) 是「方法多載(overload)」：同名、參數型別不同，編譯器依你傳入的型別選對的那個。
public class SCDao {
	public void save(Student student) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(student);
			transaction.commit();
		} catch (Exception e) {
			System.out.println(e);
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void save(Course course) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(course);
			transaction.commit();
		} catch (Exception e) {
			System.out.println(e);
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public Student update(Student student) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(student);
			transaction.commit();
			return getStudentById(student.getSid());  // 更新後重新查一次，回傳最新狀態（含剛加的課）
		} catch (Exception e) {
			System.out.println(e);
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return null;
	}

	public Student getStudentById(Long sid) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Student.class, sid);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public Course getCourseById(Long cid) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Course.class, cid);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public List<Course> getAllCourse() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Course", Course.class).getResultList();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
