package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.singleton.ConcurrenyBean;

@WebServlet("/write")
public class ConcurrencyWriteServlet extends HttpServlet {
	
	@Inject
	ConcurrenyBean cb;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		cb.write();
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with concurrency management for write</h3>");  
        out.print("</body></html>");  
	}
	
}
