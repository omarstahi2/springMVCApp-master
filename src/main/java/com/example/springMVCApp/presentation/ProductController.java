package com.example.springMVCApp.presentation;

import com.example.springMVCApp.dao.entities.Product;
import com.example.springMVCApp.dao.entities.Supplier;
import com.example.springMVCApp.service.IServices.IServiceProduct;
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
public class ProductController {
    private final IServiceProduct serviceProduct;
    private final IServiceSupplier serviceSupplier;

    @GetMapping("/listProducts")
    public String getListProduct(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(defaultValue = "0") int numPage,
                                 Model model) {
        Page<Product> products;
        if (name != null && !name.isEmpty()) {
            products = serviceProduct.searchProductsByName(name, numPage);
        } else {
            products = serviceProduct.listProducts(numPage);
        }
        model.addAttribute("currentPage", numPage);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("listProducts", products.getContent());
        model.addAttribute("name", name);
        return "product/ProductsList";
    }

    @GetMapping("/formAddProduct")
    public String getFormAdd(Model model) {
        List<Supplier> suppliers = serviceSupplier.listSuppliers();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("product", new Product());
        return "product/formulaireAddProduct";
    }

    @PostMapping("/insertProduct")
    public String postInsertProduct(@Valid Product p, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Supplier> suppliers = serviceSupplier.listSuppliers();
            model.addAttribute("suppliers", suppliers);
            model.addAttribute("product", p);
            return "product/formulaireAddProduct";
        }
        serviceProduct.addProduct(p);
        return "redirect:/listProducts";
    }

    @GetMapping("/updateProduct/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Product product = serviceProduct.searchProduct(id);
        List<Supplier> suppliers = serviceSupplier.listSuppliers();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("product", product);
        return "product/updateFormProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(Product product) {
        serviceProduct.updateProduct(product);
        return "redirect:/listProducts";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        serviceProduct.removeProduct(id);
        return "redirect:/listProducts";
    }
}
