package vendingmachine.states;

import vendingmachine.VendingMachine;
import vendingmachine.interact.ConsoleColors;
import vendingmachine.model.Product;
import vendingmachine.states.StateFactory.States;

public class ChangeReturned  implements State<States, VendingMachine> {
	
	VendingMachine vm;
	States state;
	public ChangeReturned(VendingMachine vm) {
		this.vm = vm;
		this.state = States.CHANGE_RETURNED;
	}


	@Override
	public Integer behave() {
		// TODO Auto-generated method stub
		return dispenseProduct();
	}
	
	private Integer dispenseProduct() {
		Product product = vm.getInventoryManager().getProduct(vm.getRequestedProduct());
		
		try {
			vm.getInventoryManager().reduceProductCount(vm.getRequestedProduct());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(ConsoleColors.PURPLE_BOLD + "Please collect the product : " + product.getProductName() + ConsoleColors.RESET);
		vm.setVendingMachineState(StateFactory.getState(States.PRODUCT_DISPENSED, vm));
		return 1;
	}


}
