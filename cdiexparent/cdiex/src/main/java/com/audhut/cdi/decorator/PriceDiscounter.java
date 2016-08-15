package com.audhut.cdi.decorator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.audhut.cdi.qualifiers.ClearenceSale;

@Decorator
public class PriceDiscounter implements Furniture {

	@Inject
	@ClearenceSale
	@Delegate
	Furniture fur;
	
	@Override
	public String getPrice() {
		
		String disc = fur.getPrice() + "10%";
		
		return disc;
	}

	@Override
	public String getLabel() {
		
		String lbl = fur.getLabel() + "disc";
		return lbl;
	}

}
