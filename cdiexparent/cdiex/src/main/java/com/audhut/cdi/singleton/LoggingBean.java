package com.audhut.cdi.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class LoggingBean {
	
	@PostConstruct
	public void start() {
		
		System.out.println("Logging bean is starting now");
	}

}
