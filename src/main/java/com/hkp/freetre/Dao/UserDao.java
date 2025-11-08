//package com.hkp.freetre.Dao;
//
////UserDao
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import com.hkp.freetre.Dto.Product;
//import com.hkp.freetre.Dto.User;
//import com.hkp.freetre.Dto.UserRole;
//import com.hkp.freetre.Repository.ProductRepository;
//import com.hkp.freetre.Repository.UserRepository;
//
//import jakarta.persistence.*;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class UserDao {
//
//	 @Autowired
//	    private ProductRepository productRepository;
//     
//	 @Autowired
//	    private UserRepository userRepository;
//	 // Register user
//	    public User registerUser(User user) {
//	        // Check if a user with the same email already exists
//	        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//	        if (existingUser.isPresent()) {
//	            throw new RuntimeException("User with this email already exists.");
//	        }
//	        
//	        // Save and return the user if email is unique
//	        return userRepository.save(user);
//	    }
//	    public Optional<User> loginUser(String identifier, String password) {
//	        Optional<User> user = userRepository.findByEmailOrPhoneNumber(identifier, identifier);
//	        if (user.isPresent() && user.get().getPassword().equals(password) && user.get().getRole() == UserRole.BUYER) {
//	            return user;
//	        }
//	        return Optional.empty();
//	    }
//	    
//	    public User updateUserDetails(Long userId, User updatedUser) {
//	        // Find the user by ID
//	        Optional<User> existingUserOptional = userRepository.findById(userId);
//	        
//	        if (existingUserOptional.isPresent()) {
//	            User existingUser = existingUserOptional.get();
//
//	            // Validate and update the name field
//	            if (updatedUser.getName() != null) {
//	                String name = updatedUser.getName().trim();
//	                if (!name.isEmpty() && name.matches("[a-zA-Z ]+")) {  // Allow only letters and spaces
//	                    existingUser.setName(name);
//	                } else {
//	                    throw new IllegalArgumentException("Name cannot be empty, null, or contain special characters.");
//	                }
//	            }
//
//	            // Validate and update the address field
//	            if (updatedUser.getAddress() != null) {
//	                String address = updatedUser.getAddress().trim();
//	                if (!address.isEmpty() && address.matches("[a-zA-Z0-9, ]+")) {  // Allow letters, numbers, commas, and spaces
//	                    existingUser.setAddress(address);
//	                } else {
//	                    throw new IllegalArgumentException("Address cannot be empty, null, or contain special characters.");
//	                }
//	            }
//
//	            // Save the updated user
//	            return userRepository.save(existingUser);
//	        } else {
//	            throw new RuntimeException("User not found with id: " + userId);
//	        }
//	    }
//
//
//
//	    // Delete user by ID
//	    public void deleteUser(Long userId) {
//	        Optional<User> user = userRepository.findById(userId);
//	        if (user.isPresent()) {
//	            userRepository.deleteById(userId);
//	        } else {
//	            throw new RuntimeException("User not found");
//	        }
//	    }
//      //Search product
//	    public UserDao(ProductRepository productRepository) {
//	        this.productRepository = productRepository;
//	    }
//
//	    public List<Product> searchProducts(Map<String, Object> searchParams) {
//	        return productRepository.findAll(buildSpecification(searchParams));
//	    }
//
//	    private Specification<Product> buildSpecification(Map<String, Object> searchParams) {
//	        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
//	            Predicate predicate = criteriaBuilder.conjunction();
//
//	            searchParams.forEach((key, value) -> {
//	                if (value != null && !value.toString().trim().isEmpty()) {
//	                    // Attempt to map values to fields dynamically
//	                    if (key.equalsIgnoreCase("name") || key.equalsIgnoreCase("description") || key.equalsIgnoreCase("category")) {
//	                        // Handle fields directly
//	                        predicate = criteriaBuilder.and(predicate,
//	                                criteriaBuilder.like(criteriaBuilder.lower(root.get(key)), "%" + value.toString().toLowerCase() + "%"));
//	                    } else if (key.equalsIgnoreCase("price")) {
//	                        try {
//	                            predicate = criteriaBuilder.and(predicate,
//	                                    criteriaBuilder.equal(root.get("price"), Double.parseDouble(value.toString())));
//	                        } catch (NumberFormatException e) {
//	                            // Handle invalid number format
//	                        }
//	                    } else if (key.equalsIgnoreCase("sellerName")) {
//	                        Predicate = criteriaBuilder.and(predicate,
//	                                criteriaBuilder.like(criteriaBuilder.lower(root.get("seller").get("shopName")), "%" + value.toString().toLowerCase() + "%"));
//	                    }
//	                    // Add additional mappings if needed
//	                }
//	            });
//
//	            return predicate;
//	        };
//	    }
//
//	    // Method to retrieve all products
//	    public List<Product> getAllProducts() {
//	        return productRepository.findAll();
//	    }
//
//	   
//	}


package com.hkp.freetre.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.Dto.User;
import com.hkp.freetre.Dto.UserRole;
import com.hkp.freetre.ExceptionHandle.ProductNotFoundException;
import com.hkp.freetre.Repository.ProductRepository;
import com.hkp.freetre.Repository.UserRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDao {

    @Autowired
    private ProductRepository productRepository;
     
    @Autowired
    private UserRepository userRepository;

    // Register user
    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }
        return userRepository.save(user);
    }

    //login user
    public Optional<User> loginUser(String identifier, String password) {
        Optional<User> user = userRepository.findByEmailOrPhoneNumber(identifier, identifier);
        if (user.isPresent() && user.get().getPassword().equals(password) && user.get().getRole() == UserRole.BUYER) {
            return user;
        }
        return Optional.empty();
    }
    
    //update details
    public User updateUserDetails(Long userId, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (updatedUser.getName() != null) {
                String name = updatedUser.getName().trim();
                if (!name.isEmpty() && name.matches("[a-zA-Z ]+")) {
                    existingUser.setName(name);
                } else {
                    throw new IllegalArgumentException("Name cannot be empty, null, or contain special characters.");
                }
            }
            if (updatedUser.getAddress() != null) {
                String address = updatedUser.getAddress().trim();
                if (!address.isEmpty() && address.matches("[a-zA-Z0-9, ]+")) {
                    existingUser.setAddress(address);
                } else {
                    throw new IllegalArgumentException("Address cannot be empty, null, or contain special characters.");
                }
            }
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    // Delete user by ID
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Search products
    public List<Product> searchProductsByAllData(String keyword) {
        List<Product> products = productRepository.searchProducts(keyword);
        
        if (products == null || products.isEmpty()) {
            throw new ProductNotFoundException("No products found for the keyword: " + keyword);
        }
        
        return products;
    }


    // Method to retrieve all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
