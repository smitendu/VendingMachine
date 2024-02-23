package vendingmachine.manager;

import vendingmachine.model.Inventory;
import vendingmachine.model.Product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InventoryManager implements Manager{
	private Set<Integer> validSlots;

	private Inventory inventory;
	
	public InventoryManager() {
		this.inventory = new Inventory();
		validSlots = new HashSet<Integer>(
				Arrays.asList(101, 102, 103, 104, 201, 202, 203, 204, 301, 302, 303, 304, 401, 402, 403, 404, 501, 502, 503, 504));
		this.initialize();
	}
	public Inventory getInventory() {
		return this.inventory;
	}

	public boolean isSlotInTheValidList(Integer slotId) { 
		return validSlots.contains(slotId);
	}
	
	public void initialize() {
		allotSlotToProduct(101, new Product("Pepsi", 1.25), 20);
		allotSlotToProduct(102, new Product("CocaCola", 1.30), 25);
		allotSlotToProduct(201, new Product("Pepsi", 1.25), 25);
		allotSlotToProduct(202, new Product("Walkers Ready Salted", 0.65), 40);
		//allotSlotToProduct(202, new Product("Ritz", 0.65), 10);
		allotSlotToProduct(301, new Product("Ritz", 0.65), 0);
		allotSlotToProduct(301, new Product("Ritz", 0.65), 10);
		allotSlotToProduct(302, new Product("Maryland", 0.80), 0);
	}
	
	public void printInventory () {
		this.inventory.getList()
			.forEach((k,v) -> {
				Integer slotId = k;
				Map.Entry<Product,Integer> productStock = v.entrySet().stream().findFirst().orElse(null);
				Product product = productStock.getKey();
				Integer stock = productStock.getValue();
				System.out.println("Slot Id: " + slotId + "\tProduct Name : " + product.getProductName() 
					+ " \t\t      In Stock :" + stock + " \t\tAmount :" + product.getAmount());
			});
		System.out.println("********************************************************************************************************");
	}
	
	
	public Product getProduct(Integer slotId) {
		return slotId != null && this.inventory.getList().containsKey(slotId) ? 
				this.inventory.getList().get(slotId).entrySet().stream().findFirst().get().getKey() : null;
	}
	
	public Integer getStock(Integer slotId) {
		return slotId != null && this.inventory.getList().containsKey(slotId) ? 
				this.inventory.getList().get(slotId).entrySet().stream().findFirst().get().getValue() : null;
	}
	
	public void allotSlotToProduct(Integer slotId, Product product, Integer stock) {
		if(slotId != null && !isSlotInTheValidList(slotId)) {
			throw new RuntimeException ("This Slot ID is not valid!");
		}
		if(this.inventory.getList().containsKey(slotId) && !isSlotOutOfStock(slotId)) {
			throw new RuntimeException ("This Slot is already taken!");
		}
		Map<Product, Integer> productStock = new HashMap <Product, Integer> ();
		productStock.put(product, stock);
		this.inventory.getList().put(slotId, productStock);
	}
	
	public void removeSlot(Integer slotId) {
		this.inventory.getList().entrySet().removeIf(pr -> pr.getKey().equals(slotId));
	}
	
	public boolean isSlotTaken(Integer slotId) {
		return slotId != null && this.inventory.getList().containsKey(slotId);
	}

	public boolean hasProduct(Integer slotId, String productId) {
		return isSlotTaken(slotId) 
				&& this.inventory.getList().get(slotId).entrySet().stream()
					.filter(p -> p.getKey().getProductName().equals(productId)).count() > 0;
	}
	
	public boolean isSlotOutOfStock(Integer slotId) {		
		Integer stock = getStock(slotId);
		return stock == null || stock == 0;
	}
		
	public void reduceProductCount(Integer slotId) throws Exception {
		if(!isSlotTaken(slotId)) {
			throw new Exception ("Slot " + slotId +" not found");
		}
		if(isSlotOutOfStock(slotId)) {
			throw new Exception ("Slot " + slotId + " is already empty, cannot reduce its stock further");
		}
		this.inventory.getList().get(slotId).entrySet().stream().findFirst().ifPresent(o->o.setValue(o.getValue()-1));
	}

	public static void main (String args[]) throws Exception {
		InventoryManager inMan = new InventoryManager();
		inMan.printInventory();
		
		inMan.removeSlot(102);
		inMan.printInventory();
		
		inMan.allotSlotToProduct(102, new Product("CocaCola", 1.30), 25);
		inMan.printInventory();		

		System.out.println("Is slot 301 available?: " + !inMan.isSlotTaken(301));
		System.out.println("Is slot 302 available?: " + !inMan.isSlotTaken(302));
		System.out.println("Is slot 303 available?: " + !inMan.isSlotTaken(303));
		
		System.out.println("Is slot 301 in stock?: " + !inMan.isSlotOutOfStock(301));
		System.out.println("Is slot 302 in stock?: " + !inMan.isSlotOutOfStock(302));
		
		inMan.reduceProductCount(101);
		inMan.printInventory();
		
		//inMan.reduceProductCount(302);
	}

}
