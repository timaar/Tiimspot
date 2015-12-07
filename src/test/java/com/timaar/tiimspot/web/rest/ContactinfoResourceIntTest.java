package com.timaar.tiimspot.web.rest;

import com.timaar.tiimspot.Application;
import com.timaar.tiimspot.domain.Contactinfo;
import com.timaar.tiimspot.repository.ContactinfoRepository;
import com.timaar.tiimspot.repository.search.ContactinfoSearchRepository;

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
 * Test class for the ContactinfoResource REST controller.
 *
 * @see ContactinfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContactinfoResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_TELEFOONNUMMER = "AAAAA";
    private static final String UPDATED_TELEFOONNUMMER = "BBBBB";

    @Inject
    private ContactinfoRepository contactinfoRepository;

    @Inject
    private ContactinfoSearchRepository contactinfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContactinfoMockMvc;

    private Contactinfo contactinfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactinfoResource contactinfoResource = new ContactinfoResource();
        ReflectionTestUtils.setField(contactinfoResource, "contactinfoRepository", contactinfoRepository);
        ReflectionTestUtils.setField(contactinfoResource, "contactinfoSearchRepository", contactinfoSearchRepository);
        this.restContactinfoMockMvc = MockMvcBuilders.standaloneSetup(contactinfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contactinfo = new Contactinfo();
        contactinfo.setEmail(DEFAULT_EMAIL);
        contactinfo.setTelefoonnummer(DEFAULT_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    public void createContactinfo() throws Exception {
        int databaseSizeBeforeCreate = contactinfoRepository.findAll().size();

        // Create the Contactinfo

        restContactinfoMockMvc.perform(post("/api/contactinfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactinfo)))
                .andExpect(status().isCreated());

        // Validate the Contactinfo in the database
        List<Contactinfo> contactinfos = contactinfoRepository.findAll();
        assertThat(contactinfos).hasSize(databaseSizeBeforeCreate + 1);
        Contactinfo testContactinfo = contactinfos.get(contactinfos.size() - 1);
        assertThat(testContactinfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactinfo.getTelefoonnummer()).isEqualTo(DEFAULT_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactinfoRepository.findAll().size();
        // set the field null
        contactinfo.setEmail(null);

        // Create the Contactinfo, which fails.

        restContactinfoMockMvc.perform(post("/api/contactinfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactinfo)))
                .andExpect(status().isBadRequest());

        List<Contactinfo> contactinfos = contactinfoRepository.findAll();
        assertThat(contactinfos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactinfos() throws Exception {
        // Initialize the database
        contactinfoRepository.saveAndFlush(contactinfo);

        // Get all the contactinfos
        restContactinfoMockMvc.perform(get("/api/contactinfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contactinfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER.toString())));
    }

    @Test
    @Transactional
    public void getContactinfo() throws Exception {
        // Initialize the database
        contactinfoRepository.saveAndFlush(contactinfo);

        // Get the contactinfo
        restContactinfoMockMvc.perform(get("/api/contactinfos/{id}", contactinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contactinfo.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefoonnummer").value(DEFAULT_TELEFOONNUMMER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactinfo() throws Exception {
        // Get the contactinfo
        restContactinfoMockMvc.perform(get("/api/contactinfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactinfo() throws Exception {
        // Initialize the database
        contactinfoRepository.saveAndFlush(contactinfo);

		int databaseSizeBeforeUpdate = contactinfoRepository.findAll().size();

        // Update the contactinfo
        contactinfo.setEmail(UPDATED_EMAIL);
        contactinfo.setTelefoonnummer(UPDATED_TELEFOONNUMMER);

        restContactinfoMockMvc.perform(put("/api/contactinfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactinfo)))
                .andExpect(status().isOk());

        // Validate the Contactinfo in the database
        List<Contactinfo> contactinfos = contactinfoRepository.findAll();
        assertThat(contactinfos).hasSize(databaseSizeBeforeUpdate);
        Contactinfo testContactinfo = contactinfos.get(contactinfos.size() - 1);
        assertThat(testContactinfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactinfo.getTelefoonnummer()).isEqualTo(UPDATED_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    public void deleteContactinfo() throws Exception {
        // Initialize the database
        contactinfoRepository.saveAndFlush(contactinfo);

		int databaseSizeBeforeDelete = contactinfoRepository.findAll().size();

        // Get the contactinfo
        restContactinfoMockMvc.perform(delete("/api/contactinfos/{id}", contactinfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contactinfo> contactinfos = contactinfoRepository.findAll();
        assertThat(contactinfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
