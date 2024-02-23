package vendingmachine;

import java.util.List;

import vendingmachine.states.StateFactory;
import vendingmachine.states.StateFactory.States;
import vendingmachine.manager.CoinPoolManager;
import vendingmachine.manager.InventoryManager;
import vendingmachine.states.State;

public class VendingMachine {

	State<States, VendingMachine> state;
	CoinPoolManager cpm;
	InventoryManager im;
	
	Integer requestedProduct;
	List<String> coinsInserted;
	List<String> coinsToBeReturned;
	boolean coinsReturned;

	public VendingMachine() throws Exception {
		cpm = new CoinPoolManager();
		im = new InventoryManager();
		this.state = StateFactory.getState(States.READY, this);
	}
	
	public State<States, VendingMachine> getVendingMachineState() {
        return state;
    }

    public void setVendingMachineState(State<States,VendingMachine> vendingMachineState) {
        this.state = vendingMachineState;
    }
    public CoinPoolManager getCoinPoolManager() {
		return cpm;
	}

	public InventoryManager getInventoryManager() {
		return im;
	}

	public Integer getRequestedProduct() {
		return requestedProduct;
	}
	public void setRequestedProduct(Integer requestedProduct) {
		this.requestedProduct = requestedProduct;
	}
	
	public List<String> getCoinsInserted() {
		return coinsInserted;
	}

	public void setCoinsInserted(List<String> coinsInserted) {
		this.coinsInserted = coinsInserted;
	}
	
	public List<String> getCoinsToBeReturned() {
		return coinsToBeReturned;
	}

	public void setCoinsToBeReturned(List<String> coinsToBeReturned) {
		this.coinsToBeReturned = coinsToBeReturned;
	}
	
	public boolean isCoinsReturned() {
		return coinsReturned;
	}

	public void setCoinsReturned(boolean coinsReturned) {
		this.coinsReturned = coinsReturned;
	}

	public Integer behave() {
		return state.behave();
	}
	
	public void shutDown() {
		if(!getCoinsToBeReturned().isEmpty()) {
			System.out.println("Returning the money inserted : " + getCoinsToBeReturned());
		}
		System.out.println("Shutting down the vending machine!");
	}
	
}
