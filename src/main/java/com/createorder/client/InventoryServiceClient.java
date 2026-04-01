package com.createorder.client;
import com.createorder.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
// NEW — Enhancement #2: reactive HTTP client to InventoryService
@Component @RequiredArgsConstructor @Slf4j
public class InventoryServiceClient {
    private final WebClient.Builder webClientBuilder;
    @Value("${inventory.service.url:http://localhost:8085}") private String inventoryUrl;

    public InventoryCheckResponse checkAndReserve(InventoryCheckRequest req) {
        try {
            return webClientBuilder.build()
                .post()
                .uri(inventoryUrl + "/api/inventory/check-and-reserve")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(InventoryCheckResponse.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorReturn(fallbackResponse())
                .block();
        } catch (Exception ex) {
            log.warn("InventoryService unavailable — using fallback: {}", ex.getMessage());
            return fallbackResponse();
        }
    }
    private InventoryCheckResponse fallbackResponse() {
        return InventoryCheckResponse.builder()
            .allAvailable(true).reservationId("FALLBACK-" + System.currentTimeMillis()).build();
    }
}
