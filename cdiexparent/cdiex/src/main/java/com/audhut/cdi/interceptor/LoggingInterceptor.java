package com.audhut.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LoggingInterceptor {
	
	@AroundInvoke
	public Object doLogging(InvocationContext ctx) throws Exception{
		
		//This example also demonstrates that the return value of a method can also be obtained or intercepted
		
		System.out.println("interceptor is now being invoked");
		
		//get the return value
		Object result = ctx.proceed();
		
		if (result instanceof Boolean){
			System.out.println("boolean is returned");
		}else{
			System.out.println("not a boolean");
		};
		
		return ctx.proceed();
	}

}
