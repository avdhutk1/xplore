package com.audhut.cdi.decorator;

import com.audhut.cdi.qualifiers.ClearenceSale;

@ClearenceSale
public class Table implements Furniture {

	@Override
	public String getPrice() {
		
		return "100";
	}

	@Override
	public String getLabel() {
		
		return "Table";
	}

}
