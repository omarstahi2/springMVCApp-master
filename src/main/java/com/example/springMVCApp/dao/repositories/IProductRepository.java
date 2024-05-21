package com.example.springMVCApp.dao.repositories;

import com.example.springMVCApp.dao.entities.Product;
import com.example.springMVCApp.dao.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContainingIgnoreCase(String name, PageRequest pageRequest);
}
