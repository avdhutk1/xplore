package com.audhut.cdi.service.impl;

import com.audhut.cdi.service.MessageService;
import com.audhut.cdi.qualifiers.LongMessage;;

@LongMessage
public class LongMessageService implements MessageService {

	@Override
	public void generateMessage() {
		
		System.out.println("This is a long message service");

	}

}
