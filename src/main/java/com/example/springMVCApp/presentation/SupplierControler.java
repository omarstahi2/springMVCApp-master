package com.example.springMVCApp.presentation;

import com.example.springMVCApp.dao.entities.Supplier;
import com.example.springMVCApp.service.IServices.IServiceSupplier;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class SupplierControler {
    private final IServiceSupplier serviceSupplier;

    @GetMapping("/listSuppliers")
    public String getListSuppliers(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(defaultValue = "0") int numPage,
                                   Model model) {
        Page<Supplier> suppliersPage;
        if (name != null && !name.isEmpty()) {
            suppliersPage = serviceSupplier.searchSuppliersByName(name, numPage);
        } else {
            suppliersPage = serviceSupplier.listSuppliers(numPage);
        }

        model.addAttribute("currentPage", numPage);
        model.addAttribute("totalPages", suppliersPage.getTotalPages());
        model.addAttribute("listSuppliers", suppliersPage.getContent());
        model.addAttribute("name", name);
        return "supplier/SuppliersList";
    }

    @GetMapping("/formAddSupplier")
    public String getFormAdd(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/formulaireAddSupplier";
    }

    @PostMapping("/insertSupplier")
    public String postInsertSupplier(@Valid Supplier s, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "supplier/formulaireAddSupplier";
        }
        serviceSupplier.addSupplier(s);
        return "redirect:/listSuppliers";
    }


    @GetMapping("/updateSupplier/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Supplier supplier = serviceSupplier.searchSupplier(id);
        model.addAttribute("supplier", supplier);
        return "supplier/updateFormSupplier";
    }

    @PostMapping("/updateSupplier")
    public String updateSupplier(Supplier supplier) {
        serviceSupplier.updateSupplier(supplier);
        return "redirect:/listSuppliers";
    }

    @GetMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable("id") Integer id) {
        serviceSupplier.removeSupplier(id);
        return "redirect:/listSuppliers";
    }
}
