package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.TipoComprobante;
import com.ojeda.obras.repository.TipoComprobanteRepository;
import com.ojeda.obras.service.criteria.TipoComprobanteCriteria;
import com.ojeda.obras.service.dto.TipoComprobanteDTO;
import com.ojeda.obras.service.mapper.TipoComprobanteMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TipoComprobanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoComprobanteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-comprobantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    @Autowired
    private TipoComprobanteMapper tipoComprobanteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoComprobanteMockMvc;

    private TipoComprobante tipoComprobante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoComprobante createEntity(EntityManager em) {
        TipoComprobante tipoComprobante = new TipoComprobante().name(DEFAULT_NAME);
        return tipoComprobante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoComprobante createUpdatedEntity(EntityManager em) {
        TipoComprobante tipoComprobante = new TipoComprobante().name(UPDATED_NAME);
        return tipoComprobante;
    }

    @BeforeEach
    public void initTest() {
        tipoComprobante = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoComprobante() throws Exception {
        int databaseSizeBeforeCreate = tipoComprobanteRepository.findAll().size();
        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);
        restTipoComprobanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeCreate + 1);
        TipoComprobante testTipoComprobante = tipoComprobanteList.get(tipoComprobanteList.size() - 1);
        assertThat(testTipoComprobante.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTipoComprobanteWithExistingId() throws Exception {
        // Create the TipoComprobante with an existing ID
        tipoComprobante.setId(1L);
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        int databaseSizeBeforeCreate = tipoComprobanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoComprobanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoComprobanteRepository.findAll().size();
        // set the field null
        tipoComprobante.setName(null);

        // Create the TipoComprobante, which fails.
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        restTipoComprobanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoComprobantes() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get all the tipoComprobanteList
        restTipoComprobanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoComprobante.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTipoComprobante() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get the tipoComprobante
        restTipoComprobanteMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoComprobante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoComprobante.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getTipoComprobantesByIdFiltering() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        Long id = tipoComprobante.getId();

        defaultTipoComprobanteShouldBeFound("id.equals=" + id);
        defaultTipoComprobanteShouldNotBeFound("id.notEquals=" + id);

        defaultTipoComprobanteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoComprobanteShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoComprobanteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoComprobanteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoComprobantesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get all the tipoComprobanteList where name equals to DEFAULT_NAME
        defaultTipoComprobanteShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tipoComprobanteList where name equals to UPDATED_NAME
        defaultTipoComprobanteShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTipoComprobantesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get all the tipoComprobanteList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTipoComprobanteShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tipoComprobanteList where name equals to UPDATED_NAME
        defaultTipoComprobanteShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTipoComprobantesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get all the tipoComprobanteList where name is not null
        defaultTipoComprobanteShouldBeFound("name.specified=true");

        // Get all the tipoComprobanteList where name is null
        defaultTipoComprobanteShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoComprobantesByNameContainsSomething() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get all the tipoComprobanteList where name contains DEFAULT_NAME
        defaultTipoComprobanteShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tipoComprobanteList where name contains UPDATED_NAME
        defaultTipoComprobanteShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTipoComprobantesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        // Get all the tipoComprobanteList where name does not contain DEFAULT_NAME
        defaultTipoComprobanteShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tipoComprobanteList where name does not contain UPDATED_NAME
        defaultTipoComprobanteShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoComprobanteShouldBeFound(String filter) throws Exception {
        restTipoComprobanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoComprobante.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTipoComprobanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoComprobanteShouldNotBeFound(String filter) throws Exception {
        restTipoComprobanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoComprobanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoComprobante() throws Exception {
        // Get the tipoComprobante
        restTipoComprobanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoComprobante() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();

        // Update the tipoComprobante
        TipoComprobante updatedTipoComprobante = tipoComprobanteRepository.findById(tipoComprobante.getId()).get();
        // Disconnect from session so that the updates on updatedTipoComprobante are not directly saved in db
        em.detach(updatedTipoComprobante);
        updatedTipoComprobante.name(UPDATED_NAME);
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(updatedTipoComprobante);

        restTipoComprobanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoComprobanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
        TipoComprobante testTipoComprobante = tipoComprobanteList.get(tipoComprobanteList.size() - 1);
        assertThat(testTipoComprobante.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTipoComprobante() throws Exception {
        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();
        tipoComprobante.setId(count.incrementAndGet());

        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoComprobanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoComprobanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoComprobante() throws Exception {
        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();
        tipoComprobante.setId(count.incrementAndGet());

        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoComprobanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoComprobante() throws Exception {
        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();
        tipoComprobante.setId(count.incrementAndGet());

        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoComprobanteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoComprobanteWithPatch() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();

        // Update the tipoComprobante using partial update
        TipoComprobante partialUpdatedTipoComprobante = new TipoComprobante();
        partialUpdatedTipoComprobante.setId(tipoComprobante.getId());

        restTipoComprobanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoComprobante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoComprobante))
            )
            .andExpect(status().isOk());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
        TipoComprobante testTipoComprobante = tipoComprobanteList.get(tipoComprobanteList.size() - 1);
        assertThat(testTipoComprobante.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTipoComprobanteWithPatch() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();

        // Update the tipoComprobante using partial update
        TipoComprobante partialUpdatedTipoComprobante = new TipoComprobante();
        partialUpdatedTipoComprobante.setId(tipoComprobante.getId());

        partialUpdatedTipoComprobante.name(UPDATED_NAME);

        restTipoComprobanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoComprobante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoComprobante))
            )
            .andExpect(status().isOk());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
        TipoComprobante testTipoComprobante = tipoComprobanteList.get(tipoComprobanteList.size() - 1);
        assertThat(testTipoComprobante.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTipoComprobante() throws Exception {
        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();
        tipoComprobante.setId(count.incrementAndGet());

        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoComprobanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoComprobanteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoComprobante() throws Exception {
        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();
        tipoComprobante.setId(count.incrementAndGet());

        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoComprobanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoComprobante() throws Exception {
        int databaseSizeBeforeUpdate = tipoComprobanteRepository.findAll().size();
        tipoComprobante.setId(count.incrementAndGet());

        // Create the TipoComprobante
        TipoComprobanteDTO tipoComprobanteDTO = tipoComprobanteMapper.toDto(tipoComprobante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoComprobanteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoComprobanteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoComprobante in the database
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoComprobante() throws Exception {
        // Initialize the database
        tipoComprobanteRepository.saveAndFlush(tipoComprobante);

        int databaseSizeBeforeDelete = tipoComprobanteRepository.findAll().size();

        // Delete the tipoComprobante
        restTipoComprobanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoComprobante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoComprobante> tipoComprobanteList = tipoComprobanteRepository.findAll();
        assertThat(tipoComprobanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
