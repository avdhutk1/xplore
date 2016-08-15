package com.audhut.cdi.service;

import java.util.concurrent.atomic.AtomicLong;

public interface OrderService {
	
	public static AtomicLong INSTANCE_COUNT = new AtomicLong(0);
	
	public void storeOrder();
	
	

}
