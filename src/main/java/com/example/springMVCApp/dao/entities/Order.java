package com.example.springMVCApp.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders") // order is a reserved name in phpmyadmin, so we changed it

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Client client;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @ManyToOne
    private Product product;
    private double total_amount;
    @Min(value = 1)
    private int quantity; // Quantity ordered
}
