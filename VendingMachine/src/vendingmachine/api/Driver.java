package vendingmachine.api;

import vendingmachine.VendingMachine;

public class Driver {
	private static String INITIAL_MESSAGE = "Welcome! Please select the product. You can type EXIT any time to exit this program.";
		
	public static void main (String[] args) throws Exception {
		System.out.println(INITIAL_MESSAGE);
		Integer response = 1;
		VendingMachine vm = new VendingMachine();
		while(response != null && response != -1) {
			response = vm.behave();
		}
	}
	
}
