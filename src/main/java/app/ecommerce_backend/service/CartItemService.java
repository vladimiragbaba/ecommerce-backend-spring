package app.ecommerce_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce_backend.model.CartItem;
import app.ecommerce_backend.repository.CartItemRepository;

@Service
public class CartItemService {
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	
	public Iterable<CartItem> getCartItems(){
		return cartItemRepo.findAll();
	}
	
	public Optional<CartItem> getCartItemById(Long id){
		return cartItemRepo.findById(id);
	}
	
	public void addCartItem(CartItem cartItem) {
		cartItemRepo.save(cartItem);
	}
	
	public void removeCartItem(Long id) {
		Optional<CartItem> ci = cartItemRepo.findById(id);
		if(ci.isPresent()) {
			ci.get().setDeleted(true);
			cartItemRepo.save(ci.get());
		}
	}
	
	public void updateCartItem(Long id,CartItem cartItem) {
		Optional<CartItem> ci = cartItemRepo.findById(id);
		if(ci.isPresent()) {
			ci.get().setQuantity(cartItem.getQuantity());
			ci.get().setProduct(cartItem.getProduct());
			cartItemRepo.save(ci.get());
		}
	}

}
