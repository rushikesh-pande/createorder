package com.createorder.kafka;
import com.createorder.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
@Component @RequiredArgsConstructor @Slf4j
public class OrderEventProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public void publishOrderCreated(OrderResponse order) {
        Map<String,Object> e = new HashMap<>();
        e.put("eventType","ORDER_CREATED");
        e.put("orderId",order.getOrderId());
        e.put("customerId",order.getCustomerId());
        e.put("totalAmount",order.getTotalAmount());
        e.put("inventoryReservationId",order.getInventoryReservationId()); // NEW field
        e.put("timestamp",System.currentTimeMillis());
        kafkaTemplate.send("order.created", order.getCustomerId(), e);
        log.info("Published ORDER_CREATED orderId={}", order.getOrderId());
    }
}
