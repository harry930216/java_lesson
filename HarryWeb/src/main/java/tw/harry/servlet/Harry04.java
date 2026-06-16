package tw.harry.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Harry04")
public class Harry04 extends HttpServlet {
	public Harry04() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); 

		String x = request.getParameter("x");
		String y = request.getParameter("y");
		System.out.printf("%s + %s = %n",x, y);
		// ------------------------------------------------
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			int answer = Integer.parseInt(x) + Integer.parseInt(y);
			out.format("%s + %s = %d",x,y,answer);
		}catch(Exception e) {
			out.println("error");
		}
	}
}
