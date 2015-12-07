package com.timaar.tiimspot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.timaar.tiimspot.domain.PersoonEvent;
import com.timaar.tiimspot.repository.PersoonEventRepository;
import com.timaar.tiimspot.repository.search.PersoonEventSearchRepository;
import com.timaar.tiimspot.web.rest.util.HeaderUtil;
import com.timaar.tiimspot.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing PersoonEvent.
 */
@RestController
@RequestMapping("/api")
public class PersoonEventResource {

    private final Logger log = LoggerFactory.getLogger(PersoonEventResource.class);

    @Inject
    private PersoonEventRepository persoonEventRepository;

    @Inject
    private PersoonEventSearchRepository persoonEventSearchRepository;

    /**
     * POST  /persoonEvents -> Create a new persoonEvent.
     */
    @RequestMapping(value = "/persoonEvents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PersoonEvent> createPersoonEvent(@Valid @RequestBody PersoonEvent persoonEvent) throws URISyntaxException {
        log.debug("REST request to save PersoonEvent : {}", persoonEvent);
        if (persoonEvent.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new persoonEvent cannot already have an ID").body(null);
        }
        PersoonEvent result = persoonEventRepository.save(persoonEvent);
        persoonEventSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/persoonEvents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("persoonEvent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /persoonEvents -> Updates an existing persoonEvent.
     */
    @RequestMapping(value = "/persoonEvents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PersoonEvent> updatePersoonEvent(@Valid @RequestBody PersoonEvent persoonEvent) throws URISyntaxException {
        log.debug("REST request to update PersoonEvent : {}", persoonEvent);
        if (persoonEvent.getId() == null) {
            return createPersoonEvent(persoonEvent);
        }
        PersoonEvent result = persoonEventRepository.save(persoonEvent);
        persoonEventSearchRepository.save(persoonEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("persoonEvent", persoonEvent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /persoonEvents -> get all the persoonEvents.
     */
    @RequestMapping(value = "/persoonEvents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PersoonEvent>> getAllPersoonEvents(Pageable pageable)
        throws URISyntaxException {
        Page<PersoonEvent> page = persoonEventRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/persoonEvents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /persoonEvents/:id -> get the "id" persoonEvent.
     */
    @RequestMapping(value = "/persoonEvents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PersoonEvent> getPersoonEvent(@PathVariable Long id) {
        log.debug("REST request to get PersoonEvent : {}", id);
        return Optional.ofNullable(persoonEventRepository.findOne(id))
            .map(persoonEvent -> new ResponseEntity<>(
                persoonEvent,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /persoonEvents/:id -> delete the "id" persoonEvent.
     */
    @RequestMapping(value = "/persoonEvents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePersoonEvent(@PathVariable Long id) {
        log.debug("REST request to delete PersoonEvent : {}", id);
        persoonEventRepository.delete(id);
        persoonEventSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("persoonEvent", id.toString())).build();
    }

    /**
     * SEARCH  /_search/persoonEvents/:query -> search for the persoonEvent corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/persoonEvents/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PersoonEvent> searchPersoonEvents(@PathVariable String query) {
        return StreamSupport
            .stream(persoonEventSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
