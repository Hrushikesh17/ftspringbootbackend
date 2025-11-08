package com.hkp.freetre.Controller;
//Admin controller
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hkp.freetre.Dao.AdminDao;
import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.Dto.Seller;
import com.hkp.freetre.Dto.User;
import com.hkp.freetre.Dto.UserRole;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDao adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellers() {
        return ResponseEntity.ok(adminService.getAllSellers());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(adminService.getAllProducts());
    }

    @PostMapping("/change-role/{userId}")
    public ResponseEntity<String> changeUserRole(@PathVariable Long userId, @RequestParam UserRole role) {
        adminService.changeUserRole(userId, role);
        return ResponseEntity.ok("User role updated successfully");
    }

    @PostMapping("/add-category")
    public ResponseEntity<String> addProductCategory(@RequestBody Product product) {
        adminService.addProductCategory(product);
        return ResponseEntity.ok("Product category added successfully");
    }

    // Additional endpoints for other admin functionalities
}
