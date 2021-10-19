package app.ecommerce_backend.model.dto;

import app.ecommerce_backend.model.Product;

public class CartItemDTO {
	
	private double quantity;
	private Product product;
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	

}
