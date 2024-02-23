package vendingmachine.states;

import java.util.Collections;
import java.util.List;

import vendingmachine.VendingMachine;
import vendingmachine.exceptions.ExactChangeUnavailable;
import vendingmachine.exceptions.InsufficientMoneyProvided;
import vendingmachine.model.Product;
import vendingmachine.states.StateFactory.States;

public class CoinsInserted  implements State<States, VendingMachine> {
	
	VendingMachine vm;
	States state;
	public CoinsInserted(VendingMachine vm) {
		this.vm = vm;
		this.state = States.COINS_INSERTED;
	}

	@Override
	public Integer behave() {
		// TODO Auto-generated method stub
		return processTransaction();
	}
	
	private Integer processTransaction() {
		Product product = vm.getInventoryManager().getProduct(vm.getRequestedProduct());
		List<String> coinsInserted = vm.getCoinsInserted();
		List<Double> coinsReturned = Collections.emptyList();
		try {
			coinsReturned = vm.getCoinPoolManager().processTransaction(coinsInserted, product.getAmount());
		} catch (InsufficientMoneyProvided e) {
			vm.setCoinsReturned(false);
			vm.setVendingMachineState(StateFactory.getState(States.PRODUCT_SELECTED, vm));
			return 1;
		}
		catch (ExactChangeUnavailable e) {
			vm.setCoinsReturned(true);
			vm.setCoinsInserted(Collections.emptyList());
			vm.setCoinsToBeReturned(Collections.emptyList());
			vm.setVendingMachineState(StateFactory.getState(States.PRODUCT_SELECTED, vm));
			return 1;
		}
		catch (Exception e) {
			vm.setCoinsReturned(true);
			vm.setVendingMachineState(StateFactory.getState(States.PRODUCT_SELECTED, vm));
			return 1;
		}
		System.out.println("Please collect the change : " + coinsReturned);
		vm.setCoinsReturned(true);
		vm.setVendingMachineState(StateFactory.getState(States.CHANGE_RETURNED, vm));
		return 1;
	}
}
