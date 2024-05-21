package com.example.springMVCApp.presentation;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.entities.Order;
import com.example.springMVCApp.dao.entities.Product;
import com.example.springMVCApp.service.ServicesImpl.ServiceClientImpl;
import com.example.springMVCApp.service.ServicesImpl.ServiceOrderImpl;
import com.example.springMVCApp.service.ServicesImpl.ServiceProductImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final ServiceProductImpl productService;
    private final ServiceOrderImpl orderService;
    private final ServiceClientImpl clientService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        List<Product> lowStockProducts = productService.getLowStockProducts(10);
        List<Product> mostOrderedProducts = orderService.getMostOrderedProducts();
        List<Order> recentOrders = orderService.getRecentOrders();
        List<Client> bestClients = orderService.getBestClients();
        double totalSales = orderService.getTotalSales();
        double totalSalesLastWeek = orderService.getTotalSalesLastWeek();
        double totalSalesLastMonth = orderService.getTotalSalesLastMonth();
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalSalesLastWeek", totalSalesLastWeek);
        model.addAttribute("totalSalesLastMonth", totalSalesLastMonth);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("bestClients", bestClients);
        return "Dashboard";
    }
}
