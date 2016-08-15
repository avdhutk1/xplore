package com.audhut.cdi.service.impl;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.audhut.cdi.event.EventObject;
import com.audhut.cdi.service.EventService;

public class EventServiceImpl implements EventService {

	@Inject
	Event<EventObject> evntObj;
	
	@Override
	public void generateEvent() {
		
		EventObject evtOb = new EventObject();
		evtOb.setName("eventName");
		evtOb.setId("evnt123");
		
		System.out.println("event is being fired---->");
		
		evntObj.fire(evtOb);
		
		

	}

}
