package vendingmachine.exceptions;

public class ExactChangeUnavailable extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExactChangeUnavailable () {
		super();
	}
	
	public ExactChangeUnavailable (String errorMessage) {
		super(errorMessage);
	}	
}
