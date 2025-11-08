package com.hkp.freetre.Dao;

//ProductDao

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.ExceptionHandle.IdHandle;
import com.hkp.freetre.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDao {
	
	@Autowired
    private ProductRepository productRepository;
	
	 // Add a new product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    //search
    public List<Product> searchProducts(String name, String description, Double price, String category, String sellerName) {
        Specification<Product> spec = Specification.where(ProductSpecifications.withName(name))
                .and(ProductSpecifications.withDescription(description))
                .and(ProductSpecifications.withPrice(price))
                .and(ProductSpecifications.withCategory(category))
                .and(ProductSpecifications.withSellerName(sellerName));

        // Perform search
        return productRepository.findAll(spec);
    }
	  // View all products
	  public List<Product> getAllProducts() {
	        return productRepository.findAll()
	            .stream()
	            .map(products -> new Product(products.getName(),products.getDescription(),products.getPrice(),products.getCategory()))
	            .collect(Collectors.toList());
	    }

	  // View product by ID
	    public Product getProductById(Long id) {
	        Optional<Product> product = productRepository.findById(id);
	        if (product.isPresent()) {
	            Product p = product.get();
	            return new Product(p.getName(), p.getDescription(), p.getPrice(), p.getCategory());
	        } else {
	            throw new IdHandle("Product not found with ID: " + id);
	        }
	    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Edit product name
    public Product updateProductName(Long id, String newName) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(newName);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    // Edit product price
    public Product updateProductPrice(Long id, double newPrice) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setPrice(newPrice);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    // Edit product description
    public Product updateProductDescription(Long id, String newDescription) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setDescription(newDescription);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    // Edit product category
    public Product updateProductCategory(Long id, String newCategory) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setCategory(newCategory);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }
}