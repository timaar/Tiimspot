package com.timaar.tiimspot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.timaar.tiimspot.domain.Ouder;
import com.timaar.tiimspot.repository.OuderRepository;
import com.timaar.tiimspot.repository.search.OuderSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Ouder.
 */
@RestController
@RequestMapping("/api")
public class OuderResource {

    private final Logger log = LoggerFactory.getLogger(OuderResource.class);

    @Inject
    private OuderRepository ouderRepository;

    @Inject
    private OuderSearchRepository ouderSearchRepository;

    /**
     * POST  /ouders -> Create a new ouder.
     */
    @RequestMapping(value = "/ouders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ouder> createOuder(@RequestBody Ouder ouder) throws URISyntaxException {
        log.debug("REST request to save Ouder : {}", ouder);
        if (ouder.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ouder cannot already have an ID").body(null);
        }
        Ouder result = ouderRepository.save(ouder);
        ouderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ouders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ouder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ouders -> Updates an existing ouder.
     */
    @RequestMapping(value = "/ouders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ouder> updateOuder(@RequestBody Ouder ouder) throws URISyntaxException {
        log.debug("REST request to update Ouder : {}", ouder);
        if (ouder.getId() == null) {
            return createOuder(ouder);
        }
        Ouder result = ouderRepository.save(ouder);
        ouderSearchRepository.save(ouder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ouder", ouder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ouders -> get all the ouders.
     */
    @RequestMapping(value = "/ouders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ouder>> getAllOuders(Pageable pageable)
        throws URISyntaxException {
        Page<Ouder> page = ouderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ouders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ouders/:id -> get the "id" ouder.
     */
    @RequestMapping(value = "/ouders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ouder> getOuder(@PathVariable Long id) {
        log.debug("REST request to get Ouder : {}", id);
        return Optional.ofNullable(ouderRepository.findOne(id))
            .map(ouder -> new ResponseEntity<>(
                ouder,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ouders/:id -> delete the "id" ouder.
     */
    @RequestMapping(value = "/ouders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOuder(@PathVariable Long id) {
        log.debug("REST request to delete Ouder : {}", id);
        ouderRepository.delete(id);
        ouderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ouder", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ouders/:query -> search for the ouder corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/ouders/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ouder> searchOuders(@PathVariable String query) {
        return StreamSupport
            .stream(ouderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
