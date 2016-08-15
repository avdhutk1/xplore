package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.decorator.Furniture;
import com.audhut.cdi.qualifiers.ClearenceSale;


@WebServlet("/decorator")
public class CdiDecoratorEx extends HttpServlet {
	
	@Inject
	@ClearenceSale
	Furniture tble;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		System.out.println("The price is --> "+ tble.getPrice());
		
		System.out.println("The label is --> "+ tble.getLabel());
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with decorator pattern</h3>");  
        out.print("</body></html>");  
	}

}
