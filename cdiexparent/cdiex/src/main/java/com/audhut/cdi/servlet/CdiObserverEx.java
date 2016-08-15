package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.service.EventService;

@WebServlet("/observer")
public class CdiObserverEx extends HttpServlet {
	
/*	This example demonstrates the observer pattern. You fire an event in the eventService classs
	and 2 other classes have methods which observe the event - with the @Observes annotation.
	The 2 classes receive the event.
	*/
	
	@Inject
	EventService evntSrv;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		System.out.println("calling Event Service --->");
		
		evntSrv.generateEvent();
				
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example of an Observer pattern</h3>");  
        out.print("</body></html>");  
	}

}
