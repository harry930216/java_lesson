package tw.harry.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import tw.harry.entity.Test;  // 自己看過：改用 XML 註冊後，下面 addAnnotatedClass 被註解掉，這個 import 就沒用到了，可刪

public class HibernateUtil {
	private static SessionFactory sessionFactory;  // 整個程式共用同一個（單例）；SessionFactory 很貴，只建一次
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {  // 延遲初始化：第一次呼叫才建，之後直接重用
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");  // 去 classpath 根目錄(resources)讀這個配置檔，把 url/帳密載進來
			
			// ===== 自己看過：今天老師改了 entity 的「註冊方式」=====
			// 原本(到昨天為止)：用「程式碼」一個個註冊 entity，每多一個 entity 就要回來這裡多加一行：
			//     cfg.addAnnotatedClass(Test.class);
			// 今天起：改成在 hibernate.cfg.xml 裡用 <mapping class="..."> 把所有 entity 列出來，
			//     這支 Java 就不用再改了，所以下面這行「註解掉、停用」。
			// 兩種方式效果完全一樣(都是告訴 Hibernate 哪些類別是 entity)，差別只在「寫在 Java 還是寫在 XML」。
			// 去 hibernate.cfg.xml 確認 Test / Member / MemberInfo 三個 <mapping> 都有列到。
			//cfg.addAnnotatedClass(Test.class);
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
