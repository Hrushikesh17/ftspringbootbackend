package com.hkp.freetre.Repository;

//UserRepository 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hkp.freetre.Dto.Product;
import com.hkp.freetre.Dto.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email); // Find user by email (useful for login)
    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);
    
   
        Optional<User> findByEmailAndPassword(String email, String password);
        Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password);
   

    
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    
   

}

