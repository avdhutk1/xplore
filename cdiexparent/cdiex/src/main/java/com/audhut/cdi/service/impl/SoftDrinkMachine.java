package com.audhut.cdi.service.impl;

import javax.enterprise.inject.Alternative;

import com.audhut.cdi.service.DrinkMachineService;

@Alternative
public class SoftDrinkMachine implements DrinkMachineService {
	
	double ranNum;
	
	public SoftDrinkMachine(double ranNum) {
		this.ranNum = ranNum;
	}


	public double getRanNum() {
		return ranNum;
	}
	
	
	@Override
	public void getDrink() {
		
		System.out.println("This is a SOFTDRINK ---->" + ranNum);
	}

}
