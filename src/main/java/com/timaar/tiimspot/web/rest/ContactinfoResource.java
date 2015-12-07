package com.timaar.tiimspot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.timaar.tiimspot.domain.Contactinfo;
import com.timaar.tiimspot.repository.ContactinfoRepository;
import com.timaar.tiimspot.repository.search.ContactinfoSearchRepository;
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
 * REST controller for managing Contactinfo.
 */
@RestController
@RequestMapping("/api")
public class ContactinfoResource {

    private final Logger log = LoggerFactory.getLogger(ContactinfoResource.class);

    @Inject
    private ContactinfoRepository contactinfoRepository;

    @Inject
    private ContactinfoSearchRepository contactinfoSearchRepository;

    /**
     * POST  /contactinfos -> Create a new contactinfo.
     */
    @RequestMapping(value = "/contactinfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contactinfo> createContactinfo(@Valid @RequestBody Contactinfo contactinfo) throws URISyntaxException {
        log.debug("REST request to save Contactinfo : {}", contactinfo);
        if (contactinfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new contactinfo cannot already have an ID").body(null);
        }
        Contactinfo result = contactinfoRepository.save(contactinfo);
        contactinfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/contactinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contactinfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contactinfos -> Updates an existing contactinfo.
     */
    @RequestMapping(value = "/contactinfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contactinfo> updateContactinfo(@Valid @RequestBody Contactinfo contactinfo) throws URISyntaxException {
        log.debug("REST request to update Contactinfo : {}", contactinfo);
        if (contactinfo.getId() == null) {
            return createContactinfo(contactinfo);
        }
        Contactinfo result = contactinfoRepository.save(contactinfo);
        contactinfoSearchRepository.save(contactinfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contactinfo", contactinfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contactinfos -> get all the contactinfos.
     */
    @RequestMapping(value = "/contactinfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Contactinfo>> getAllContactinfos(Pageable pageable)
        throws URISyntaxException {
        Page<Contactinfo> page = contactinfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contactinfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contactinfos/:id -> get the "id" contactinfo.
     */
    @RequestMapping(value = "/contactinfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contactinfo> getContactinfo(@PathVariable Long id) {
        log.debug("REST request to get Contactinfo : {}", id);
        return Optional.ofNullable(contactinfoRepository.findOne(id))
            .map(contactinfo -> new ResponseEntity<>(
                contactinfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contactinfos/:id -> delete the "id" contactinfo.
     */
    @RequestMapping(value = "/contactinfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContactinfo(@PathVariable Long id) {
        log.debug("REST request to delete Contactinfo : {}", id);
        contactinfoRepository.delete(id);
        contactinfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contactinfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contactinfos/:query -> search for the contactinfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contactinfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contactinfo> searchContactinfos(@PathVariable String query) {
        return StreamSupport
            .stream(contactinfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
