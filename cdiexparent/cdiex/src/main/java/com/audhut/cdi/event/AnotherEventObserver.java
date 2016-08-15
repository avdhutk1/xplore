package com.audhut.cdi.event;

import javax.enterprise.event.Observes;

public class AnotherEventObserver {
	
	public void eventReceived(@Observes EventObject eObj){
		
		System.out.println("The event name for another observer is ---> " + eObj.getName());
		System.out.println("The event id for another observer is ---> " + eObj.getId());
		
	}

}
