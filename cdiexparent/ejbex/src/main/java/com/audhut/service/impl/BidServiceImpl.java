package com.audhut.service.impl;

import javax.ejb.Stateless;

import com.audhut.service.BidService;

@Stateless(name="specificBid")
public class BidServiceImpl implements BidService {

	@Override
	public void doBid() {
		
		System.out.println("Specific Bid service is called");

	}

}
