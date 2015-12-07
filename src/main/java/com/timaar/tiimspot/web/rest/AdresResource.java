package com.timaar.tiimspot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.timaar.tiimspot.domain.Adres;
import com.timaar.tiimspot.repository.AdresRepository;
import com.timaar.tiimspot.repository.search.AdresSearchRepository;
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
 * REST controller for managing Adres.
 */
@RestController
@RequestMapping("/api")
public class AdresResource {

    private final Logger log = LoggerFactory.getLogger(AdresResource.class);

    @Inject
    private AdresRepository adresRepository;

    @Inject
    private AdresSearchRepository adresSearchRepository;

    /**
     * POST  /adress -> Create a new adres.
     */
    @RequestMapping(value = "/adress",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adres> createAdres(@Valid @RequestBody Adres adres) throws URISyntaxException {
        log.debug("REST request to save Adres : {}", adres);
        if (adres.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new adres cannot already have an ID").body(null);
        }
        Adres result = adresRepository.save(adres);
        adresSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/adress/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("adres", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adress -> Updates an existing adres.
     */
    @RequestMapping(value = "/adress",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adres> updateAdres(@Valid @RequestBody Adres adres) throws URISyntaxException {
        log.debug("REST request to update Adres : {}", adres);
        if (adres.getId() == null) {
            return createAdres(adres);
        }
        Adres result = adresRepository.save(adres);
        adresSearchRepository.save(adres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("adres", adres.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adress -> get all the adress.
     */
    @RequestMapping(value = "/adress",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Adres>> getAllAdress(Pageable pageable)
        throws URISyntaxException {
        Page<Adres> page = adresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adress");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adress/:id -> get the "id" adres.
     */
    @RequestMapping(value = "/adress/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adres> getAdres(@PathVariable Long id) {
        log.debug("REST request to get Adres : {}", id);
        return Optional.ofNullable(adresRepository.findOne(id))
            .map(adres -> new ResponseEntity<>(
                adres,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /adress/:id -> delete the "id" adres.
     */
    @RequestMapping(value = "/adress/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAdres(@PathVariable Long id) {
        log.debug("REST request to delete Adres : {}", id);
        adresRepository.delete(id);
        adresSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("adres", id.toString())).build();
    }

    /**
     * SEARCH  /_search/adress/:query -> search for the adres corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/adress/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Adres> searchAdress(@PathVariable String query) {
        return StreamSupport
            .stream(adresSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
