package com.hkp.freetre.Dao;

//SellerDao

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.Dto.Seller;
import com.hkp.freetre.Dto.User;
import com.hkp.freetre.Dto.UserRole;
import com.hkp.freetre.ExceptionHandle.ProductNotFoundException;
import com.hkp.freetre.Repository.ProductRepository;
import com.hkp.freetre.Repository.SellerRepository;
import com.hkp.freetre.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SellerDao {
	
	    @Autowired
	    private UserRepository userRepository;


	    @Autowired
	    private ProductRepository productRepository;
	    
	    @Autowired
	    private SellerRepository sellerRepository;
	    
	    public Seller registerSeller(Seller seller) {
	        // Ensure the user is not null
	        if (seller.getUser() == null) {
	            throw new IllegalArgumentException("User details are required");
	        }

	        // Ensure user fields are not null or empty
	        if (seller.getUser().getEmail() == null || seller.getUser().getEmail().isEmpty()) {
	            throw new IllegalArgumentException("User email is required");
	        }
	        if (seller.getUser().getPhoneNumber() == null || seller.getUser().getPhoneNumber().isEmpty()) {
	            throw new IllegalArgumentException("User phone number is required");
	        }
	        if (seller.getShopName() == null || seller.getShopName().isEmpty()) {
	            throw new IllegalArgumentException("Shop name is required");
	        }

	        // Check if the email or phone number already exists
	        if (userRepository.existsByEmail(seller.getUser().getEmail())) {
	            throw new RuntimeException("Email already registered");
	        }
	        if (userRepository.existsByPhoneNumber(seller.getUser().getPhoneNumber())) {
	            throw new RuntimeException("Phone number already registered");
	        }

	        // Check if the shop name already exists
	        if (sellerRepository.existsByShopName(seller.getShopName())) {
	            throw new RuntimeException("Shop name already registered");
	        }

	        // Set the role as SELLER
	        seller.getUser().setRole(UserRole.SELLER);

	        // Save the associated user first (automatically saves Seller with CascadeType.ALL)
	        return sellerRepository.save(seller);
	    }



	    public Optional<Seller> loginSeller(String identifier, String password) {
	        Optional<Seller> seller = sellerRepository.findByUserEmailOrUserPhoneNumber(identifier, identifier);
	        if (seller.isPresent() && seller.get().getUser().getPassword().equals(password) && seller.get().getUser().getRole() == UserRole.SELLER) {
	            return seller;
	        }
	        return Optional.empty();
	    }


//	    public Seller updateSellerDetails(Long sellerId, Seller updatedSeller) {
//	        Optional<Seller> existingSellerOptional = sellerRepository.findById(sellerId);
//	        if (existingSellerOptional.isPresent()) {
//	            Seller existingSeller = existingSellerOptional.get();
//
//	            // Update the shop name if provided and valid
//	            if (updatedSeller.getShopName() != null && !updatedSeller.getShopName().trim().isEmpty()) {
//	                // Check if the new shop name already exists for another seller
//	                Seller shopWithSameName = sellerRepository.findByShopName(updatedSeller.getShopName());
//	                if (shopWithSameName != null && !shopWithSameName.getId().equals(sellerId)) {
//	                    throw new IllegalArgumentException("Shop name already exists");
//	                }
//	                existingSeller.setShopName(updatedSeller.getShopName());
//	            } else if (updatedSeller.getShopName() != null) {
//	                throw new IllegalArgumentException("Shop name cannot be empty or null");
//	            }
//
//	            // Update the user details (name, address) if provided and valid
//	            User existingUser = existingSeller.getUser();
//	            User updatedUser = updatedSeller.getUser();
//
//	            if (updatedUser != null) {
//	                if (updatedUser.getName() != null && !updatedUser.getName().trim().isEmpty()) {
//	                    existingUser.setName(updatedUser.getName());
//	                } else if (updatedUser.getName() != null) {
//	                    throw new IllegalArgumentException("Name cannot be empty or null");
//	                }
//
//	                if (updatedUser.getAddress() != null && !updatedUser.getAddress().trim().isEmpty()) {
//	                    existingUser.setAddress(updatedUser.getAddress());
//	                } else if (updatedUser.getAddress() != null) {
//	                    throw new IllegalArgumentException("Address cannot be empty or null");
//	                }
//
//	                // Save the updated user details
//	                userRepository.save(existingUser);
//	            }
//
//	            // Save the updated seller details
//	            return sellerRepository.save(existingSeller);
//	        } else {
//	            throw new RuntimeException("Seller not found with id: " + sellerId);
//	        }
//	    }


	    
	   
	    public Optional<Seller> getSellerByUserId(Long userId) {
	        return sellerRepository.findByUserId(userId);
	    }
	    public List<Product> getProductsBySellerId(Long sellerId) {
	        // Fetch products by seller ID
	        return productRepository.findBySellerId(sellerId);
	    }

	    // Method to delete seller by ID
	    public void deleteSeller(Long sellerId) {
	        Optional<Seller> seller = sellerRepository.findById(sellerId);
	        if (seller.isPresent()) {
	            sellerRepository.deleteById(sellerId);
	        } else {
	            throw new RuntimeException("Seller not found");
	        }
	    }

	    public Optional<Seller> getSellerById(Long id) {
	        return sellerRepository.findById(id);
	    }
	    
	    public Product saveProduct(Product product) {
	        return productRepository.save(product);
	    }
	    
//	    public List<Product> getAllProducts() {
//	        return productRepository.findAll();
//	    }
	    //serach product by seller
	    public List<Product> searchProducts(Long userId, String searchString) {
	        return productRepository.searchProductsByUserIdAndString(userId, searchString);
	    }

	    public Optional<Product> getProductById(Long id) {
	        return productRepository.findById(id);
	    }

	  
	    // Check if the product belongs to the seller
	    public boolean checkProductBelongsToSeller(Long sellerId, Long productId) {
	        return productRepository.existsByIdAndSellerId(productId, sellerId);
	    }

	    // Delete the product
	    public void deleteProduct(Long productId) {
	    	
	        if (productRepository.existsById(productId)) {
	            productRepository.deleteById(productId);
	        } else {
	            throw new ProductNotFoundException("Product not found.");
	        }
	    }
	 
	    public Product updateProduct(Product product) {
	        return productRepository.save(product);
	    }
	    
	    // Method to check if the product belongs to the seller using userId
	    public boolean isProductOwnedByUser(Long userId, Long productId) {
	        // Find the seller by user ID
	        Optional<Seller> sellerOptional = sellerRepository.findByUserId(userId);
	        if (sellerOptional.isPresent()) {
	            Seller seller = sellerOptional.get();
	            // Check if the product exists and belongs to the seller
	            return productRepository.existsByIdAndSellerUserId(productId, userId);
	        }
	        return false;
	    }
	}