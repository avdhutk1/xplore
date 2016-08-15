package com.audhut.service.impl;

import javax.ejb.Stateless;

import com.audhut.service.BidService;

@Stateless(name="defaultBid")
public class DefaultBidServiceImpl implements BidService {

	@Override
	public void doBid() {
		System.out.println("Default Bid service is called");

	}

}
