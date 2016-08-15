package com.audhut.cdi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.audhut.cdi.qualifiers.LongMessage;
import com.audhut.cdi.qualifiers.ShortMessage;
import com.audhut.cdi.service.MessageService;

@WebServlet("/qualify")
public class CdiQualifierEx extends HttpServlet {
	
	@Inject @LongMessage
	private MessageService longMessage;
	
	@Inject @ShortMessage
	private MessageService shortMessage;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		longMessage.generateMessage();
		shortMessage.generateMessage();
		
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        out.print("<html><body>");  
        out.print("<h3>CDI Example with qualifier</h3>");  
        out.print("</body></html>");  
	}

	
	
	

}
