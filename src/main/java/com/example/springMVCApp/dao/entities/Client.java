package com.example.springMVCApp.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Size(min = 2, max = 15)
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 15)
    private String lastName;
    @NotEmpty
    @Size(min = 2, max = 25)
    private String address;
    private boolean deleted = false; // Soft delete flag
}

