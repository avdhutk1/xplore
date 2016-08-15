package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


import com.audhut.cdi.service.OrderService;

@WebServlet("/cdiex")
public class CdiServletEx extends HttpServlet {
	
	//servlet to check CDI injection and scope.
	//The injected bean has to have the scope annotation
	
	@Inject
	private OrderService ordSrv;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("The servlet is ----> " + this);
		System.out.println("The injected bean for this request is --->" + ordSrv);
		
		System.out.println("The injected bean count is --> " + ordSrv.INSTANCE_COUNT);
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Examples</h3>");  
        out.print("</body></html>");  
	}
	
	

}
