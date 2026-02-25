package com.orderprocessing.createorder.service;

import com.orderprocessing.createorder.model.Order;
import com.orderprocessing.createorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order.created";

    public Order create(Order order) {
        log.info("Creating Order: {}", order);
        Order saved = orderRepository.save(order);
        kafkaTemplate.send(TOPIC, "ORDER_CREATED", saved.toString());
        log.info("Order created with id: {}", saved.getId());
        return saved;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order update(Long id, Order updated) {
        return orderRepository.findById(id).map(existing -> {
            updated.setId(id);
            Order saved = orderRepository.save(updated);
            kafkaTemplate.send(TOPIC, "ORDER_UPDATED", saved.toString());
            log.info("Order updated: {}", saved.getId());
            return saved;
        }).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
        kafkaTemplate.send(TOPIC, "ORDER_DELETED", id.toString());
        log.info("Order deleted: {}", id);
    }
}
