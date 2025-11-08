package com.hkp.freetre.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Product Entity

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String category; // Farming tools, seeds, plants, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonIgnore
    private Seller seller;

	public Product(String name, String description, double price, String category) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

   
    // Additional fields like stock quantity, product type, etc. can be added here
}
