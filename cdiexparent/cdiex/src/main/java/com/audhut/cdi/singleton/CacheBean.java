package com.audhut.cdi.singleton;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
@DependsOn(value = { "LoggingBean" })
public class CacheBean {
	
	/*This example demonstrates singleton bean at startup. If there are multiple 'startup' beans, you can specify the dependencies
	using the @DependsOn annotation, In the above example, Cachebean is dependent on the logging bean and the logging bean is started first */
	
	private Map<String, String> cache;
	
	@Inject
	LoggingBean logBean;
	
	@PostConstruct
	public void start(){
		System.out.println("The cache bean is starting now");
	}

}
