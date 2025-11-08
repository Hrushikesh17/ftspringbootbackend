package com.hkp.freetre.Dao;

import com.hkp.freetre.Dto.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> withName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<Product> withDescription(String description) {
        return (root, query, criteriaBuilder) -> {
            if (description == null || description.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<Product> withPrice(Double price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("price"), price);
        };
    }

    public static Specification<Product> withCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    public static Specification<Product> withSellerName(String sellerName) {
        return (root, query, criteriaBuilder) -> {
            if (sellerName == null || sellerName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("seller").get("shopName"), "%" + sellerName + "%");
        };
    }
}
