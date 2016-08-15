package com.audhut.cdi.service.impl;

import javax.enterprise.inject.Alternative;

import com.audhut.cdi.service.ProductService;

@Alternative
public class ProductServiceImpl2 implements ProductService {
	
	//alternative annotation means it is injected via thebeans.xml or factory methods

	@Override
	public void storeProduct() {
		System.out.println("The product service implemetnaion is ProductService Impl2");
	}

}
