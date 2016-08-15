package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.service.ServiceWithInterceptor;

@WebServlet("/interceptor")
public class CdiInterceptorEx extends HttpServlet {
	
	/*This example demonstrates interceptors using CDI. A Service class, ServiceWithInterceptor, has got interceptors
	and whenever a method is invoked on that class, the interceptor is called.The interceptors need to be defined in the beans.xml file*/
	
	@Inject
	ServiceWithInterceptor srv;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		srv.doShipping();
		
		srv.doCreditCheck();
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with Interceptor</h3>");  
        out.print("</body></html>");  
	}

}
