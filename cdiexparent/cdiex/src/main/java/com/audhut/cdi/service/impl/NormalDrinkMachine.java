package com.audhut.cdi.service.impl;

import javax.enterprise.inject.Alternative;

import com.audhut.cdi.service.DrinkMachineService;

@Alternative
public class NormalDrinkMachine implements DrinkMachineService {

	 double randNum;
	 
	 public NormalDrinkMachine(double randNum){
		 this.randNum = randNum;
	 }
	@Override
	public void getDrink() {
		
		System.out.println("This is a NORMAL DRINK  ---->" + randNum);

	}

}
