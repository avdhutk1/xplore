package com.audhut.jaxrs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.audhut.domain.Design;
import com.audhut.exception.OppException;
import com.audhut.jaxrs.OpportunityResource;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.NotFoundException;

public class OpportunityResourceImpl implements OpportunityResource {

	// this is to prove the return type is automatically jsonified
	@Override
	public Design getDesign(String oppId) {
		
		System.out.println("opportunity instance is " + this);
		
		Design dsg = new Design();
		dsg.setDesignId("design123");
		//return Response.ok().entity(dsg).build();
		return dsg;
	}

	// this is to prove the return type of Collections is automatically jsonified
	@Override
	public List<Design> getDesignList(String oppId){
		List<Design> lst = new ArrayList<Design>();
	
		for (int i=0; i<2; i++){
			Design dsg = new Design();
			dsg.setDesignId("designid" + i);
			lst.add(dsg);
		
		}
	
		return lst;
	}	
	
	// this is to prove how the http response types,codes can be set and as send the return type
	public Response getDesigns(){
		
		List<Design> lst = new ArrayList<Design>();
		
		for (int i=0; i<2; i++){
			Design dsg = new Design();
			dsg.setDesignId("designid" + i);
			lst.add(dsg);
			
		}
		
		return Response.ok(lst).build();
		
	}
	
	//to prove how exception mapper works
	public Response getDesignExcep() throws OppException{
		
		Design dsg = new Design();
		dsg.setDesignId("design123");
		if (true){
			throw new OppException("4500", "error in getting data");
		}
		
		return Response.ok().build();
		
	}
	
	// to prove how the input object is automatically converted
	public Design createDesign(Design dsg){
		
		String dsgId = dsg.getDesignId();
		
		Design outDsg = new Design();
		outDsg.setDesignId(dsgId + "out");
		
		return outDsg;
		
	}
	
	// to prove how HTTP headers can be retrieved
	public Response getHttpHeaders(HttpHeaders headers){
		MultivaluedMap<String, String> strMap  = headers.getRequestHeaders();
		
		Set<String> st = strMap.keySet();
		
		for(String key : st){
			System.out.println("the key is: " + key + " and the value is: " + strMap.get(key));
		}
		
		return Response.ok().build();
		
	}
	
	// to prove request and response filters
	public Response getTestFilters(){
		return Response.ok().build();
		
	}
	
}
