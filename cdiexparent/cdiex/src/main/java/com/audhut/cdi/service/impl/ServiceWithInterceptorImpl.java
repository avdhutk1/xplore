package com.audhut.cdi.service.impl;

import javax.interceptor.Interceptors;

import com.audhut.cdi.interceptor.LoggingInterceptor;
import com.audhut.cdi.service.ServiceWithInterceptor;

@Interceptors(LoggingInterceptor.class)
public class ServiceWithInterceptorImpl implements ServiceWithInterceptor {

	@Override
	public void doShipping() {
		System.out.println("Shipping method called");
	
	}

	@Override
	public boolean doCreditCheck() {
		
		System.out.println("credit check method called");
		boolean success = true;
		return success;

	}

}
