package com.example.springMVCApp.dao.repositories;

import com.example.springMVCApp.dao.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    void deleteByProductId(Integer productId);
}
