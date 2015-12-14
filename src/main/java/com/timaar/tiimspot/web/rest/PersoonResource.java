package com.timaar.tiimspot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.timaar.tiimspot.domain.Persoon;
import com.timaar.tiimspot.repository.PersoonRepository;
import com.timaar.tiimspot.repository.search.PersoonSearchRepository;
import com.timaar.tiimspot.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Persoon.
 */
@RestController
@RequestMapping("/api")
public class PersoonResource {

    private final Logger log = LoggerFactory.getLogger(PersoonResource.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private PersoonSearchRepository persoonSearchRepository;

    /**
     * POST  /persoons -> Create a new persoon.
     */
    @RequestMapping(value = "/persoons",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Persoon> createPersoon(@Valid @RequestBody Persoon persoon) throws URISyntaxException {
        log.debug("REST request to save Persoon : {}", persoon);
        if (persoon.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new persoon cannot already have an ID").body(null);
        }
        Persoon result = persoonRepository.save(persoon);
        persoonSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/persoons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("persoon", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /persoons -> Updates an existing persoon.
     */
    @RequestMapping(value = "/persoons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Persoon> updatePersoon(@Valid @RequestBody Persoon persoon) throws URISyntaxException {
        log.debug("REST request to update Persoon : {}", persoon);
        if (persoon.getId() == null) {
            return createPersoon(persoon);
        }
        Persoon result = persoonRepository.save(persoon);
        persoonSearchRepository.save(persoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("persoon", persoon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /persoons -> get all the persoons.
     */
    @RequestMapping(value = "/persoons",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Persoon> getAllPersoons() {
        log.debug("REST request to get all Persoons");
        return persoonRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /persoons/:id -> get the "id" persoon.
     */
    @RequestMapping(value = "/persoons/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Persoon> getPersoon(@PathVariable Long id) {
        log.debug("REST request to get Persoon : {}", id);
        return Optional.ofNullable(persoonRepository.findOneWithEagerRelationships(id))
            .map(persoon -> new ResponseEntity<>(
                persoon,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /persoons/:id -> delete the "id" persoon.
     */
    @RequestMapping(value = "/persoons/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePersoon(@PathVariable Long id) {
        log.debug("REST request to delete Persoon : {}", id);
        persoonRepository.delete(id);
        persoonSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("persoon", id.toString())).build();
    }

    /**
     * SEARCH  /_search/persoons/:query -> search for the persoon corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/persoons/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Persoon> searchPersoons(@PathVariable String query) {
        return StreamSupport
            .stream(persoonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
