package com.audhut.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


public class OppExceptionMapper implements ExceptionMapper<OppException>{

	@Override
	public Response toResponse(OppException ex) {
		
		//return Response.status(Response.Status.NO_CONTENT).build();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getErrorObject()).build();
			
		
	}
	
 	

}
