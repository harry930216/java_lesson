package tw.harry.learnByMyself;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Step2PreparedStmt {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/learnjdbc";
		String user = "root";
		String password = "root";

		// 開頭 """ 後面必須換行，不能接內容
		/*
		 * Step 2 驗收題（用 product 表） 查「某分類 且 價格 ≤ 某金額」的商品，依價格由低到高排序，印出
		 * name、category、price。
		 * 
		 * 分類、金額都用變數（兩個 ?）。 自己決定用哪個 setXxx、? 編號排幾號。
		 */

		String sql = """
				SELECT * 
				FROM `product`
				WHERE `category` = ?
					AND `price` > ?
				""";
		// ?——不能包任何引號（反引號、單引號、雙引號都不行）。
		// ? 只能綁「值」，不能綁「表名 / 欄名」
		// 因為 ? 是**「預編譯之後才填的洞」，而表名/欄名是「預編譯時就必須先知道的結構」**——時間點對不上。
		
		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, "周邊");
			pstmt.setInt(2, 750);
			ResultSet rs = pstmt.executeQuery();

			// ⚠️ setInt 是「動作」，要在 prepareStatement 之後、executeQuery 之前跑
			// → executeQuery()/rs 不能留在 try( ) 括號裡（那裡只能放宣告）
			// → 挪進 { } 內：先 setInt 塞值，再 executeQuery() 拿 rs
			// pstmt 關了連帶關 rs

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String category = rs.getString("category");
				String price= rs.getString("price");
				String stock = rs.getString("stock");

				System.out.printf("%d %s %s %n", id, name, category, price, stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
