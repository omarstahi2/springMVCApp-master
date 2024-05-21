package com.example.springMVCApp.service.IServices;

import com.example.springMVCApp.dao.entities.Order;
import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceOrder {
    void addOrder(Order o);
    void removeOrder(Integer id);
    void updateOrder(Order o);
    Order searchOrder(Integer id);
    List<Order> listOrders();
    Page<Order> listOrders(int numPage);
    void removeOrdersByProductId(Integer productId); // New method
    double getTotalSales();
    double getTotalSalesLastWeek();
    double getTotalSalesLastMonth();
    List<Order> getRecentOrders();
    List<Product> getMostOrderedProducts();
    List<Client> getBestClients();}
