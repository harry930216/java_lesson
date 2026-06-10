package tw.Harry.viewer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.Harry.apis.HarryUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CalcViewer")
public class CalcViewer extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		String webContent;
		
		try {
			webContent = HarryUtils.loadView();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		out.print(webContent);
	}

}
