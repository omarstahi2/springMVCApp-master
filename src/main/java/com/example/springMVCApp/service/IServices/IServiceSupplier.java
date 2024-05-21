package com.example.springMVCApp.service.IServices;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.entities.Supplier;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceSupplier {
    void addSupplier(Supplier s);
    void removeSupplier(Integer id);
    void updateSupplier(Supplier s);
    Supplier searchSupplier(Integer id);
    List<Supplier> listSuppliers();
    Page<Supplier> listSuppliers(int numPage);
    Page<Supplier> searchSuppliersByName(String name, int numPage);
}
