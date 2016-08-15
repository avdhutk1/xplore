package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.service.BidService;

@WebServlet("/ejbex")
public class EjbStatelessEx extends HttpServlet {
	
	/*This example demonstrates how an EJB can be injected into a web module.
	It also demonstrates that if there are 2 EJB's implementing the same interface, then the @EJB inject with beanName
	can be used. 
	EDI can also be used to inject EJB using @Inject but if there are more than one EJB implementing the same interface
	it requires qualifiers*/
	
	//@EJB(name="defaultBid")
	@EJB(beanName="specificBid")
	//@EJB(lookup="java:module/earex-1.0-SNAPSHOT/ejbex-1.0-SNAPSHOT/defaultBid!com.audhut.service.BidService")
	BidService bidSrv;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		System.out.println("Bid service is being called");
		
		bidSrv.doBid();
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>EJB injection example</h3>");  
        out.print("</body></html>");  
	}

}
