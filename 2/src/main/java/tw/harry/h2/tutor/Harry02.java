package tw.harry.h2.tutor;

import java.beans.Customizer;  // 自己看過：這行是老師 IDE 誤自動匯入的，程式根本沒用到，你可以直接刪掉

import org.hibernate.Session;

import tw.harry.h2.entity.Customer;
import tw.harry.h2.utils.HibernateUtil;

// Harry02：用「主鍵」抓單一筆——session.get(Customer.class, 主鍵值)
public class Harry02 {

	public static void main(String[] args) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			Customer c1 = session.get(Customer.class, "BLONP");  // 主鍵是字串，傳 Northwind 客戶代碼 "BLONP"
			System.out.println(c1.getCname());                   // 印公司名（前提：north.customers 裡要有這筆，否則 c1 會是 null）
		}
	}

}
