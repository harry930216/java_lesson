package tw.Harry.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
		location = 
		"C:\\Users\\User\\eclipse-workspace\\HarryWeb\\src\\main\\webapp\\upload"
)                       // ← 收檔案必加,沒它 getPart 不會動
@WebServlet("/Harry07")                // ← 改成 Harry07,別撞舊的 Harry06
public class Harry07 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    // ← 有 throws,例外往外丟,不用 try

        request.setCharacterEncoding("UTF-8");

        Part part = request.getPart("upload");        // upload 要對到前端 <input name="upload">
        String type  = part.getContentType();
        String name  = part.getName();
        long   size  = part.getSize();
        String sname = part.getSubmittedFileName();

        System.out.println(type);
        System.out.println(name);
        System.out.println(size);
        System.out.println(sname);
        
        if (size > 0) {
        	part.write(sname);
        }else {
        	System.out.println("failed");
        }

        response.setContentType("text/html; charset=UTF-8");
    }
}