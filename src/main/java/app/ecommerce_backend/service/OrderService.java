package app.ecommerce_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce_backend.model.Cart;
import app.ecommerce_backend.model.CartItem;
import app.ecommerce_backend.model.Order;
import app.ecommerce_backend.model.dto.CartItemDTO;
import app.ecommerce_backend.model.dto.OrderDTO;
import app.ecommerce_backend.repository.CartItemRepository;
import app.ecommerce_backend.repository.CartRepository;
import app.ecommerce_backend.repository.OrderRepository;
import app.ecommerce_backend.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	
	public List<Order> getOrders(){
		return orderRepo.findAll();
	}
	
	public Optional<Order> getOrderById(Long id){
		return orderRepo.findById(id);
	}
	
	public Order addOrder(OrderDTO orderDto) {
		Order order = new Order();
		order.setUser(userRepo.getById(orderDto.getUserId()));
		order.setAddress(orderDto.getAddress());
		order.setPrice(orderDto.getPrice());
		
		List<CartItem> items = addCartItems(orderDto.getItems());
		Cart cart = addCart(items);
		
		order.setCart(cart);
		return orderRepo.save(order);
	}
	
	public Cart addCart(List<CartItem> items) {
		Cart cart = new Cart();
		cart.setCartItems(items);
		return cartRepo.save(cart);
	}
	
	public List<CartItem> addCartItems(List<CartItemDTO> dtos) {
		List<CartItem> itemsList = new ArrayList<CartItem>();
		for (CartItemDTO cartItemDTO : dtos) {
			CartItem item = new CartItem();
			item.setProduct(cartItemDTO.getProduct());
			item.setQuantity(cartItemDTO.getQuantity());
			itemsList.add(cartItemRepo.save(item));
		}
		return itemsList;
	}
	
	public void removeOrder(Long id) {
		Optional<Order> or = orderRepo.findById(id);
		if(or.isPresent()) {
			or.get().setDeleted(true);
			orderRepo.save(or.get());
		}
	}
	
	public void updateOrder(Long id,Order order) {
		Optional<Order> or = orderRepo.findById(id);
		if(or.isPresent()) {
			or.get().setPrice(order.getPrice());
			or.get().setUser(order.getUser());
			orderRepo.save(or.get());
		}
	}

}
