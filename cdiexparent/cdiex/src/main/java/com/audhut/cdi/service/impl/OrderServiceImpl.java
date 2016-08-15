package com.audhut.cdi.service.impl;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import com.audhut.cdi.service.OrderService;

@RequestScoped
public class OrderServiceImpl implements OrderService {

		
	@Override
	public void storeOrder() {
		System.out.println("Order Service 1 is being used");

	}

	@PostConstruct
	public void onNewRequest(){
		INSTANCE_COUNT.incrementAndGet();
	}

	
}
