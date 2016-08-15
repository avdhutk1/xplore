package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.service.ProductService;



@WebServlet("/altex")
public class CdiAlternativeEx extends HttpServlet {
	
	//servlet to demonstrate alternative object injection using beans.xml
	
	@Inject
	private ProductService prodSrv;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("The injected bean for this request is --->");
		prodSrv.storeProduct();
		
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with alternative</h3>");  
        out.print("</body></html>");  
	}
}
