package com.example.springMVCApp.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 10, max = 500)
    private String description;
    @NotNull
    @Min(value = 0)
    private Double price;
    @NotNull
    @Min(value = 1)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}