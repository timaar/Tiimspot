package com.timaar.tiimspot.repository.search;

import com.timaar.tiimspot.domain.Persoon;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Persoon entity.
 */
public interface PersoonSearchRepository extends ElasticsearchRepository<Persoon, Long> {
}
