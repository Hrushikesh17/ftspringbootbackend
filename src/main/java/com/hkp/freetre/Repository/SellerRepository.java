package com.hkp.freetre.Repository;

import java.util.Optional;

//SellerRepository 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hkp.freetre.Dto.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	
	Optional<Seller> findByUserEmail(String email);
	
	Optional<Seller> findByUserEmailOrUserPhoneNumber(String email, String phoneNumber);
	
	// Method to find a Seller by shop name
    Seller findByShopName(String shopName);
    
    boolean existsByShopName(String shopName);
    
    Optional<Seller> findByUserId(Long userId); 
    
//    Seller findByUserIdForProductUpdate(Long userId);
    
 
}

