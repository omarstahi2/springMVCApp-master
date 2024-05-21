package com.example.springMVCApp.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
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
    @Pattern(regexp = "^\\d{10,15}$")
    private String phone;
    @NotEmpty
    @Email
    private String email;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Product> products;
}
