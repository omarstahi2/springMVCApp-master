package com.example.springMVCApp.service.ServicesImpl;

import com.example.springMVCApp.dao.entities.Supplier;
import com.example.springMVCApp.dao.repositories.ISupplierRepository;
import com.example.springMVCApp.service.IServices.IServiceSupplier;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ServiceSupplierImpl implements IServiceSupplier {
    private final ISupplierRepository supplierRepository;

    @Override
    public void addSupplier(Supplier s) {
        supplierRepository.save(s);
    }

    @Override
    public void removeSupplier(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplier.setDeleted(true);
        supplierRepository.save(supplier);
    }

    @Override
    public void updateSupplier(Supplier s) {
        supplierRepository.save(s);

    }

    @Override
    public Supplier searchSupplier(Integer id) {
        Optional<Supplier> c = supplierRepository.findById(id);
        if (c.isEmpty()) throw new RuntimeException("Supplier not found");
        else return c.get();
    }

    @Override
    public List<Supplier> listSuppliers() {
        return supplierRepository.findAllActiveSuppliers();
    }

    @Override
    public Page<Supplier> listSuppliers(int numPage) {
        return supplierRepository.findAllActive(PageRequest.of(numPage, 3));
    }

    @Override
    public Page<Supplier> searchSuppliersByName(String name, int numPage) {
        return supplierRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, PageRequest.of(numPage, 3));
    }
}
