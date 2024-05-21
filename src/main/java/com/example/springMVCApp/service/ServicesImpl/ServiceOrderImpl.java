package com.example.springMVCApp.service.ServicesImpl;

import com.example.springMVCApp.dao.entities.Client;
import com.example.springMVCApp.dao.entities.Order;
import com.example.springMVCApp.dao.entities.Product;
import com.example.springMVCApp.dao.repositories.IOrderRepository;
import com.example.springMVCApp.service.IServices.IServiceOrder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ServiceOrderImpl implements IServiceOrder {
    private final IOrderRepository or;

    @Override
    public void addOrder(Order o) {
        or.save(o);
    }

    @Override
    public void removeOrder(Integer id) {
        Optional<Order> p = or.findById(id);
        if(p.isEmpty()) throw new RuntimeException("Order not found");
        else or.deleteById(id);
    }

    @Override
    public void updateOrder(Order o) {
        or.save(o);

    }

    @Override
    public Order searchOrder(Integer id) {
        Optional<Order> c = or.findById(id);
        if(c.isEmpty()) throw new RuntimeException("Product not found");
        else return c.get();
    }

    @Override
    public List<Order> listOrders() {
        return or.findAll();
    }

    @Override
    public Page<Order> listOrders(int numPage) {
        return or.findAll(PageRequest.of(numPage, 3));
    }

    @Override
    public void removeOrdersByProductId(Integer productId) {
        or.deleteByProductId(productId);
    }

    public double getTotalSales() {
        return or.findAll()
                .stream()
                .mapToDouble(Order::getTotal_amount)
                .sum();
    }

    public double getTotalSalesLastWeek() {
        LocalDate oneWeekAgo = LocalDate.now().minus(1, ChronoUnit.WEEKS);
        return or.findAll()
                .stream()
                .filter(order -> order.getDate().isAfter(oneWeekAgo))
                .mapToDouble(Order::getTotal_amount)
                .sum();
    }

    public double getTotalSalesLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minus(1, ChronoUnit.MONTHS);
        return or.findAll()
                .stream()
                .filter(order -> order.getDate().isAfter(oneMonthAgo))
                .mapToDouble(Order::getTotal_amount)
                .sum();
    }

    @Override
    public List<Order> getRecentOrders() {
        return or.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getMostOrderedProducts() {
        return or.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getProduct, Collectors.summingInt(Order::getQuantity)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> getBestClients() {
        return or.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getClient, Collectors.summingDouble(Order::getTotal_amount)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Client, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
