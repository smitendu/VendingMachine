package vendingmachine.states;

import vendingmachine.interact.UserInputHandler;

/* Ready -(selectProduct)-> ProductSelected -(collectCash)-> CoinsInserted -(dispenseChange)-> ChangeDispensed -(dispenseItem)-> ItemDispensed -> Ready
 *
 * ANY_STATE -(cancelTransaction)-> TransactionCanceled [-(dispenseChange)-> ChangeDispensed] -> Ready
 */
public interface State<States, VendingMachine> {
	public Integer behave();

}
