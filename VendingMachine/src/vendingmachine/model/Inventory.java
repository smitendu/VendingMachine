package vendingmachine.model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
	private Map<Integer, Map<Product, Integer>> inventoryList;
	
	public Inventory() {
		this.inventoryList = new HashMap<Integer, Map<Product, Integer>>();
	}
	
	public Map<Integer, Map<Product, Integer>> getList() {
		return inventoryList;
	}

}