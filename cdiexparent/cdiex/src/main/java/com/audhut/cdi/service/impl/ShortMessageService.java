package com.audhut.cdi.service.impl;

import com.audhut.cdi.qualifiers.ShortMessage;
import com.audhut.cdi.service.MessageService;

@ShortMessage
public class ShortMessageService implements MessageService {

	@Override
	public void generateMessage() {
		
		System.out.println("This is a short message service");

	}

}
