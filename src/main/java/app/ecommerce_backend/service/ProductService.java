package app.ecommerce_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce_backend.model.Product;
import app.ecommerce_backend.model.dto.ProductDTO;
import app.ecommerce_backend.repository.CategoryRepository;
import app.ecommerce_backend.repository.ProductRepository;



@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	
	public List<Product> getProducts(){
		return productRepo.findAll().stream()
				.filter(p -> !p.isDeleted())
				.collect(Collectors.toList());
	}
	
	public Product getProductById(Long id){
		Optional<Product> opt = productRepo.findById(id);
		if(opt.isPresent()) {
			return productRepo.findById(id).get();
		}
		return null;
	}
	
	public Product addProduct(ProductDTO productDto) {
		Product newProduct = new Product();
		newProduct.setDescription(productDto.getDescription());
		newProduct.setImage(productDto.getImage());
		newProduct.setName(productDto.getName());
		newProduct.setPrice(productDto.getPrice());
		newProduct.setCategory(categoryRepo.findById(productDto.getCategoryId()).get());
		return productRepo.save(newProduct);
	}
	
	public Product removeProduct(Long id) {
		Product pr = productRepo.findById(id).get();
		if(pr != null) {
			pr.setDeleted(true);
			return productRepo.save(pr);
		}
		return null;
	}
	
	public Product updateProduct(Long id,ProductDTO productDto) {
		Product pr = productRepo.findById(id).get();
		if(pr != null) {
			pr.setName(productDto.getName());
			pr.setPrice(productDto.getPrice());
			pr.setDescription(productDto.getDescription());
			pr.setImage(productDto.getImage());
			pr.setCategory(categoryRepo.findById(productDto.getCategoryId()).get());
			return productRepo.save(pr);
		}
		return null;
	}

	public List<Product> getProductsByCategory(Long catId) {	
		return getProducts().stream()
				.filter(p -> p.getCategory().getId() == catId)
				.collect(Collectors.toList());
	} 

}
