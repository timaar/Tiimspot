package com.timaar.tiimspot.repository.search;

import com.timaar.tiimspot.domain.Adres;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Adres entity.
 */
public interface AdresSearchRepository extends ElasticsearchRepository<Adres, Long> {
}
