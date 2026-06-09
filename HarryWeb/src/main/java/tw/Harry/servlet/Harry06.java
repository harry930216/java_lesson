package tw.Harry.servlet;

import java.io.UnsupportedEncodingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Harry06")
public class Harry06 extends HttpServlet {

    public Harry06() {
        System.out.println("Harry06()");
    }
    
    public void init() {
    	
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		}

        String account = request.getParameter("account");
        String passwd = request.getParameter("passwd");
        System.out.println(account);
        System.out.println(passwd);

        response.setContentType("text/html; charset=UTF-8");
    }
}

/* ============================================================
Servlet 後端心智模型 = 三步循環

1. 前端傳值   → 瀏覽器把表單打包成請求(Harry06?account=...&gender=m)
2. 後端處理   → getParameter 收值 → 驗證 / 查DB / 算邏輯
3. 回應前端   → out.print(HTML) / sendRedirect(轉址) / 回 JSON

------------------------------------------------------------
容器(Tomcat)幫你做掉的雜事 —— 你都不用碰：

- 收 HTTP 請求、解析成 request 物件(你才能 getParameter)
- URL 對應到哪支 servlet(靠 @WebServlet 標註)
- new / init / destroy 生命週期(只跑一次的初始化放 init)
- service 自動分流到 doGet / doPost(你只寫 doGet/doPost)
- 多人同時連線的執行緒管理
- 把 response 轉回 HTTP 送回瀏覽器

你唯一要寫的 = 中間那步「業務邏輯」(收值 + 判斷 + 決定回什麼)

------------------------------------------------------------
兩個必記的坑：

- servlet 只 new 一次、所有請求共用
  → 請求資料放「方法內區域變數」,別放成員變數
    (放成員變數會被別的請求蓋掉 = thread safety bug)

- override init / service 時 super 不能省
  → 省掉會斷鏈(config 變 null、doGet 不被呼叫)

------------------------------------------------------------
越高級的框架(Spring)幫你做越多,
但「收 → 處理 → 回」這三步骨架,永遠不變。
============================================================ */