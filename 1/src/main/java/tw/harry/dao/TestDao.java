package tw.harry.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tw.harry.entity.Test;
import tw.harry.utils.HibernateUtil;

public class TestDao {
	public void addTest(Test test) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.persist(test);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void delTest(Test test) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.remove(test);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void updateTest(Test test) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.merge(test);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public Test findById(long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			return session.get(Test.class, id);
		} catch (Exception e) {
			
		}
		return null;
		// 這裡要去了解
	}

	public List<Test> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			String hql = "FROM test";
			Query<Test> query = session.createQuery(hql, Test.class);
			return query.getresult
		} catch (Exception e) {
			
		}
		return null;
	}
}
