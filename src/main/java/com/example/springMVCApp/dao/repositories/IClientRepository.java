package com.example.springMVCApp.dao.repositories;

import com.example.springMVCApp.dao.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {
    @Query("SELECT c FROM Client c WHERE c.deleted = false")
    List<Client> findAllActiveClients();
    @Query("SELECT c FROM Client c WHERE c.deleted = false")
    Page<Client> findAllActive(PageRequest pageRequest);
    Page<Client> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, PageRequest pageRequest);
}
