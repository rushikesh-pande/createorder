package com.orderprocessing.createorder.db.health;

import org.springframework.boot.actuate.health.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * Database Optimisation Enhancement: Elasticsearch Health Indicator
 */
@Component("elasticsearchHealth")
public class ElasticsearchHealthIndicator implements HealthIndicator {

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchHealthIndicator(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Health health() {
        try {
            boolean up = elasticsearchTemplate.indexOps(
                    org.springframework.data.elasticsearch.core.IndexCoordinates.of("createorder-index"))
                    .exists();
            return Health.up()
                    .withDetail("service",       "createorder")
                    .withDetail("elasticsearch", "reachable")
                    .withDetail("index",         "createorder-index (exists=" + up + ")")
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail("service",       "createorder")
                    .withDetail("elasticsearch", "unreachable")
                    .withDetail("error",         ex.getMessage())
                    .build();
        }
    }
}
