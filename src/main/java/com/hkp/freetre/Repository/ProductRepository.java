package com.hkp.freetre.Repository;

//ProductRepository 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hkp.freetre.Dto.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	 // Search by name, description, price, or seller's shop name
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
    	       "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
    	       "OR LOWER(CAST(p.price AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
    	       "OR LOWER(p.seller.shopName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    	List<Product> searchProducts(String keyword); 
    List<Product> findBySellerId(Long sellerId);
    
    @Query("SELECT p FROM Product p WHERE p.seller.user.id = :userId " +
            "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR CAST(p.price AS string) LIKE CONCAT('%', :searchString, '%'))")
     List<Product> searchProductsByUserIdAndString(Long userId, String searchString);
    
    boolean existsByIdAndSellerId(Long productId, Long sellerId);
    
    boolean existsByIdAndSellerUserId(Long productId, Long userId);

}

