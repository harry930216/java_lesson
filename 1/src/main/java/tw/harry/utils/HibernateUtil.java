package tw.harry.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import tw.harry.entity.Test;

public class HibernateUtil {
	private static SessionFactory sessionFactory;  // 整個程式共用同一個（單例）；SessionFactory 很貴，只建一次
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {  // 延遲初始化：第一次呼叫才建，之後直接重用
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");  // 去 classpath 根目錄(resources)讀這個配置檔，把 url/帳密載進來
			
			cfg.addAnnotatedClass(Test.class);  // 用程式碼註冊 entity：告訴 Hibernate「Test 要對映成資料表」(xml 才不用寫 mapping)
			sessionFactory = cfg.buildSessionFactory();  // 配置齊全，建出工廠
		}
		return sessionFactory;  // 已建過就直接回傳同一個
	}
	
	
}

/*
一定要會（能用嘴講出來、面試會問）
為什麼做成單例（singleton）？
→ SessionFactory 建立很貴（要讀設定、掃描 entity、建連線池），所以整個程式只建一次、大家共用。

SessionFactory 跟 Session 差在哪？（前面 JDBC 對照過，這是核心）
→ SessionFactory = 工廠（≈ DataSource），thread-safe、全程式一個；
→ Session = 一次操作的窗口（≈ Connection），不是 thread-safe、用完就關。

延遲初始化（lazy init）在做什麼？
→ if (sessionFactory == null) 第一次呼叫才建，之後直接回傳同一個。
*/
