package com.hkp.freetre.Dao;

//AdminDao


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkp.freetre.Dto.Admin;
import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.Dto.Seller;
import com.hkp.freetre.Dto.User;
import com.hkp.freetre.Dto.UserRole;
import com.hkp.freetre.Repository.AdminRepository;
import com.hkp.freetre.Repository.ProductRepository;
import com.hkp.freetre.Repository.SellerRepository;
import com.hkp.freetre.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminDao {

	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private SellerRepository sellerRepository;

	    @Autowired
	    private ProductRepository productRepository;

	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    public List<Seller> getAllSellers() {
	        return sellerRepository.findAll();
	    }

	    public List<Product> getAllProducts() {
	        return productRepository.findAll();
	    }

	    public void changeUserRole(Long userId, UserRole role) {
	        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	        user.setRole(role);
	        userRepository.save(user);
	    }

	    public void addProductCategory(Product product) {
	        productRepository.save(product);
	    }

	    // Additional methods to manage users, sellers, and products
	}