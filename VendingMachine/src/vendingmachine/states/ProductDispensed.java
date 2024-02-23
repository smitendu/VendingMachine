package vendingmachine.states;

import java.util.Collections;

import vendingmachine.VendingMachine;
import vendingmachine.states.StateFactory.States;

public class ProductDispensed  implements State<States, VendingMachine>  {
	
	VendingMachine vm;
	States state;
	public ProductDispensed(VendingMachine vm) {
		this.vm = vm;
		this.state = States.PRODUCT_DISPENSED;
	}

	public Integer getReady() {
		System.out.println("Transaction completete");
		vm.setCoinsInserted(Collections.emptyList());
		vm.setCoinsToBeReturned(Collections.emptyList());
		vm.setRequestedProduct(null);
		vm.setCoinsReturned(true);
		vm.setVendingMachineState(StateFactory.getState(States.READY, vm));
		return 1;
	}

	@Override
	public Integer behave() {
		// TODO Auto-generated method stub
		return getReady();
	}

}
