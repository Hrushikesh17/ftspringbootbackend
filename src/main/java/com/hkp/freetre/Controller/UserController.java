package com.hkp.freetre.Controller;

import com.hkp.freetre.Dao.ProductDao;
import com.hkp.freetre.Dao.UserDao;
import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.Dto.Seller;

//UserController 
import com.hkp.freetre.Dto.User;
import com.hkp.freetre.Dto.UserRole;
import com.hkp.freetre.ExceptionHandle.ProductNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserDao userService;

    @Autowired
    private ProductDao productService;
    
    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/login/user")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestParam String identifier, @RequestParam String password) {
        Optional<User> user = userService.loginUser(identifier, password);

        if (user.isPresent()) {
            System.out.println("User found: " + user.get().getName() + ", Role: " + user.get().getRole());
            
            // Create a response map
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.get().getId());
            response.put("name", user.get().getName());
            response.put("role", user.get().getRole());
            
         // Save user ID in session
//            session.setAttribute("userId", user.get().getId());
         
            
            return ResponseEntity.ok(response);
        } else {
            System.out.println("Login failed for identifier: " + identifier);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid email/phone number or password"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserDetails(@PathVariable Long id, @RequestBody User updatedUser) {
        System.out.println("Updating user with ID: " + id); // Logging ID
        try {
            User user = userService.updateUserDetails(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
    	userService.deleteUser(id);
    }
    
  
    // Search for products by name, description, price, or seller's shop name
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable String keyword) {
        try {
            List<Product> products = userService.searchProductsByAllData(keyword);
            return ResponseEntity.ok(products);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //search product
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}