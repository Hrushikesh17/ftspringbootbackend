package com.hkp.freetre.Controller;

//ProductController 

import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.ExceptionHandle.IdHandle;
import com.hkp.freetre.Dao.ProductDao;
import com.hkp.freetre.Dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	  @Autowired
	    private ProductDao productService;
	  
	  @Autowired
	  private UserDao userService;
	  
	  // Add a new product $
	    @PostMapping
	    public Product addProduct(@RequestBody Product product) {
	        return productService.addProduct(product);
	    }

	    // View all products $
	    @GetMapping
	    public List<Product> getAllProducts() {
	        return productService.getAllProducts();
	    }

	  
	    // Delete a product by ID $
	    @DeleteMapping("/{id}")
	    public void deleteProduct(@PathVariable Long id) {
	        productService.deleteProduct(id);
	    }

	    // Edit product name
	    @PutMapping("/{id}/name")
	    public Product updateProductName(@PathVariable Long id, @RequestParam String name) {
	        return productService.updateProductName(id, name);
	    }

	    // Edit product price
	    @PutMapping("/{id}/price")
	    public Product updateProductPrice(@PathVariable Long id, @RequestParam double price) {
	        return productService.updateProductPrice(id, price);
	    }

	    // Edit product description
	    @PutMapping("/{id}/description")
	    public Product updateProductDescription(@PathVariable Long id, @RequestParam String description) {
	        return productService.updateProductDescription(id, description);
	    }

	    // Edit product category
	    @PutMapping("/{id}/category")
	    public Product updateProductCategory(@PathVariable Long id, @RequestParam String category) {
	        return productService.updateProductCategory(id, category);
	    }
	}