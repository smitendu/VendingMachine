package vendingmachine.states;

import vendingmachine.states.StateFactory.States;

import vendingmachine.VendingMachine;
import vendingmachine.model.Product;

public class Ready implements State<States, VendingMachine> {
	VendingMachine vm;
	States state;
	public Ready(VendingMachine vm) {
		this.vm = vm;
		this.state = States.READY;
	}

	public Integer selectProduct() {
		Integer slotId = seeker.selectProductMenu(vm);
		if(slotId == -1) {
			vm.shutDown();
			return -1;
		}
		else {
			vm.setRequestedProduct(slotId);
			System.out.println("You selected : " + slotId);
			Product product = vm.getInventoryManager().getProduct(slotId);
			System.out.println(product.getProductName() + " costs " + product.getAmount());
			vm.setVendingMachineState(StateFactory.getState(States.PRODUCT_SELECTED, vm));
			return 1;
		}
	}

	@Override
	public Integer behave() {
		return selectProduct();
	}

}
