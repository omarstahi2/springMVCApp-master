package com.example.springMVCApp.dao.repositories;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Integer> {
    Page<Supplier> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, PageRequest pageRequest);
}
