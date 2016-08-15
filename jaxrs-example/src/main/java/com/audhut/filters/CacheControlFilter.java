package com.audhut.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.ext.Provider;

//@Provider
public class CacheControlFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext reqCtx,
			ContainerResponseContext resCtx) throws IOException {
		
		CacheControl cc= new CacheControl();
		cc.setMaxAge(100);
		reqCtx.getHeaders().add("Cache-Control", cc.toString());
		System.out.println("response filter has cache control " + cc.toString());
		
		
	}

}
