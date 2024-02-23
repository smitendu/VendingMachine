package vendingmachine.interact;

import vendingmachine.VendingMachine;
import vendingmachine.states.State;
import vendingmachine.states.StateFactory.States;

public interface UserInputSeeker extends State <States, VendingMachine>{
	UserInputHandler inputHandler = new UserInputHandler();
}
