/* src/controllers/Beenbumped.java */
package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**Active the JSON for the main web page*/
public class Beenbumped extends HttpServlet {

	private static final long serialVersionUID = -4260962579726500851L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher view = request.getRequestDispatcher("/beenbumped.jsp");
	    view.forward(request, response);
	}


}
