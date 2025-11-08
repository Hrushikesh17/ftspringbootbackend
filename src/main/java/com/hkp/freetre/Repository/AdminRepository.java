package com.hkp.freetre.Repository;

//AdminRepository

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hkp.freetre.Dto.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // You can add custom query methods if needed
}
