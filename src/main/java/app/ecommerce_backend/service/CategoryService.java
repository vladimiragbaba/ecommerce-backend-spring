package app.ecommerce_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce_backend.model.Category;
import app.ecommerce_backend.model.Product;
import app.ecommerce_backend.model.dto.CategoryDTO;
import app.ecommerce_backend.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	public List<Category> getCategories(){
		return categoryRepo.findAll().stream()
				.filter(c -> !c.isDeleted())
				.collect(Collectors.toList());
	}
	
	public Optional<Category> getCategoryById(Long id){
		return categoryRepo.findById(id);
	}
	
	public Category addCategory(CategoryDTO categoryDto) {
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setProducts(new ArrayList<Product>());
		return categoryRepo.save(category);
	}
	
	public Category removeCategory(Long id) {
		Category ca = categoryRepo.findById(id).get();
		if(ca != null) {
			ca.setDeleted(true);
			return categoryRepo.save(ca);		
		}
		return null;
	}
	
	public Category updateCategory(Long id, CategoryDTO categoryDto) {
		Category cat = categoryRepo.findById(id).get();
		if(cat != null) {
			cat.setName(categoryDto.getName());
			return categoryRepo.save(cat);
		}
		return null;
	}
	
	

}
