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
    private final IOrderRepository orderRepository;

    @Override
    public void addOrder(Order order) {
        order.setDate(LocalDate.now());
        orderRepository.save(order);
    }

    @Override
    public void removeOrder(Integer id) {
        Optional<Order> p = orderRepository.findById(id);
        if (p.isEmpty()) throw new RuntimeException("Order not found");
        else orderRepository.deleteById(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);

    }

    @Override
    public Order searchOrder(Integer id) {
        Optional<Order> c = orderRepository.findById(id);
        if (c.isEmpty()) throw new RuntimeException("Product not found");
        else return c.get();
    }

    @Override
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> listOrders(int numPage) {
        return orderRepository.findAll(PageRequest.of(numPage, 3));
    }

    @Override
    public void removeOrdersByProductId(Integer productId) {
        orderRepository.deleteByProductId(productId);
    }

    public double getTotalSales() {
        return orderRepository.findAll()
                .stream()
                .mapToDouble(Order::getTotal_amount)
                .sum();
    }

    public double getTotalSalesLastWeek() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getDate().isAfter(oneWeekAgo))
                .mapToDouble(Order::getTotal_amount)
                .sum();
    }

    public double getTotalSalesLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getDate().isAfter(oneMonthAgo))
                .mapToDouble(Order::getTotal_amount)
                .sum();
    }

    @Override
    public List<Order> getRecentOrders() {
        return orderRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getMostOrderedProducts() {
        return orderRepository.findAll()
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
        return orderRepository.findAll()
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
