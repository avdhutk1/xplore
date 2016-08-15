package com.audhut.cdi.singleton;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
//@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ConcurrenyBean {
	/*
	This example demonstrates the singleton bean and concurrency. Default concurrency is write, i.e. all methods are locked.
	If you want concurrent access to Read methods, use the read lock as shown below in the read method.
	Run the read servlet in 2 tabs and you will see that both threads can access Read methods.
	Run the write servlet and read servlet in another tab. The access to read is blocked till the write release the lock.  
	*/
	//@Lock(LockType.READ)
	public void read(){
		System.out.println("concurrency bean is reading");
		
		System.out.println("system time is -->" + System.currentTimeMillis());
		try{
			Thread.sleep(120000);
			
			}catch(Exception e){}
	}

	//@Lock(LockType.WRITE)
	public void write(){
		System.out.println("concurrency bean is writing");
		System.out.println("system time is -->" + System.currentTimeMillis());
		try{
		Thread.sleep(120000);
		}catch(Exception e){}
	}
	
}
