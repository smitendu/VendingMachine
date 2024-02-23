package vendingmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//import org.apache.commons.lang3.StringUtils;

public class InputSeeker {
	Scanner input = new Scanner(System.in); 
	
	public Integer selectProductMenu(VendingMachine vm) {
		Integer slotId = 0;
		while(true) {
			vm.getInventoryManager().printInventory();
			System.out.println("Please select the product by entering slotId. Enter EXIT to shut down the program.");
			String line = "";
			try {				
				line = input.nextLine();
			} catch (Exception e) {
				System.err.println("Please enter valid input");
				continue;
			}
			if(line == null || line.trim().isEmpty()) {
				continue;
			}
			if("EXIT".equalsIgnoreCase(line.trim())) {
				return -1;
			}
			try {
				slotId = Integer.parseInt(line);
			}
			catch (Exception e) {
				System.err.println("Please enter valid input");
				continue;
			}
			if (!vm.getInventoryManager().isSlotInTheValidList(slotId)) {
				System.err.println("Please select existing slot on machine.");
				continue;
			}
			if(!vm.getInventoryManager().isSlotTaken(slotId) || vm.getInventoryManager().isSlotOutOfStock(slotId)) {
				System.err.println("This slot is empty. Please select the slot that has items on it!");
				continue;
			}
			return slotId;
		}
	}

	public List<String>  collectCashMenu(VendingMachine vm) {
		List<String> userInput;
		List<String> coinsInserted;
		boolean exit = false, cancel = false;
		while(true) {
			userInput = Collections.emptyList();
			coinsInserted = Collections.emptyList();
			vm.getInventoryManager().printInventory();
			System.out.println("Please insert coins 1p, 2p, 5p, 10p, 50p, £1, £2 (comma separated).");
			System.out.println("Press CANCEL to go back to main menu or EXIT to shut down the vending machine");

			String line = "";
			try {				
				line = input.nextLine();
			} catch (Exception e) {
				System.err.println("Please enter valid input");
				continue;
			}
			if(line == null || line.trim().isEmpty()) {
				continue;
			}
			if(line.trim().contains("EXIT")) {
				exit = true;
				line.replaceAll("EXIT", "");
			}
			if(line.trim().contains("CANCEL")) {
				cancel = true;
				line.replaceAll("CANCEL", "");
			}
			try {
				userInput = Arrays.asList(line.split("[\\s,]+"));
			}
			catch (Exception e) {
				System.err.println("Please enter valid input");
				continue;
			}
			coinsInserted = new ArrayList<String>();
			for(String coin : userInput) {
				if(coin.isBlank()) {
					userInput.remove(coin);
					continue;
				}
				if(vm.getCoinPoolManager().isValidDenomination(coin.trim())) {
					coinsInserted.add(coin);
				}
				//else
					//break;
			}
			if(!(exit || cancel) && userInput.size() != coinsInserted.size()) {
				continue;
			}
			if(vm.getCoinsInserted() != null && !vm.getCoinsInserted().isEmpty() && !vm.isCoinsReturned()) {
				coinsInserted.addAll(vm.getCoinsInserted());
			}
			if(exit) {
				coinsInserted.add("EXIT");
				return coinsInserted;
			}
			if(cancel) {
				coinsInserted.add("CANCEL");
				return coinsInserted;
			}
			return coinsInserted;
		}
	}

}
