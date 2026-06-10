package tw.Harry.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tw.Harry.model.CalsModel;

import java.io.IOException;

@WebServlet("/CalcController")
public class CalcController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Client
		request.setCharacterEncoding("UTF-8");
		
		
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		
		// 2. Model
		CalsModel calcmodel = new CalsModel(x,y);
		
		// 3. Viewer => forward
		RequestDispatcher dispatcher = request.getRequestDispatcher("CalcViewer");
		response.setContentType("text/html; charset=UTF-8");
		dispatcher.forward(request, response);
		
	}
}