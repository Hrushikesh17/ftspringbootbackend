package com.hkp.freetre.Controller;

import com.hkp.freetre.Dao.ProductDao;
import com.hkp.freetre.Dao.SellerDao;
import com.hkp.freetre.Dao.UserDao;
import com.hkp.freetre.Dto.Product;

//SellerController 

import com.hkp.freetre.Dto.Seller;
import com.hkp.freetre.Dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
@CrossOrigin(origins = "http://localhost:3000")
public class SellerController {

	 @Autowired
	    private SellerDao sellerService;
	 @Autowired
	    private ProductDao productService;
	 @Autowired
	 private UserDao userService;
	 
	 
	 @PostMapping("/register")
	 public ResponseEntity<String> registerSeller(@RequestBody Seller seller) {
	     try {
	         // Validate seller and user
	         if (seller.getUser() == null) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User details are required");
	         }
	         if (seller.getUser().getEmail() == null || seller.getUser().getEmail().isEmpty()) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email is required");
	         }
	         if (seller.getUser().getPhoneNumber() == null || seller.getUser().getPhoneNumber().isEmpty()) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User phone number is required");
	         }
	         if (seller.getShopName() == null || seller.getShopName().isEmpty()) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Shop name is required");
	         }

	         Seller registeredSeller = sellerService.registerSeller(seller);
	         return ResponseEntity.status(HttpStatus.CREATED).body("Seller registered successfully");
	     } catch (IllegalArgumentException e) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	     } catch (RuntimeException e) {
	         return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	     }
	 }


	    
	 @PostMapping("/login/seller")
	 public ResponseEntity<?> loginSeller(@RequestParam String identifier, @RequestParam String password) {
	     Optional<Seller> seller = sellerService.loginSeller(identifier, password);
	     
	     if (seller.isPresent()) {
	         Seller loggedInSeller = seller.get();
	         Map<String, Object> response = new HashMap<>();
	         response.put("id", loggedInSeller.getUser().getId());
	         response.put("shopName", loggedInSeller.getShopName());
	         response.put("role", "SELLER"); // Assuming the role is "SELLER"
	         
	         return ResponseEntity.ok(response);
	     } else {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/phone number or password");
	     }
	 }


//	 // Update seller details (name, shop name, address)
//	    @PutMapping("/{id}")
//	    public ResponseEntity<Seller> updateSellerDetails(@PathVariable Long id, @RequestBody Seller updatedSeller) {
//	        try {
//	            Seller seller = sellerService.updateSellerDetails(id, updatedSeller);
//	            return ResponseEntity.ok(seller);
//	        } catch (IllegalArgumentException e) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//	        } catch (RuntimeException e) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	        }
//	    }

	 @GetMapping("/{userId}/products")
	 public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
	     // Find the seller by user ID
	     Optional<Seller> sellerOptional = sellerService.getSellerByUserId(userId);
	     if (sellerOptional.isPresent()) {
	         Seller seller = sellerOptional.get();
	         // Retrieve the products owned by the seller
	         List<Product> products = sellerService.getProductsBySellerId(seller.getId());

	         // Return the list of products (empty list if no products)
	         return ResponseEntity.ok(products);
	     } else {
	         // Return an empty list if the seller is not found
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	     }
	 }


	 // Search products by a single string, filtering by user ID of the seller
	 @GetMapping("/{userId}/products/search/{searchString}")
	    public ResponseEntity<?> searchProducts(
	            @PathVariable Long userId,
	            @PathVariable String searchString) {

	        List<Product> products = sellerService.searchProducts(userId, searchString);

	        if (products.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	        }

	        return ResponseEntity.ok(products);
	    }
	    // Endpoint to delete seller
	    @DeleteMapping("/{id}")
	    public void deleteSeller(@PathVariable Long id) {
	    	sellerService.deleteSeller(id);
	    }

	    //create product
	    @PostMapping("/{sellerId}/products")
	    public ResponseEntity<Product> createProduct(@PathVariable Long sellerId, @RequestBody Product product) {
	        // Check if the seller exists
	        Optional<Seller> sellerOptional = sellerService.getSellerByUserId(sellerId);
	        if (sellerOptional.isPresent()) {
	            Seller seller = sellerOptional.get();
	            product.setSeller(seller);
	            
	            // Save the product
	            Product createdProduct = sellerService.saveProduct(product);
	            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	        } else {
	            // Return a specific HTTP status code for not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }

	    // update product 
	    @PutMapping("/{userId}/products/{productId}")
	    public ResponseEntity<?> updateProduct(
	        @PathVariable Long userId,
	        @PathVariable Long productId,
	        @RequestBody Product productDetails) {

	        // Retrieve the product
	        Optional<Product> productOptional = sellerService.getProductById(productId);

	        if (productOptional.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	        }

	        Product product = productOptional.get();

	        // Check if the user owns the product
	        if (!product.getSeller().getUser().getId().equals(userId)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not own this product");
	        }

	        // Update product details
	        if (productDetails.getName() != null) {
	            product.setName(productDetails.getName());
	        }
	        if (productDetails.getDescription() != null) {
	            product.setDescription(productDetails.getDescription());
	        }
	        if (productDetails.getPrice() != 0) {
	            product.setPrice(productDetails.getPrice());
	        }
	        if (productDetails.getCategory() != null) {
	            product.setCategory(productDetails.getCategory());
	        }

	        // Save and return the updated product
	        Product updatedProduct = sellerService.updateProduct(product);
	        return ResponseEntity.ok(updatedProduct);
	    }
	    
//	     // Check if the product is owned by the user
	    @GetMapping("/{userId}/products/{productId}/ownership")
	    public ResponseEntity<Boolean> checkProductOwnership(
	            @PathVariable Long userId,
	            @PathVariable Long productId) {
	        boolean isOwnedByUser = sellerService.isProductOwnedByUser(userId, productId);
	        return ResponseEntity.ok(isOwnedByUser);
	    }
	    
	    // Delete a product
	    @DeleteMapping("/{userId}/products/{productId}")
	    public ResponseEntity<String> deleteProductByUserId(
	            @PathVariable Long userId, 
	            @PathVariable Long productId) {
	        
	        try {
	            // Find the seller by the userId
	            Optional<Seller> sellerOptional = sellerService.getSellerByUserId(userId);
	            
	            if (!sellerOptional.isPresent()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found.");
	            }
	            
	            Seller seller = sellerOptional.get();
	            
	            // Find the product by productId
	            Optional<Product> productOptional = sellerService.getProductById(productId);
	            
	            if (!productOptional.isPresent()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
	            }
	            
	            Product product = productOptional.get();
	            
	            // Check if the product belongs to the seller
	            if (product.getSeller().getId().equals(seller.getId())) {
	                // Delete the product
	                sellerService.deleteProduct(productId);
	                return ResponseEntity.ok("Product deleted successfully.");
	            } else {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Seller does not own this product.");
	            }
	            
	        } catch (Exception e) {
	            // Log the exception if needed
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the product.");
	        }
	    }

	    
	    
	}