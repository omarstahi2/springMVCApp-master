package com.example.springMVCApp.presentation;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.entities.Order;
import com.example.springMVCApp.dao.entities.Product;
import com.example.springMVCApp.service.IServices.IServiceClient;
import com.example.springMVCApp.service.IServices.IServiceOrder;
import com.example.springMVCApp.service.IServices.IServiceProduct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {
    private final IServiceOrder serviceOrder;
    private final IServiceClient clientService;
    private final IServiceProduct productService;

    @GetMapping("/listOrders")
    public String getListOrders(Model model, @RequestParam(defaultValue = "0") int numPage) {
        Page<Order> orders = serviceOrder.listOrders(numPage);
        int currentPage = numPage;
        int totalPages = orders.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listOrders", orders.getContent());
        return "order/OrdersList";
    }

    @GetMapping("/formAddOrder")
    public String getFormAdd(Model model, @RequestParam(defaultValue = "0") int numPage) {
        List<Client> clients = clientService.listClients(); // Assuming 10 clients per page
        List<Product> products = productService.listProducts();
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clients);
        model.addAttribute("products", products);
        return "order/formulaireAddOrder";
    }

    @PostMapping("/insertOrder")
    public String postInsertOrder(@Valid Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Client> clients = clientService.listClients();
            List<Product> products = productService.listProducts();
            model.addAttribute("clients", clients);
            model.addAttribute("products", products);
            return "order/formulaireAddOrder";
        }

        // Fetch the associated product for the order
        Product product = productService.searchProduct(order.getProduct().getId());

        // Check if the ordered quantity is available
        if (product.getQuantity() < order.getQuantity()) {
            model.addAttribute("errorMessage", "Insufficient quantity available for the product");
            List<Client> clients = clientService.listClients();
            List<Product> products = productService.listProducts();
            model.addAttribute("clients", clients);
            model.addAttribute("products", products);
            return "order/formulaireAddOrder";
        }

        // Calculate total amount for the order
        order.setTotal_amount(product.getPrice() * order.getQuantity());

        // Update the quantity of the product
        product.setQuantity(product.getQuantity() - order.getQuantity());
        productService.updateProduct(product);

        // Add the order
        serviceOrder.addOrder(order);

        return "redirect:/listOrders";
    }

    @GetMapping("/updateOrder/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Order order = serviceOrder.searchOrder(id);
        List<Client> clients = clientService.listClients();
        List<Product> products = productService.listProducts();
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        model.addAttribute("products", products);
        model.addAttribute("totalPages", clients);
        return "order/updateFormOrder";
    }

    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute("order") Order order) {
        // Fetch the existing order
        Order existingOrder = serviceOrder.searchOrder(order.getId());

        // Fetch the associated product for the existing order
        Product existingProduct = productService.searchProduct(existingOrder.getProduct().getId());

        // Revert the quantity of the existing product
        existingProduct.setQuantity(existingProduct.getQuantity() + existingOrder.getQuantity());
        productService.updateProduct(existingProduct);

        // Fetch the associated product for the updated order
        Product updatedProduct = productService.searchProduct(order.getProduct().getId());

        // Check if the updated quantity is available
        if (updatedProduct.getQuantity() < order.getQuantity()) {
            return "redirect:/updateOrder/" + order.getId() + "?error=Insufficient+quantity+available+for+the+product";
        }

        // Update the quantity of the updated product
        updatedProduct.setQuantity(updatedProduct.getQuantity() - order.getQuantity());
        productService.updateProduct(updatedProduct);

        // Calculate the new total amount for the order
        order.setTotal_amount(updatedProduct.getPrice() * order.getQuantity());

        // Update the order
        serviceOrder.updateOrder(order);

        return "redirect:/listOrders";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        // Fetch the existing order
        Order order = serviceOrder.searchOrder(id);

        // Fetch the associated product
        Product product = productService.searchProduct(order.getProduct().getId());

        // Revert the quantity of the product
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productService.updateProduct(product);

        // Remove the order
        serviceOrder.removeOrder(id);

        return "redirect:/listOrders";
    }
}
