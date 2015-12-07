package com.timaar.tiimspot.web.rest;

import com.timaar.tiimspot.Application;
import com.timaar.tiimspot.domain.Persoon;
import com.timaar.tiimspot.repository.PersoonRepository;
import com.timaar.tiimspot.repository.search.PersoonSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.timaar.tiimspot.domain.enumeration.Geslacht;

/**
 * Test class for the PersoonResource REST controller.
 *
 * @see PersoonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersoonResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VOORNAAM = "A";
    private static final String UPDATED_VOORNAAM = "B";
    private static final String DEFAULT_NAAM = "AAAAA";
    private static final String UPDATED_NAAM = "BBBBB";


private static final Geslacht DEFAULT_GESLACHT = Geslacht.M;
    private static final Geslacht UPDATED_GESLACHT = Geslacht.V;

    private static final ZonedDateTime DEFAULT_GEBOORTE_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_GEBOORTE_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_GEBOORTE_DATUM_STR = dateTimeFormatter.format(DEFAULT_GEBOORTE_DATUM);

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private PersoonSearchRepository persoonSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPersoonMockMvc;

    private Persoon persoon;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersoonResource persoonResource = new PersoonResource();
        ReflectionTestUtils.setField(persoonResource, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(persoonResource, "persoonSearchRepository", persoonSearchRepository);
        this.restPersoonMockMvc = MockMvcBuilders.standaloneSetup(persoonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        persoon = new Persoon();
        persoon.setVoornaam(DEFAULT_VOORNAAM);
        persoon.setNaam(DEFAULT_NAAM);
        persoon.setGeslacht(DEFAULT_GESLACHT);
        persoon.setGeboorteDatum(DEFAULT_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    public void createPersoon() throws Exception {
        int databaseSizeBeforeCreate = persoonRepository.findAll().size();

        // Create the Persoon

        restPersoonMockMvc.perform(post("/api/persoons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoon)))
                .andExpect(status().isCreated());

        // Validate the Persoon in the database
        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeCreate + 1);
        Persoon testPersoon = persoons.get(persoons.size() - 1);
        assertThat(testPersoon.getVoornaam()).isEqualTo(DEFAULT_VOORNAAM);
        assertThat(testPersoon.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testPersoon.getGeslacht()).isEqualTo(DEFAULT_GESLACHT);
        assertThat(testPersoon.getGeboorteDatum()).isEqualTo(DEFAULT_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    public void checkVoornaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonRepository.findAll().size();
        // set the field null
        persoon.setVoornaam(null);

        // Create the Persoon, which fails.

        restPersoonMockMvc.perform(post("/api/persoons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoon)))
                .andExpect(status().isBadRequest());

        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonRepository.findAll().size();
        // set the field null
        persoon.setNaam(null);

        // Create the Persoon, which fails.

        restPersoonMockMvc.perform(post("/api/persoons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoon)))
                .andExpect(status().isBadRequest());

        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeslachtIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonRepository.findAll().size();
        // set the field null
        persoon.setGeslacht(null);

        // Create the Persoon, which fails.

        restPersoonMockMvc.perform(post("/api/persoons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoon)))
                .andExpect(status().isBadRequest());

        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeboorteDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonRepository.findAll().size();
        // set the field null
        persoon.setGeboorteDatum(null);

        // Create the Persoon, which fails.

        restPersoonMockMvc.perform(post("/api/persoons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoon)))
                .andExpect(status().isBadRequest());

        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersoons() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        // Get all the persoons
        restPersoonMockMvc.perform(get("/api/persoons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(persoon.getId().intValue())))
                .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM.toString())))
                .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM.toString())))
                .andExpect(jsonPath("$.[*].geslacht").value(hasItem(DEFAULT_GESLACHT.toString())))
                .andExpect(jsonPath("$.[*].geboorteDatum").value(hasItem(DEFAULT_GEBOORTE_DATUM_STR)));
    }

    @Test
    @Transactional
    public void getPersoon() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        // Get the persoon
        restPersoonMockMvc.perform(get("/api/persoons/{id}", persoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(persoon.getId().intValue()))
            .andExpect(jsonPath("$.voornaam").value(DEFAULT_VOORNAAM.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM.toString()))
            .andExpect(jsonPath("$.geslacht").value(DEFAULT_GESLACHT.toString()))
            .andExpect(jsonPath("$.geboorteDatum").value(DEFAULT_GEBOORTE_DATUM_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPersoon() throws Exception {
        // Get the persoon
        restPersoonMockMvc.perform(get("/api/persoons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersoon() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

		int databaseSizeBeforeUpdate = persoonRepository.findAll().size();

        // Update the persoon
        persoon.setVoornaam(UPDATED_VOORNAAM);
        persoon.setNaam(UPDATED_NAAM);
        persoon.setGeslacht(UPDATED_GESLACHT);
        persoon.setGeboorteDatum(UPDATED_GEBOORTE_DATUM);

        restPersoonMockMvc.perform(put("/api/persoons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(persoon)))
                .andExpect(status().isOk());

        // Validate the Persoon in the database
        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeUpdate);
        Persoon testPersoon = persoons.get(persoons.size() - 1);
        assertThat(testPersoon.getVoornaam()).isEqualTo(UPDATED_VOORNAAM);
        assertThat(testPersoon.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testPersoon.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testPersoon.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    public void deletePersoon() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

		int databaseSizeBeforeDelete = persoonRepository.findAll().size();

        // Get the persoon
        restPersoonMockMvc.perform(delete("/api/persoons/{id}", persoon.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Persoon> persoons = persoonRepository.findAll();
        assertThat(persoons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
