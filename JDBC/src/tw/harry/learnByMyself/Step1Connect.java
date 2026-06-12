package tw.harry.learnByMyself;

// ── import：【次要】Eclipse 自動補，不用背拼字，知道五個角色即可
import java.sql.Connection;    // 連線
import java.sql.DriverManager; // 工廠
import java.sql.ResultSet;     // 結果游標
import java.sql.SQLException;  // 例外（今天不是重點）
import java.sql.Statement;     // 送 SQL 的信差

public class Step1Connect {

	public static void main(String[] args) {
		// ── 連線三要素：【觀念重要，寫法次要】
		//    要懂「連去哪 / 誰 / 密碼」；但實務這三個會搬到設定檔 + 連線池，不寫死在程式裡。
		String url = "jdbc:mysql://localhost:3306/learnjdbc";
		String user = "root";
		String password = "root";

		try (
			// ═══ try-with-resources 關閉規則（重點：它看「變數」）═══
			//   它只關「在這括號裡『宣告成變數』的東西」，一個變數關一個。
			//   conn / stmt / rs 三個各自宣告 → 三個都會被關（離開時倒序 rs → stmt → conn）。
			//   省掉哪個變數，哪個就沒人關 → 連線洩漏（connection leak）。
			//   ❌ 若串成 .createStatement().executeQuery() 只接 rs，conn、stmt 沒變數 → 漏掉。
			//   （關閉只往下傳：關 conn 會連帶關 stmt/rs；關 rs 不會往上關 stmt/conn。）
			//
			// ── 第 1 節【★核心動作，但 DriverManager 是「入門形」】
			//    重點是「拿到一條 Connection」這件事，不是 DriverManager 本身。
			//    👉 框架/實務：改用連線池 dataSource.getConnection()（HikariCP），不再用 DriverManager。
			Connection conn = DriverManager.getConnection(url, user, password);

			// ── 第 2 節【★核心，但下一步就被換掉】
			//    重點是「從連線開一個送 SQL 的物件」。
			//    👉 主幹 Step 2：換成 conn.prepareStatement(sql)（PreparedStatement，可防 SQL injection）。
			//    👉 框架：MyBatis / JPA 把這一段整個包掉，你只呼叫一個方法。
			Statement stmt = conn.createStatement();

			// ── 第 3 節【★核心：執行 + 拿到結果】
			//    SELECT(讀) → executeQuery；INSERT/UPDATE/DELETE(寫) → executeUpdate（對稱）。
			//    👉 框架：變成 repository / mapper 的一個方法呼叫。
			ResultSet rs = stmt.executeQuery(
					"""
					SELECT * FROM member
					""")
		)
		{
			// ── 把結果「讀進 Java 變數」：【★核心，這段就是 ORM 在做的事，手工版】
			//    next() 把游標往下移一列（起點在第一列之前）；getXxx 用欄位名取值，型別要對。
			//    👉 框架：RowMapper / @Entity 自動把一列(row) 變成一個物件，你就不用自己 getXxx。
		    while (rs.next()) {
		        int id = rs.getInt("id");             // INT → getInt
		        String name = rs.getString("name");   // VARCHAR → getString
		        String email = rs.getString("email");
		        System.out.println(id + " | " + name + " | " + email);
		    }

		} catch (SQLException e) {
			// 例外處理（今天不是重點，先這樣讓它能跑）
			System.out.println("連線失敗" + e);;
		}

	}

}
