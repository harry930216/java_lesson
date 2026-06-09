package tw.Harry.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/Harry03")
public class Harry03 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");                // 收進來的內容用 UTF-8 解碼

        Enumeration<String> names = request.getHeaderNames(); // 拿到「所有 header 名稱」的清單
        while (names.hasMoreElements()) {                     // 還有下一個名稱就繼續
            String name  = names.nextElement();               // 取出目前這個 header「名稱」(key)
            String value = request.getHeader(name);           // 用名稱去查出對應的「值」(value)
            System.out.printf("%s : %s%n", name, value);      // 印成「名稱 : 值」到伺服器 Console
        }

        response.setContentType("text/html; charset=UTF-8");  // 回去的內容是 HTML、用 UTF-8
    }
}