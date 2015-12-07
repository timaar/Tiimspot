package com.timaar.tiimspot.web.rest;

import com.timaar.tiimspot.Application;
import com.timaar.tiimspot.domain.PersoonEvent;
import com.timaar.tiimspot.repository.PersoonEventRepository;
import com.timaar.tiimspot.repository.search.PersoonEventSearchRepository;

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

import com.timaar.tiimspot.domain.enumeration.AanwezigheidsStatus;

/**
 * Test class for the PersoonEventResource REST controller.
 *
 * @see PersoonEventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersoonEventResourceIntTest {



private static final AanwezigheidsStatus DEFAULT_AANWEZIGHEIDS_STATUS = AanwezigheidsStatus.ONBEKEND;
    private static final AanwezigheidsStatus UPDATED_AANWEZIGHEIDS_STATUS = AanwezigheidsStatus.GESELECTEERD;

    @Inject
    private PersoonEventRepository persoonEventRepository;

    @Inject
    private PersoonEventSearchRepository persoonEventSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPersoonEventMockMvc;

    private PersoonEvent persoonEvent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersoonEventResource persoonEventResource = new PersoonEventResource();
        ReflectionTestUtils.setField(persoonEventResource, "persoonEventRepository", persoonEventRepository);
        ReflectionTestUtils.setField(persoonEventResource, "persoonEventSearchRepository", persoonEventSearchRepository);
        this.restPersoonEventMockMvc = MockMvcBuilders.standaloneSetup(persoonEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        persoonEvent = new PersoonEvent();
        persoonEvent.setAanwezigheidsStatus(DEFAULT_AANWEZIGHEIDS_STATUS);
    }

    @Test
    @Transactional
    public void createPersoonEvent() throws Exception {
        int databaseSizeBeforeCreate = persoonEventRepository.findAll().size();

        // Create the PersoonEvent

        restPersoonEventMockMvc.perform(post("/api/persoonEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoonEvent)))
                .andExpect(status().isCreated());

        // Validate the PersoonEvent in the database
        List<PersoonEvent> persoonEvents = persoonEventRepository.findAll();
        assertThat(persoonEvents).hasSize(databaseSizeBeforeCreate + 1);
        PersoonEvent testPersoonEvent = persoonEvents.get(persoonEvents.size() - 1);
        assertThat(testPersoonEvent.getAanwezigheidsStatus()).isEqualTo(DEFAULT_AANWEZIGHEIDS_STATUS);
    }

    @Test
    @Transactional
    public void checkAanwezigheidsStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonEventRepository.findAll().size();
        // set the field null
        persoonEvent.setAanwezigheidsStatus(null);

        // Create the PersoonEvent, which fails.

        restPersoonEventMockMvc.perform(post("/api/persoonEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoonEvent)))
                .andExpect(status().isBadRequest());

        List<PersoonEvent> persoonEvents = persoonEventRepository.findAll();
        assertThat(persoonEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersoonEvents() throws Exception {
        // Initialize the database
        persoonEventRepository.saveAndFlush(persoonEvent);

        // Get all the persoonEvents
        restPersoonEventMockMvc.perform(get("/api/persoonEvents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(persoonEvent.getId().intValue())))
                .andExpect(jsonPath("$.[*].aanwezigheidsStatus").value(hasItem(DEFAULT_AANWEZIGHEIDS_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPersoonEvent() throws Exception {
        // Initialize the database
        persoonEventRepository.saveAndFlush(persoonEvent);

        // Get the persoonEvent
        restPersoonEventMockMvc.perform(get("/api/persoonEvents/{id}", persoonEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(persoonEvent.getId().intValue()))
            .andExpect(jsonPath("$.aanwezigheidsStatus").value(DEFAULT_AANWEZIGHEIDS_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersoonEvent() throws Exception {
        // Get the persoonEvent
        restPersoonEventMockMvc.perform(get("/api/persoonEvents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersoonEvent() throws Exception {
        // Initialize the database
        persoonEventRepository.saveAndFlush(persoonEvent);

		int databaseSizeBeforeUpdate = persoonEventRepository.findAll().size();

        // Update the persoonEvent
        persoonEvent.setAanwezigheidsStatus(UPDATED_AANWEZIGHEIDS_STATUS);

        restPersoonEventMockMvc.perform(put("/api/persoonEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoonEvent)))
                .andExpect(status().isOk());

        // Validate the PersoonEvent in the database
        List<PersoonEvent> persoonEvents = persoonEventRepository.findAll();
        assertThat(persoonEvents).hasSize(databaseSizeBeforeUpdate);
        PersoonEvent testPersoonEvent = persoonEvents.get(persoonEvents.size() - 1);
        assertThat(testPersoonEvent.getAanwezigheidsStatus()).isEqualTo(UPDATED_AANWEZIGHEIDS_STATUS);
    }

    @Test
    @Transactional
    public void deletePersoonEvent() throws Exception {
        // Initialize the database
        persoonEventRepository.saveAndFlush(persoonEvent);

		int databaseSizeBeforeDelete = persoonEventRepository.findAll().size();

        // Get the persoonEvent
        restPersoonEventMockMvc.perform(delete("/api/persoonEvents/{id}", persoonEvent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PersoonEvent> persoonEvents = persoonEventRepository.findAll();
        assertThat(persoonEvents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
