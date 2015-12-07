package com.timaar.tiimspot.web.rest;

import com.timaar.tiimspot.Application;
import com.timaar.tiimspot.domain.Adres;
import com.timaar.tiimspot.repository.AdresRepository;
import com.timaar.tiimspot.repository.search.AdresSearchRepository;

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


/**
 * Test class for the AdresResource REST controller.
 *
 * @see AdresResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AdresResourceIntTest {

    private static final String DEFAULT_STRAAT = "AAAAA";
    private static final String UPDATED_STRAAT = "BBBBB";
    private static final String DEFAULT_HUISNUMMER = "AAAAA";
    private static final String UPDATED_HUISNUMMER = "BBBBB";
    private static final String DEFAULT_BUSNUMMER = "AAAAA";
    private static final String UPDATED_BUSNUMMER = "BBBBB";
    private static final String DEFAULT_POSTCODE = "AAAAA";
    private static final String UPDATED_POSTCODE = "BBBBB";
    private static final String DEFAULT_GEMEENTE = "AAAAA";
    private static final String UPDATED_GEMEENTE = "BBBBB";
    private static final String DEFAULT_LAND_ISO3 = "AAAAA";
    private static final String UPDATED_LAND_ISO3 = "BBBBB";

    @Inject
    private AdresRepository adresRepository;

    @Inject
    private AdresSearchRepository adresSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdresMockMvc;

    private Adres adres;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdresResource adresResource = new AdresResource();
        ReflectionTestUtils.setField(adresResource, "adresRepository", adresRepository);
        ReflectionTestUtils.setField(adresResource, "adresSearchRepository", adresSearchRepository);
        this.restAdresMockMvc = MockMvcBuilders.standaloneSetup(adresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        adres = new Adres();
        adres.setStraat(DEFAULT_STRAAT);
        adres.setHuisnummer(DEFAULT_HUISNUMMER);
        adres.setBusnummer(DEFAULT_BUSNUMMER);
        adres.setPostcode(DEFAULT_POSTCODE);
        adres.setGemeente(DEFAULT_GEMEENTE);
        adres.setLandISO3(DEFAULT_LAND_ISO3);
    }

    @Test
    @Transactional
    public void createAdres() throws Exception {
        int databaseSizeBeforeCreate = adresRepository.findAll().size();

        // Create the Adres

        restAdresMockMvc.perform(post("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isCreated());

        // Validate the Adres in the database
        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeCreate + 1);
        Adres testAdres = adress.get(adress.size() - 1);
        assertThat(testAdres.getStraat()).isEqualTo(DEFAULT_STRAAT);
        assertThat(testAdres.getHuisnummer()).isEqualTo(DEFAULT_HUISNUMMER);
        assertThat(testAdres.getBusnummer()).isEqualTo(DEFAULT_BUSNUMMER);
        assertThat(testAdres.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testAdres.getGemeente()).isEqualTo(DEFAULT_GEMEENTE);
        assertThat(testAdres.getLandISO3()).isEqualTo(DEFAULT_LAND_ISO3);
    }

    @Test
    @Transactional
    public void checkStraatIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresRepository.findAll().size();
        // set the field null
        adres.setStraat(null);

        // Create the Adres, which fails.

        restAdresMockMvc.perform(post("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isBadRequest());

        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHuisnummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresRepository.findAll().size();
        // set the field null
        adres.setHuisnummer(null);

        // Create the Adres, which fails.

        restAdresMockMvc.perform(post("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isBadRequest());

        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresRepository.findAll().size();
        // set the field null
        adres.setPostcode(null);

        // Create the Adres, which fails.

        restAdresMockMvc.perform(post("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isBadRequest());

        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGemeenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresRepository.findAll().size();
        // set the field null
        adres.setGemeente(null);

        // Create the Adres, which fails.

        restAdresMockMvc.perform(post("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isBadRequest());

        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLandISO3IsRequired() throws Exception {
        int databaseSizeBeforeTest = adresRepository.findAll().size();
        // set the field null
        adres.setLandISO3(null);

        // Create the Adres, which fails.

        restAdresMockMvc.perform(post("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isBadRequest());

        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdress() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get all the adress
        restAdresMockMvc.perform(get("/api/adress"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adres.getId().intValue())))
                .andExpect(jsonPath("$.[*].straat").value(hasItem(DEFAULT_STRAAT.toString())))
                .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER.toString())))
                .andExpect(jsonPath("$.[*].busnummer").value(hasItem(DEFAULT_BUSNUMMER.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].gemeente").value(hasItem(DEFAULT_GEMEENTE.toString())))
                .andExpect(jsonPath("$.[*].landISO3").value(hasItem(DEFAULT_LAND_ISO3.toString())));
    }

    @Test
    @Transactional
    public void getAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get the adres
        restAdresMockMvc.perform(get("/api/adress/{id}", adres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(adres.getId().intValue()))
            .andExpect(jsonPath("$.straat").value(DEFAULT_STRAAT.toString()))
            .andExpect(jsonPath("$.huisnummer").value(DEFAULT_HUISNUMMER.toString()))
            .andExpect(jsonPath("$.busnummer").value(DEFAULT_BUSNUMMER.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.gemeente").value(DEFAULT_GEMEENTE.toString()))
            .andExpect(jsonPath("$.landISO3").value(DEFAULT_LAND_ISO3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdres() throws Exception {
        // Get the adres
        restAdresMockMvc.perform(get("/api/adress/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

		int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Update the adres
        adres.setStraat(UPDATED_STRAAT);
        adres.setHuisnummer(UPDATED_HUISNUMMER);
        adres.setBusnummer(UPDATED_BUSNUMMER);
        adres.setPostcode(UPDATED_POSTCODE);
        adres.setGemeente(UPDATED_GEMEENTE);
        adres.setLandISO3(UPDATED_LAND_ISO3);

        restAdresMockMvc.perform(put("/api/adress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adres)))
                .andExpect(status().isOk());

        // Validate the Adres in the database
        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeUpdate);
        Adres testAdres = adress.get(adress.size() - 1);
        assertThat(testAdres.getStraat()).isEqualTo(UPDATED_STRAAT);
        assertThat(testAdres.getHuisnummer()).isEqualTo(UPDATED_HUISNUMMER);
        assertThat(testAdres.getBusnummer()).isEqualTo(UPDATED_BUSNUMMER);
        assertThat(testAdres.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAdres.getGemeente()).isEqualTo(UPDATED_GEMEENTE);
        assertThat(testAdres.getLandISO3()).isEqualTo(UPDATED_LAND_ISO3);
    }

    @Test
    @Transactional
    public void deleteAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

		int databaseSizeBeforeDelete = adresRepository.findAll().size();

        // Get the adres
        restAdresMockMvc.perform(delete("/api/adress/{id}", adres.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adres> adress = adresRepository.findAll();
        assertThat(adress).hasSize(databaseSizeBeforeDelete - 1);
    }
}
