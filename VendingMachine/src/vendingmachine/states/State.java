package vendingmachine.states;

/* Ready -(selectProduct)-> ProductSelected -(collectCash)-> CoinsInserted -(dispenseChange)-> ChangeDispensed -(dispenseItem)-> ItemDispensed -> Ready
 *
 * ANY_STATE -(cancelTransaction)-> TransactionCanceled [-(dispenseChange)-> ChangeDispensed] -> Ready
 */
public interface State<States, VendingMachine> {
	public Integer behave();

}
