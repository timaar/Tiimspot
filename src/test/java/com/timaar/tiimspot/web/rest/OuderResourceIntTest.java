package com.timaar.tiimspot.web.rest;

import com.timaar.tiimspot.Application;
import com.timaar.tiimspot.domain.Ouder;
import com.timaar.tiimspot.repository.OuderRepository;
import com.timaar.tiimspot.repository.search.OuderSearchRepository;

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
 * Test class for the OuderResource REST controller.
 *
 * @see OuderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OuderResourceIntTest {


    @Inject
    private OuderRepository ouderRepository;

    @Inject
    private OuderSearchRepository ouderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOuderMockMvc;

    private Ouder ouder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OuderResource ouderResource = new OuderResource();
        ReflectionTestUtils.setField(ouderResource, "ouderRepository", ouderRepository);
        ReflectionTestUtils.setField(ouderResource, "ouderSearchRepository", ouderSearchRepository);
        this.restOuderMockMvc = MockMvcBuilders.standaloneSetup(ouderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ouder = new Ouder();
    }

    @Test
    @Transactional
    public void createOuder() throws Exception {
        int databaseSizeBeforeCreate = ouderRepository.findAll().size();

        // Create the Ouder

        restOuderMockMvc.perform(post("/api/ouders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ouder)))
                .andExpect(status().isCreated());

        // Validate the Ouder in the database
        List<Ouder> ouders = ouderRepository.findAll();
        assertThat(ouders).hasSize(databaseSizeBeforeCreate + 1);
        Ouder testOuder = ouders.get(ouders.size() - 1);
    }

    @Test
    @Transactional
    public void getAllOuders() throws Exception {
        // Initialize the database
        ouderRepository.saveAndFlush(ouder);

        // Get all the ouders
        restOuderMockMvc.perform(get("/api/ouders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ouder.getId().intValue())));
    }

    @Test
    @Transactional
    public void getOuder() throws Exception {
        // Initialize the database
        ouderRepository.saveAndFlush(ouder);

        // Get the ouder
        restOuderMockMvc.perform(get("/api/ouders/{id}", ouder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ouder.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOuder() throws Exception {
        // Get the ouder
        restOuderMockMvc.perform(get("/api/ouders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOuder() throws Exception {
        // Initialize the database
        ouderRepository.saveAndFlush(ouder);

		int databaseSizeBeforeUpdate = ouderRepository.findAll().size();

        // Update the ouder

        restOuderMockMvc.perform(put("/api/ouders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ouder)))
                .andExpect(status().isOk());

        // Validate the Ouder in the database
        List<Ouder> ouders = ouderRepository.findAll();
        assertThat(ouders).hasSize(databaseSizeBeforeUpdate);
        Ouder testOuder = ouders.get(ouders.size() - 1);
    }

    @Test
    @Transactional
    public void deleteOuder() throws Exception {
        // Initialize the database
        ouderRepository.saveAndFlush(ouder);

		int databaseSizeBeforeDelete = ouderRepository.findAll().size();

        // Get the ouder
        restOuderMockMvc.perform(delete("/api/ouders/{id}", ouder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ouder> ouders = ouderRepository.findAll();
        assertThat(ouders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
