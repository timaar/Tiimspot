package com.timaar.tiimspot.repository.search;

import com.timaar.tiimspot.domain.Ouder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Ouder entity.
 */
public interface OuderSearchRepository extends ElasticsearchRepository<Ouder, Long> {
}
