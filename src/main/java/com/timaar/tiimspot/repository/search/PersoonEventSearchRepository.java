package com.timaar.tiimspot.repository.search;

import com.timaar.tiimspot.domain.PersoonEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PersoonEvent entity.
 */
public interface PersoonEventSearchRepository extends ElasticsearchRepository<PersoonEvent, Long> {
}
