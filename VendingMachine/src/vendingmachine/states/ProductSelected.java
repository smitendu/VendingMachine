package vendingmachine.states;

import java.util.Collections;
import java.util.List;

import vendingmachine.VendingMachine;
import vendingmachine.interact.UserInputSeeker;
import vendingmachine.states.StateFactory.States;

public class ProductSelected  implements State<States, VendingMachine>, UserInputSeeker {
	VendingMachine vm;
	States state;

	public ProductSelected(VendingMachine vm) {
		this.vm = vm;
		this.state = States.PRODUCT_SELECTED;
	}
	
	@Override
	public Integer behave() {
		return collectCash();		
	}
	
	public Integer collectCash() {

		List<String> coinsInserted = Collections.emptyList();
		coinsInserted = inputHandler.collectCashMenu(vm);
		if(coinsInserted.contains("EXIT")) {
			coinsInserted.remove("EXIT");
			vm.setCoinsInserted(coinsInserted);
			vm.setCoinsToBeReturned(coinsInserted);
			vm.shutDown();
			return -1;
		}
		else if(coinsInserted.contains("CANCEL")) {
			coinsInserted.remove("CANCEL");
			vm.setCoinsInserted(coinsInserted);
			vm.setVendingMachineState(StateFactory.getState(States.TRANSACTION_CANCELLED, vm));
			return 1;
		} else {
			vm.setCoinsInserted(coinsInserted);
			vm.setVendingMachineState(StateFactory.getState(States.COINS_INSERTED, vm));
			return 1;
		}
					
	}


}
