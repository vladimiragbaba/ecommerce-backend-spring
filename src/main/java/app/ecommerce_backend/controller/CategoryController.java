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

import app.ecommerce_backend.model.Category;
import app.ecommerce_backend.model.dto.CategoryDTO;
import app.ecommerce_backend.service.CategoryService;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value="/getCategories", method=RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories(){
		return new ResponseEntity<List<Category>>(categoryService.getCategories(),HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getCategory/{id}", method=RequestMethod.GET)
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category= categoryService.getCategoryById(id);
        if(category.isPresent()) {
            return new ResponseEntity<Category>(category.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDTO categoryDto) {
        return new ResponseEntity<Category>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Category> removeCategory(@PathVariable Long id) {
        try {
        	return new ResponseEntity<Category>(categoryService.removeCategory(id), HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }

    }
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> updateCategory(@PathVariable Long id,
			@RequestBody CategoryDTO categoryDto){
		return new ResponseEntity<Category>
			(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
	}
	

}
