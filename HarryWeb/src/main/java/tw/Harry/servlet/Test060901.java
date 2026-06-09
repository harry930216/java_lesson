package tw.Harry.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Test060901")
public class Test060901 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

/*
 
練習題(15 分鐘,從零手寫)
做一個登入判斷功能。

目標規格:

前端一張表單:account、passwd 兩個欄位 + 送出按鈕
送到一支 servlet,自己決定該用 GET 還 POST(想想今天學的:有密碼該用哪個?)
後端讀出 account、passwd,跟固定一組帳密比對(自己設,例如 admin / 1234)
用 getWriter() 回應瀏覽器:
帳密正確 → 顯示「登入成功,歡迎 <account>」
錯誤 → 顯示「帳號或密碼錯誤」
處理沒填的情況:account 或 passwd 是 null / 空,別讓它崩
自我檢查點(寫完對照):

method 選對了嗎?(密碼的考量)
後端方法名跟 method 配對了嗎?(post → 哪個方法)
編碼順序對嗎?
帳密用的變數是區域還成員?
null 處理了嗎?
 */
*/