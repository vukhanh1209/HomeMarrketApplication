package com.example.homemarket.repositories;

import com.example.homemarket.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderItem,Integer> {
}
