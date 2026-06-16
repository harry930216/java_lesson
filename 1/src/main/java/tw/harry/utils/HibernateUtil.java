package tw.harry.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import tw.harry.entity.Test;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			
			cfg.addAnnotatedClass(Test.class);
			sessionFactory = cfg.buildSessionFactory();
		}
		return sessionFactory;
	}
	
	
}
