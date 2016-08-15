package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.service.AsynchService;


@WebServlet("/asynch")
public class CdiAsynchEx extends HttpServlet {
	
	/*This example demonstrates Asynchronous bean and method call. The bean injected must be a EJB and it 
	should have a method annotated with Asynchronous annotation. The Asynch annotation does not work on a normal POJO.
	It requires the bean to be a EJB.
	*/
	
	@Inject
	AsynchService asychSrv;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		asychSrv.doAsynWork();
		
		asychSrv.doSynchWork();
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with Asynch Bean</h3>");  
        out.print("</body></html>");  
	}

}
