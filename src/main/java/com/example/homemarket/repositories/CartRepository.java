package com.example.homemarket.repositories;

import com.example.homemarket.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("select c " +
            "from Cart c " +
            "where c.user.id = :id")
    Cart findByUserId(@Param("id")Integer id);
}
