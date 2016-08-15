package com.audhut.cdi.event;

import javax.enterprise.event.Observes;

public class EventObserver {
	
	public void onEventReceive(@Observes EventObject eventObj){
		
		System.out.println("The event name is ---> " + eventObj.getName());
		System.out.println("The event id is ---> " + eventObj.getId());
		
	}

}
