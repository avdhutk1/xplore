package com.audhut.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.PreMatching;


@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		
		//String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
		MultivaluedMap<String, String> strMap = ctx.getHeaders();
		
		String authHeader = strMap.getFirst("Authorization");
		System.out.println("header is " + authHeader);
		
		
	}

}
