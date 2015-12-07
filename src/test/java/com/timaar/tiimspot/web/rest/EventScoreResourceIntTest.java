package com.timaar.tiimspot.web.rest;

import com.timaar.tiimspot.Application;
import com.timaar.tiimspot.domain.EventScore;
import com.timaar.tiimspot.repository.EventScoreRepository;
import com.timaar.tiimspot.repository.search.EventScoreSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.timaar.tiimspot.domain.enumeration.Score;
import com.timaar.tiimspot.domain.enumeration.Score;

/**
 * Test class for the EventScoreResource REST controller.
 *
 * @see EventScoreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventScoreResourceIntTest {



private static final Score DEFAULT_DISCIPLINE = Score.ONTOELAATBAAR;
    private static final Score UPDATED_DISCIPLINE = Score.ZWAK;


private static final Score DEFAULT_TECHNIEK = Score.;
    private static final Score UPDATED_TECHNIEK = Score.;

    @Inject
    private EventScoreRepository eventScoreRepository;

    @Inject
    private EventScoreSearchRepository eventScoreSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEventScoreMockMvc;

    private EventScore eventScore;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventScoreResource eventScoreResource = new EventScoreResource();
        ReflectionTestUtils.setField(eventScoreResource, "eventScoreRepository", eventScoreRepository);
        ReflectionTestUtils.setField(eventScoreResource, "eventScoreSearchRepository", eventScoreSearchRepository);
        this.restEventScoreMockMvc = MockMvcBuilders.standaloneSetup(eventScoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        eventScore = new EventScore();
        eventScore.setDiscipline(DEFAULT_DISCIPLINE);
        eventScore.setTechniek(DEFAULT_TECHNIEK);
    }

    @Test
    @Transactional
    public void createEventScore() throws Exception {
        int databaseSizeBeforeCreate = eventScoreRepository.findAll().size();

        // Create the EventScore

        restEventScoreMockMvc.perform(post("/api/eventScores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventScore)))
                .andExpect(status().isCreated());

        // Validate the EventScore in the database
        List<EventScore> eventScores = eventScoreRepository.findAll();
        assertThat(eventScores).hasSize(databaseSizeBeforeCreate + 1);
        EventScore testEventScore = eventScores.get(eventScores.size() - 1);
        assertThat(testEventScore.getDiscipline()).isEqualTo(DEFAULT_DISCIPLINE);
        assertThat(testEventScore.getTechniek()).isEqualTo(DEFAULT_TECHNIEK);
    }

    @Test
    @Transactional
    public void getAllEventScores() throws Exception {
        // Initialize the database
        eventScoreRepository.saveAndFlush(eventScore);

        // Get all the eventScores
        restEventScoreMockMvc.perform(get("/api/eventScores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventScore.getId().intValue())))
                .andExpect(jsonPath("$.[*].discipline").value(hasItem(DEFAULT_DISCIPLINE.toString())))
                .andExpect(jsonPath("$.[*].techniek").value(hasItem(DEFAULT_TECHNIEK.toString())));
    }

    @Test
    @Transactional
    public void getEventScore() throws Exception {
        // Initialize the database
        eventScoreRepository.saveAndFlush(eventScore);

        // Get the eventScore
        restEventScoreMockMvc.perform(get("/api/eventScores/{id}", eventScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventScore.getId().intValue()))
            .andExpect(jsonPath("$.discipline").value(DEFAULT_DISCIPLINE.toString()))
            .andExpect(jsonPath("$.techniek").value(DEFAULT_TECHNIEK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventScore() throws Exception {
        // Get the eventScore
        restEventScoreMockMvc.perform(get("/api/eventScores/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventScore() throws Exception {
        // Initialize the database
        eventScoreRepository.saveAndFlush(eventScore);

		int databaseSizeBeforeUpdate = eventScoreRepository.findAll().size();

        // Update the eventScore
        eventScore.setDiscipline(UPDATED_DISCIPLINE);
        eventScore.setTechniek(UPDATED_TECHNIEK);

        restEventScoreMockMvc.perform(put("/api/eventScores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventScore)))
                .andExpect(status().isOk());

        // Validate the EventScore in the database
        List<EventScore> eventScores = eventScoreRepository.findAll();
        assertThat(eventScores).hasSize(databaseSizeBeforeUpdate);
        EventScore testEventScore = eventScores.get(eventScores.size() - 1);
        assertThat(testEventScore.getDiscipline()).isEqualTo(UPDATED_DISCIPLINE);
        assertThat(testEventScore.getTechniek()).isEqualTo(UPDATED_TECHNIEK);
    }

    @Test
    @Transactional
    public void deleteEventScore() throws Exception {
        // Initialize the database
        eventScoreRepository.saveAndFlush(eventScore);

		int databaseSizeBeforeDelete = eventScoreRepository.findAll().size();

        // Get the eventScore
        restEventScoreMockMvc.perform(delete("/api/eventScores/{id}", eventScore.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventScore> eventScores = eventScoreRepository.findAll();
        assertThat(eventScores).hasSize(databaseSizeBeforeDelete - 1);
    }
}
