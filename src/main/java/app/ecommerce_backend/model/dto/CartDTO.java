package app.ecommerce_backend.model.dto;

import java.util.List;

public class CartDTO {
	
	private List<CartItemDTO> cartItems;
	
	
	
	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}
	
	
	
	
	
}
