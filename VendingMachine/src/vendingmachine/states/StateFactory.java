package vendingmachine.states;

import vendingmachine.VendingMachine;

public class StateFactory {
	public static enum States {READY, PRODUCT_SELECTED, COINS_INSERTED, CHANGE_RETURNED, PRODUCT_DISPENSED, TRANSACTION_CANCELLED};
	
	public static State<States, VendingMachine> getState(States state, VendingMachine vm){
		switch (state) {
			case READY :
				return new Ready(vm);
			case PRODUCT_SELECTED :
				return new ProductSelected(vm);
			case COINS_INSERTED:
				return new CoinsInserted(vm);
			case CHANGE_RETURNED:
				return new ChangeReturned(vm);
			case PRODUCT_DISPENSED:
				return new ProductDispensed(vm);
			case TRANSACTION_CANCELLED:
				return new TransactionCancelled(vm);
			default :
				throw new RuntimeException("Unknown state"); 
		}
		
	}

}
