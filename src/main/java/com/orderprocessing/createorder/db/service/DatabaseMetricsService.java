package com.orderprocessing.createorder.db.service;

import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Database Optimisation Enhancement: Database Metrics Service
 *
 * Tracks cache and query performance metrics for createorder.
 * Exposed to Prometheus via /actuator/prometheus.
 *
 * Metrics:
 *  - createorder_cache_hits_total       — Redis cache hits
 *  - createorder_cache_misses_total     — Redis cache misses (DB queries)
 *  - createorder_db_queries_total       — Total DB queries by type
 *  - createorder_db_slow_queries_total  — Queries above 500ms
 *  - createorder_connection_pool_active — HikariCP active connections
 */
@Service
public class DatabaseMetricsService {

    private final MeterRegistry meterRegistry;
    private final AtomicLong activeConnections = new AtomicLong(0);

    public DatabaseMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        Gauge.builder("createorder.connection.pool.active", activeConnections, AtomicLong::get)
             .description("Active HikariCP connections for createorder")
             .tag("service", "createorder")
             .register(meterRegistry);
    }

    public void recordCacheHit(String cacheName) {
        Counter.builder("createorder.cache.hits.total")
               .tag("service", "createorder").tag("cache", cacheName)
               .description("Redis cache hits for createorder")
               .register(meterRegistry).increment();
    }

    public void recordCacheMiss(String cacheName) {
        Counter.builder("createorder.cache.misses.total")
               .tag("service", "createorder").tag("cache", cacheName)
               .description("Redis cache misses for createorder (DB fallback)")
               .register(meterRegistry).increment();
    }

    public void recordDbQuery(String queryType) {
        Counter.builder("createorder.db.queries.total")
               .tag("service", "createorder").tag("type", queryType)
               .description("DB queries for createorder")
               .register(meterRegistry).increment();
    }

    public void recordSlowQuery(String queryType, long ms) {
        Counter.builder("createorder.db.slow.queries.total")
               .tag("service", "createorder").tag("type", queryType)
               .description("DB queries exceeding 500ms for createorder")
               .register(meterRegistry).increment();
        meterRegistry.summary("createorder.db.query.duration",
                "service", "createorder", "type", queryType).record(ms);
    }

    public void setActiveConnections(long count) {
        activeConnections.set(count);
    }
}
