package com.example.springMVCApp.dao.repositories;

import com.example.springMVCApp.dao.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Integer> {
    @Query("SELECT s FROM Supplier s WHERE s.deleted = false")
    List<Supplier> findAllActiveSuppliers();

    @Query("SELECT s FROM Supplier s WHERE s.deleted = false")
    Page<Supplier> findAllActive(PageRequest pageRequest);

    Page<Supplier> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, PageRequest pageRequest);
}
