package com.timaar.tiimspot.repository.search;

import com.timaar.tiimspot.domain.Contactinfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Contactinfo entity.
 */
public interface ContactinfoSearchRepository extends ElasticsearchRepository<Contactinfo, Long> {
}
