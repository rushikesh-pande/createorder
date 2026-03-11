package com.orderprocessing.createorder.db.search;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Database Optimisation Enhancement: Elasticsearch Search Repository
 * Provides full-text search over createorder entities.
 */
@Repository
public interface CreateorderSearchRepository
        extends ElasticsearchRepository<CreateorderSearchDocument, String> {

    List<CreateorderSearchDocument> findByNameContainingOrDescriptionContaining(
            String name, String description);

    List<CreateorderSearchDocument> findByStatus(String status);

    List<CreateorderSearchDocument> findByCategory(String category);

    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"name^2\",\"description\",\"category\"],\"fuzziness\":\"AUTO\",\"type\":\"best_fields\"}}")
    List<CreateorderSearchDocument> fuzzySearch(String query);
}
