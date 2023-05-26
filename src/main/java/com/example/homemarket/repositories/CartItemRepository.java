package com.example.homemarket.repositories;

import com.example.homemarket.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("select it " +
            "from CartItem it " +
            "where it.cart.id = :cartId and it.statusCheckout = false")
    List<CartItem> findByCartId(@Param("cartId") Integer cartId);
    @Query("select i " +
            "from CartItem i " +
            "where i.product.id = :productId and i.cart.id = :cartId and i.statusCheckout = false ")
    CartItem findByProductIdAndCartId(@Param("productId") Integer productId, @Param("cartId") Integer cartId);
}
