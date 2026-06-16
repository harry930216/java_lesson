package tw.harry.servlet;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(location = "C:\\Users\\User\\eclipse-workspace\\HarryWeb\\src\\main\\webapp\\upload") // ← 收檔案必加,沒它
																										// getPart 不會動
@WebServlet("/Harry08") // ← 改成 Harry07,別撞舊的 Harry06
public class Harry08 extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // ← 有 throws,例外往外丟,不用 try

		request.setCharacterEncoding("UTF-8");

		String account = request.getParameter("account");
		System.out.println(account);

		/*
		 * ============================================================ 【後端:請求送達後對 part
		 * 做了什麼】
		 * 
		 * 前提:servlet 要有 @MultipartConfig,Tomcat 才會解析 part (沒它 → getParts()/getPart()
		 * 拿不到東西)
		 * 
		 * Tomcat 收到請求後: 1. 從 header 讀出 boundary(瀏覽器產生的分隔線) 2. 依 boundary 把 body 切回一段一段
		 * 3. 每段包成一個 Part 物件,交給你的 servlet
		 * ============================================================
		 */

		// 拿「全部」part 的集合(同名多檔必用;getPart("upload") 只拿一個、會漏)
		Collection<Part> parts = request.getParts();

		// for-each 逐一取出每個 part(文字欄位、檔案都各算一個 part)
		for (Part part : parts) {
			String type = part.getContentType(); // MIME 類型(image/png);文字欄位為 null
			String name = part.getName(); // 欄位的 name(upload / account)← 不是檔名!
			long size = part.getSize(); // 大小(byte);沒選檔案=0;用 long 防大檔溢位
			String sname = part.getSubmittedFileName(); // 上傳檔名;null/空 = 不是檔案,是文字欄位

			// 存檔判斷:有檔名 且 size>0,才是「真的有上傳檔案」的 part
			// if (sname != null && size > 0) {
			// part.write(sname); // 把這段內容寫成檔案 → 存到 @MultipartConfig 的 location
			// }

			// System.out.printf("%s:%s:%d:%s\n", type, name, size, sname);

			if (name.equals("upload") && size > 0) {
				part.write(String.format("%s_%s", account, sname));
			}
		}

		response.setContentType("text/html; charset=UTF-8");
	}
}