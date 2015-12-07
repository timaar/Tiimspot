package com.timaar.tiimspot.repository.search;

import com.timaar.tiimspot.domain.EventScore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EventScore entity.
 */
public interface EventScoreSearchRepository extends ElasticsearchRepository<EventScore, Long> {
}
