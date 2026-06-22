package tw.harry.h2.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// HibernateUtil：產生並快取「唯一的」SessionFactory（它建立成本很高，整個程式共用一個就好）
public class HibernateUtil {
	private static SessionFactory sessionFactory;  // static：全程式共用一份

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {           // 第一次呼叫才建立（lazy 初始化）
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");  // 讀 resources 下的設定檔（DB 連線 + entity 註冊都寫在那）

			//cfg.addAnnotatedClass(Test.class);  // 自己看過：改用 cfg.xml 的 <mapping> 註冊後，這種程式碼註冊就不需要了

			sessionFactory = cfg.buildSessionFactory();
		}
		return sessionFactory;
	}

}
