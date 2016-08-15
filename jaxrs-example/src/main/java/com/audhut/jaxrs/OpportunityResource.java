package com.audhut.jaxrs;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.audhut.domain.Design;
import com.audhut.exception.OppException;

@Path("/opportunity")
public interface OpportunityResource {
	
	@GET
	@Path("{oppId}")
	@Produces("application/json")
	public Design getDesign(@PathParam("oppId") String oppId);
	
	@GET
	@Path("/designs")
	@Produces("application/json")
	public Response getDesigns();
	
	@GET
	@Path("/designlist/{oppid}")
	@Produces("application/json")
	public List<Design> getDesignList(String oppId);
	
	@GET
	@Path("/excep")
	@Produces("application/json")
	public Response getDesignExcep() throws OppException;
	
	@POST
	@Path("design")
	@Consumes("application/json")
	@Produces("application/json")
	public Design createDesign(Design dsg);
	
	@GET
	@Path("/httpheaders")
	public Response getHttpHeaders(@Context HttpHeaders headers);
	
	@GET
	@Path("/filters")
	public Response getTestFilters();

}
