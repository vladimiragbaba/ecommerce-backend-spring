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

import app.ecommerce_backend.model.Product;
import app.ecommerce_backend.model.dto.ProductDTO;
import app.ecommerce_backend.service.ProductService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/getProducts")
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<List<Product>>(productService.getProducts(), HttpStatus.OK);

	}

	@RequestMapping(value="/getProduct/{id}", method=RequestMethod.GET)
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		
        Product product = productService.getProductById(id);
        if(product != null) {
            return new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
        }
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value = "/getProductsByCategory/{catId}")
	public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("catId") Long catId) {
		return new ResponseEntity<List<Product>>
				(productService.getProductsByCategory(catId), HttpStatus.OK);
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDto) {
    	return new ResponseEntity<Product>(productService.addProduct(productDto), HttpStatus.CREATED);
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Product> removeProduct(@PathVariable Long id) {
        try {
        	productService.removeProduct(id);
        }catch (Exception e) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDto) {
        return new ResponseEntity<Product>(productService.updateProduct(id, productDto), HttpStatus.CREATED);
    }

}
