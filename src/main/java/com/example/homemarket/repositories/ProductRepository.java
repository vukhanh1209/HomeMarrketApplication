package com.example.homemarket.repositories;

import com.example.homemarket.dtos.ProductDTO;
import com.example.homemarket.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Integer> {
    @Query("select p " +
            "from Product p " +
            "where p.productName LIKE CONCAT('%',:query,'%')")
    List<ProductDTO> searchProducts(@Param("query") String query);
    // List product by category
    @Query("select p " +
            "from Product p " +
            "where p.category.id in :ids")
    List<ProductDTO> listProductByCategories(@Param("ids") List<Integer> categoryIds);

    List<Product> findAll(Specification<Product> spec, Sort sort);
}
