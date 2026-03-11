package com.orderprocessing.createorder.db.search;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Database Optimisation Enhancement: Search Service
 *
 * Combines Redis caching with Elasticsearch full-text search.
 * Search results are cached for 60s to reduce ES query load.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateorderSearchService {

    private final CreateorderSearchRepository searchRepository;

    /**
     * Full-text search with Redis caching.
     * Cache key includes the query string — evict on data changes.
     */
    @Cacheable(value = "createorderCache", key = "'search:' + #query")
    @Timed(value = "createorder.search.duration", description = "ES search duration")
    public List<CreateorderSearchDocument> search(String query) {
        log.info("[SEARCH] Full-text search for '{}'", query);
        return searchRepository.fuzzySearch(query);
    }

    @Cacheable(value = "createorderCache", key = "'status:' + #status")
    public List<CreateorderSearchDocument> findByStatus(String status) {
        log.info("[SEARCH] Status filter: '{}'", status);
        return searchRepository.findByStatus(status);
    }

    /** Index a new document (call from Kafka consumer or service layer) */
    public CreateorderSearchDocument index(CreateorderSearchDocument doc) {
        log.info("[ES INDEX] Indexing {} id={}", "createorder", doc.getId());
        return searchRepository.save(doc);
    }

    /** Remove from index (call on delete/cancel) */
    public void removeFromIndex(String id) {
        log.info("[ES DELETE] Removing {} id={}", "createorder", id);
        searchRepository.deleteById(id);
    }
}
