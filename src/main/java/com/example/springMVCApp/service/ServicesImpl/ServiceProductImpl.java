package com.example.springMVCApp.service.ServicesImpl;

import com.example.springMVCApp.dao.entities.Product;
import com.example.springMVCApp.dao.repositories.IProductRepository;
import com.example.springMVCApp.service.IServices.IServiceProduct;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ServiceProductImpl implements IServiceProduct {
    private final IProductRepository productRepository;
    private final ServiceOrderImpl serviceOrder;

    @Override
    public void addProduct(Product p) {
        productRepository.save(p);
    }

    @Override
    public void removeProduct(Integer id) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isEmpty()) throw new RuntimeException("Product not found");
        else {
            serviceOrder.removeOrdersByProductId(id);
            productRepository.deleteById(id);
        }
    }

    @Override
    public void updateProduct(Product p) {
        productRepository.save(p);

    }

    @Override
    public Product searchProduct(Integer id) {
        Optional<Product> c = productRepository.findById(id);
        if (c.isEmpty()) throw new RuntimeException("Product not found");
        else return c.get();
    }

    @Override
    public Page<Product> searchProductsByName(String name, int numPage) {
        return productRepository.findByNameContainingIgnoreCase(name, PageRequest.of(numPage, 3));
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> listProducts(int numPage) {
        return productRepository.findAll(PageRequest.of(numPage, 3));
    }

    @Override
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getQuantity() < threshold)
                .collect(Collectors.toList());
    }
}
