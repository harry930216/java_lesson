package tw.Harry.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Harry05")  // 對應網址 /Harry05
public class Harry05 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");  // 設定讀取參數的編碼，避免中文亂碼

		// 計算用的變數，先全部給空字串（第一次載入畫面時就是空白）
		String x, y, result, op;
		x = y = result = op = "";

		// 下拉選單「記住上次選的運算子」用的旗標，預設都空字串
		String s1, s2, s3, s4;
		s1 = s2 = s3 = s4 = "";

		try {
		    // 從表單抓使用者輸入；第一次載入沒帶參數時，這裡會是 null
		    x = request.getParameter("x");
		    y = request.getParameter("y");
		    op = request.getParameter("op");
		    int r1, r2;

		    // 依照選到的運算子做對應運算；op 是 null 時 switch 會丟例外 → 跳 catch
		    switch(op) {
		        case "1":r1 = Integer.parseInt(x) + Integer.parseInt(y); result += r1; s1 = "selected";break; // 加
		        case "2":r1 = Integer.parseInt(x) - Integer.parseInt(y); result += r1; s2 = "selected";break; // 減
		        case "3":r1 = Integer.parseInt(x) * Integer.parseInt(y); result += r1; s3 = "selected";break; // 乘
		        case "4":
		            r1 = Integer.parseInt(x) / Integer.parseInt(y);  // 商
		            r2 = Integer.parseInt(x) % Integer.parseInt(y);  // 餘數
		            result += r1 + " ...... " + r2;
		            s4 = "selected";
		            break;
		    }
		    }catch(Exception e) {
		    // 第一次載入(op=null)或輸入非數字時會進來，把欄位重設成空字串 → 畫面不顯示 null
		    x = y = result = "";
			System.out.println(e);
		}
		// ------------------------------------------------
		response.setContentType("text/html; charset=UTF-8");  // 回應也用 UTF-8
			PrintWriter out = response.getWriter();  // 拿到輸出串流，準備把 HTML 寫回瀏覽器

			// 讀取 HTML 樣板檔案的路徑
			String address = "C:/Users/User/eclipse-workspace/HarryWeb/src/main/webapp/Harry05.html";

			// try-with-resources：用完自動關閉 bin，不用自己寫 close()
			try (BufferedInputStream bin = new BufferedInputStream(
				    new FileInputStream(address))) {
				byte[] data = bin.readAllBytes();      // 一次把整個檔案讀成 byte 陣列
				String html = new String(data);        // byte 轉成字串(HTML 樣板，裡面有 %s 佔位)

				// 把 x, s1~s4, y, result 依序填進樣板的 %s，再輸出到瀏覽器
				out.print(String.format(html, x, s1, s2, s3, s4, y, result));
			}
	}
}