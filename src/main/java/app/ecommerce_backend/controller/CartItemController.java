package app.ecommerce_backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce_backend.model.CartItem;
import app.ecommerce_backend.model.dto.CartItemDTO;
import app.ecommerce_backend.service.CartItemService;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/cartItems")
public class CartItemController {
	@Autowired
	CartItemService cartItemService;
	
	@RequestMapping()
	public ResponseEntity<Iterable<CartItemDTO>> getCartItems(){
		return new ResponseEntity<Iterable<CartItemDTO>>(HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id) {
		
        Optional<CartItem> cartItem= cartItemService.getCartItemById(id);
        if(cartItem.isPresent()) {
            return new ResponseEntity<CartItemDTO> (HttpStatus.OK);
        }
        return new ResponseEntity<CartItemDTO>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<CartItemDTO> addCartItem(@RequestBody CartItem cartItem) {
		cartItemService.addCartItem(cartItem);
        return new ResponseEntity<CartItemDTO>(HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<CartItemDTO> removeCartItem(@PathVariable Long id) {
        try {
        	cartItemService.removeCartItem(id);
        }catch (Exception e) {
            return new ResponseEntity<CartItemDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<CartItemDTO>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
		cartItemService.updateCartItem(id, cartItem);
        return new ResponseEntity<CartItemDTO>(HttpStatus.CREATED);
    }

}
