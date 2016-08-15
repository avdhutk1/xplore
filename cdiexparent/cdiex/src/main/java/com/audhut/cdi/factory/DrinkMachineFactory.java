package com.audhut.cdi.factory;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import com.audhut.cdi.qualifiers.DrinkMachine;
import com.audhut.cdi.service.DrinkMachineService;
import com.audhut.cdi.service.impl.NormalDrinkMachine;
import com.audhut.cdi.service.impl.SoftDrinkMachine;

public class DrinkMachineFactory {
	
	//one is request scoped and the other is not
	
	@Produces
	@DrinkMachine(DrinkMachine.Type.SOFTDRINKMACHINE)
	@RequestScoped
	public DrinkMachineService getSoftDrinkMachine(){
		
		return new SoftDrinkMachine(Math.random());
	}

	@Produces
	@DrinkMachine(DrinkMachine.Type.NORMALDRINKMACHINE)
	public DrinkMachineService getNormalDrinkMachine(){
		
		return new NormalDrinkMachine(Math.random());
	}
}
