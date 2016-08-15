package com.audhut.cdi.service.impl;

import com.audhut.cdi.service.AsynchService;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;

@Singleton
public class AsynchServiceImpl implements AsynchService {

	@Override
	@Asynchronous
	public void doAsynWork() {
		
		System.out.println("Asynch method is being called");
		
		try{
			Thread.sleep(60000);
			System.out.println("Asynch method compelted");
		}catch(Exception e){
			
		}

	}

	@Override
	public void doSynchWork() {
		System.out.println("Synch method is being called");

	}

}
