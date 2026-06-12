package tw.harry.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class scannerNinput {
	// 1.先用scanner取得資料 
	// 2.注入資料庫
	// 3.在終端機顯示輸入之欄位
	
	public static void main(String[] args) {

        // ───── 1. 用 Scanner 取得資料　★換你寫★ ─────
        // 開 Scanner，讀 cname / tel / birthday 三個變數
		Scanner s = new Scanner(System.in);
		System.out.print("請輸入名字:");
		String cname = s.nextLine();
		System.out.print("請輸入電話:");
		String tel = s.nextLine();
		System.out.print("請輸入生日:");
		String birthday = s.nextLine();
		System.out.println(cname);
		System.out.println(tel);
		System.out.println(birthday);

        // ───── 連線設定（骨架，給你）─────
        final String URL = "jdbc:mysql://localhost:3306/harry";
        Properties prop = new Properties();
        prop.put("user", "root");
        prop.put("password", "root");
        prop.put("useSSL", "false");

        // ───── 2. 你的 SQL　★換你寫★ ─────
        // 寫一句 INSERT，VALUES 用上面讀到的變數塞進 test
        final String SQLINSERT = """
        		INSERT INTO test
        		(cname, tel, birthday)
        		VALUES
        		('%s','%s','%s')
        		""";

        final String SQLSELECT = """
        		SELECT * FROM test
        		""";
        
        // ───── 連線 → 送出 → 結果（骨架，給你）─────
        try (Connection conn = DriverManager.getConnection(URL, prop);
             Statement  stmt = conn.createStatement();
            ) {

            int changes = stmt.executeUpdate(SQLINSERT.formatted(cname, tel, birthday));
            System.out.println(changes);

            // ───── 3. 顯示輸入的欄位　★換你寫★ ─────
            // 把剛剛 Scanner 讀到的變數印出來
            ResultSet result = stmt.executeQuery(SQLSELECT);
            
            while(result.next()) {
            	System.out.printf("\tid: %s  ",result.getString("id")); 
            	System.out.printf("\tcname: %s  ",result.getString("cname")); 
            	System.out.printf("\ttel: %s  ",result.getString("tel")); 
            	System.out.printf("\tbirthday: %s",result.getString("birthday")); 
            	System.out.println();
            }
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

// 記得防SQL injection 注入那換?
// try那改prepare statement



// 拿公開資料表 注入
// 來做練習
// 複習串流 再來接入

