package vendingmachine.states;

import java.util.Collections;

import vendingmachine.VendingMachine;
import vendingmachine.states.StateFactory.States;

public class TransactionCancelled  implements State<States, VendingMachine> {
	VendingMachine vm;
	States state;
	public TransactionCancelled(VendingMachine vm) {
		this.vm = vm;
		this.state = States.TRANSACTION_CANCELLED;
	}

	public Integer cancelTransaction() {
		System.out.println("Returning money you entered : " + vm.getCoinsInserted());
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
		return cancelTransaction();
	}

}
