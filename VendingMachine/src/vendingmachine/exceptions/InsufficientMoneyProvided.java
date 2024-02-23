package vendingmachine.exceptions;

public class InsufficientMoneyProvided extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public InsufficientMoneyProvided () {
		super();
	}
	
	public InsufficientMoneyProvided (String errorMessage) {
		super(errorMessage);
	}	
}
