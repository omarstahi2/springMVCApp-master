package com.example.springMVCApp.service.IServices;

import com.example.springMVCApp.dao.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceProduct {
    void addProduct(Product p);
    void removeProduct(Integer id);
    void updateProduct(Product p);
    Product searchProduct(Integer id);
    List<Product> listProducts();
    Page<Product> listProducts(int numPage);
    Page<Product> searchProductsByName(String name, int numPage);
    List<Product> getLowStockProducts(int threshold);
}