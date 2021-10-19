package app.ecommerce_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.ecommerce_backend.model.Cart;
import app.ecommerce_backend.model.CartItem;
import app.ecommerce_backend.model.dto.CartDTO;
import app.ecommerce_backend.model.dto.CartItemDTO;
import app.ecommerce_backend.repository.CartItemRepository;
import app.ecommerce_backend.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	CartItemRepository cartItemRepo;
	
//	public Iterable<Cart> getCart(){
//		return cartRepo.findAll();
//	}
	
	public Optional<Cart> getCartById(Long id){
		return cartRepo.findById(id);
	}
	
	public Cart addCart(CartDTO cartDto) {
		Cart cart = new Cart();
		List<CartItem> items = saveCartItems(cartDto);
		cart.setCartItems(items);
		return cartRepo.save(cart);
	}
	
	private List<CartItem> saveCartItems(CartDTO cartDto) {
		List<CartItem> itemList = new ArrayList<CartItem>();
		for (CartItemDTO itemDto : cartDto.getCartItems()) {
			CartItem item = new CartItem();
			item.setProduct(itemDto.getProduct());
			item.setQuantity(itemDto.getQuantity());
			itemList.add(cartItemRepo.save(item));
		}
		return itemList;
	}

	public void removeCart(Long id) {
		Optional<Cart> ca = cartRepo.findById(id);
		if(ca.isPresent()) {
			ca.get().setDeleted(true);
			cartRepo.save(ca.get());
		}
	}
	

}
