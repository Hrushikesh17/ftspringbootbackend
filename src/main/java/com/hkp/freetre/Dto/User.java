//package com.hkp.freetre.Dto;
//
////User Entity (with Admin, Seller, and Buyer Roles)
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "users", uniqueConstraints = {
//	    @UniqueConstraint(columnNames = "email"),
//	    @UniqueConstraint(columnNames = "phoneNumber")
//	})
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    @Column(unique = true, nullable = false)
//    private String email;
//    @Column(unique = true, nullable = false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private UserRole role; // ADMIN, SELLER, or BUYER
//
//    private String address;
//    @Column(unique = true, nullable = false)
//    private String phoneNumber;
//
//    // Additional attributes like profile picture, status (active/inactive), etc.
//    
//    
//    
//    
//    
//}

package com.hkp.freetre.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "phoneNumber")
})
//@JsonIgnoreProperties({"seller"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role; // ADMIN, SELLER, or BUYER

    private String address;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonBackReference  // Prevents recursion
    private Seller seller;
}

//
