package app.ecommerce_backend.controller;

import java.util.List;
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

import app.ecommerce_backend.model.Order;
import app.ecommerce_backend.model.dto.OrderDTO;
import app.ecommerce_backend.service.OrderService;



@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping(value = "/getAll")
	public ResponseEntity<List<Order>> getOrders(){
		return new ResponseEntity<List<Order>>(orderService.getOrders(), HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		
        Optional<Order> order= orderService.getOrderById(id);
        if(order.isPresent()) {
            return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value="/addOrder", method=RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDto) {
        return new ResponseEntity<Order>(orderService.addOrder(orderDto), HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Order> removeOrder(@PathVariable Long id) {
        try {
        	orderService.removeOrder(id);
        }catch (Exception e) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }
	
//	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
//    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody Order order) {
//		orderService.updateOrder(id, order);
//        return new ResponseEntity<OrderDTO>(OrderDTOAdapter.convertToDTO(order), HttpStatus.CREATED);
//    }

}
