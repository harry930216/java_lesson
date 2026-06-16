package tw.harry.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Harry02")
public class Harry02 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");                    // ← 新增:收進來用 UTF-8 解碼(對 POST body 有效)
        
        response.getWriter()
        .append("測試")
        .append("123");
        response.setContentType("text/html; charset=UTF-8");      // ← 新增:回去的內容是 HTML、用 UTF-8(防中文亂碼)
    }
}