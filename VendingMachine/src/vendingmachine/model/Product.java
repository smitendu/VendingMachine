package vendingmachine.model;

public class Product {
	private String productName;
	private double productPrice;
	
	public Product (String productName, double productPrice) {
		this.productName = productName;
		this.productPrice = productPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getAmount() {
		return productPrice;
	}
	public void setAmount(double productPrice) {
		this.productPrice = productPrice;
	}
}
