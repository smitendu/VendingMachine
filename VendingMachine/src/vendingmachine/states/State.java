package vendingmachine.states;

import vendingmachine.InputSeeker;

/* Ready -(selectProduct)-> ProductSelected -(collectCash)-> CoinsInserted -(dispenseChange)-> ChangeDispensed -(dispenseItem)-> ItemDispensed -> Ready
 *
 * ANY_STATE -(cancelTransaction)-> TransactionCanceled [-(dispenseChange)-> ChangeDispensed] -> Ready
 */
public interface State<States, VendingMachine> {
	InputSeeker seeker = new InputSeeker();
	public Integer behave();

}
