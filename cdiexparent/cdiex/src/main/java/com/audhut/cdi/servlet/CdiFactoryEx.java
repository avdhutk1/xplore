package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.qualifiers.DrinkMachine;
import com.audhut.cdi.service.DrinkMachineService;

@WebServlet("/factory")
public class CdiFactoryEx extends HttpServlet {
	
	//This is an example of CDI using the factory pattern
	//Objects are injected using the @Produces annotation in the factory
	@Inject
	@DrinkMachine(DrinkMachine.Type.SOFTDRINKMACHINE)
	private DrinkMachineService softDrink;
	
	@Inject
	@DrinkMachine(DrinkMachine.Type.NORMALDRINKMACHINE)
	private DrinkMachineService normalDrink;
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		softDrink.getDrink();
		normalDrink.getDrink();
		
		System.out.println("servlet is ---> " + this);
		System.out.println("soft drink machine is -->" + this.softDrink);
		System.out.println("normal drink machine is -->" + this.normalDrink);
	
		
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with factory pattern</h3>");  
        out.print("</body></html>");  
	}

}
