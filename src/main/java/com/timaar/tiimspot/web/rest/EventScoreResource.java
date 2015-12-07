package com.timaar.tiimspot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.timaar.tiimspot.domain.EventScore;
import com.timaar.tiimspot.repository.EventScoreRepository;
import com.timaar.tiimspot.repository.search.EventScoreSearchRepository;
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
 * REST controller for managing EventScore.
 */
@RestController
@RequestMapping("/api")
public class EventScoreResource {

    private final Logger log = LoggerFactory.getLogger(EventScoreResource.class);

    @Inject
    private EventScoreRepository eventScoreRepository;

    @Inject
    private EventScoreSearchRepository eventScoreSearchRepository;

    /**
     * POST  /eventScores -> Create a new eventScore.
     */
    @RequestMapping(value = "/eventScores",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventScore> createEventScore(@RequestBody EventScore eventScore) throws URISyntaxException {
        log.debug("REST request to save EventScore : {}", eventScore);
        if (eventScore.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eventScore cannot already have an ID").body(null);
        }
        EventScore result = eventScoreRepository.save(eventScore);
        eventScoreSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/eventScores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eventScore", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eventScores -> Updates an existing eventScore.
     */
    @RequestMapping(value = "/eventScores",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventScore> updateEventScore(@RequestBody EventScore eventScore) throws URISyntaxException {
        log.debug("REST request to update EventScore : {}", eventScore);
        if (eventScore.getId() == null) {
            return createEventScore(eventScore);
        }
        EventScore result = eventScoreRepository.save(eventScore);
        eventScoreSearchRepository.save(eventScore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eventScore", eventScore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eventScores -> get all the eventScores.
     */
    @RequestMapping(value = "/eventScores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventScore>> getAllEventScores(Pageable pageable)
        throws URISyntaxException {
        Page<EventScore> page = eventScoreRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventScores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eventScores/:id -> get the "id" eventScore.
     */
    @RequestMapping(value = "/eventScores/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventScore> getEventScore(@PathVariable Long id) {
        log.debug("REST request to get EventScore : {}", id);
        return Optional.ofNullable(eventScoreRepository.findOne(id))
            .map(eventScore -> new ResponseEntity<>(
                eventScore,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /eventScores/:id -> delete the "id" eventScore.
     */
    @RequestMapping(value = "/eventScores/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEventScore(@PathVariable Long id) {
        log.debug("REST request to delete EventScore : {}", id);
        eventScoreRepository.delete(id);
        eventScoreSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eventScore", id.toString())).build();
    }

    /**
     * SEARCH  /_search/eventScores/:query -> search for the eventScore corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/eventScores/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EventScore> searchEventScores(@PathVariable String query) {
        return StreamSupport
            .stream(eventScoreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
