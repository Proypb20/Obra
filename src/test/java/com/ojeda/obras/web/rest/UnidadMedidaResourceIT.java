package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.UnidadMedida;
import com.ojeda.obras.repository.UnidadMedidaRepository;
import com.ojeda.obras.service.criteria.UnidadMedidaCriteria;
import com.ojeda.obras.service.dto.UnidadMedidaDTO;
import com.ojeda.obras.service.mapper.UnidadMedidaMapper;
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
 * Integration tests for the {@link UnidadMedidaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadMedidaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unidad-medidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadMedidaMockMvc;

    private UnidadMedida unidadMedida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadMedida createEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida().name(DEFAULT_NAME);
        return unidadMedida;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadMedida createUpdatedEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida().name(UPDATED_NAME);
        return unidadMedida;
    }

    @BeforeEach
    public void initTest() {
        unidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    void createUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();
        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);
        restUnidadMedidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createUnidadMedidaWithExistingId() throws Exception {
        // Create the UnidadMedida with an existing ID
        unidadMedida.setId(1L);
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMedidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadMedidaRepository.findAll().size();
        // set the field null
        unidadMedida.setName(null);

        // Create the UnidadMedida, which fails.
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        restUnidadMedidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isBadRequest());

        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUnidadMedidas() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList
        restUnidadMedidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get the unidadMedida
        restUnidadMedidaMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getUnidadMedidasByIdFiltering() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        Long id = unidadMedida.getId();

        defaultUnidadMedidaShouldBeFound("id.equals=" + id);
        defaultUnidadMedidaShouldNotBeFound("id.notEquals=" + id);

        defaultUnidadMedidaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUnidadMedidaShouldNotBeFound("id.greaterThan=" + id);

        defaultUnidadMedidaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUnidadMedidaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUnidadMedidasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where name equals to DEFAULT_NAME
        defaultUnidadMedidaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the unidadMedidaList where name equals to UPDATED_NAME
        defaultUnidadMedidaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnidadMedidasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUnidadMedidaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the unidadMedidaList where name equals to UPDATED_NAME
        defaultUnidadMedidaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnidadMedidasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where name is not null
        defaultUnidadMedidaShouldBeFound("name.specified=true");

        // Get all the unidadMedidaList where name is null
        defaultUnidadMedidaShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllUnidadMedidasByNameContainsSomething() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where name contains DEFAULT_NAME
        defaultUnidadMedidaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the unidadMedidaList where name contains UPDATED_NAME
        defaultUnidadMedidaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUnidadMedidasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where name does not contain DEFAULT_NAME
        defaultUnidadMedidaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the unidadMedidaList where name does not contain UPDATED_NAME
        defaultUnidadMedidaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnidadMedidaShouldBeFound(String filter) throws Exception {
        restUnidadMedidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restUnidadMedidaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnidadMedidaShouldNotBeFound(String filter) throws Exception {
        restUnidadMedidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnidadMedidaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUnidadMedida() throws Exception {
        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida
        UnidadMedida updatedUnidadMedida = unidadMedidaRepository.findById(unidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadMedida are not directly saved in db
        em.detach(updatedUnidadMedida);
        updatedUnidadMedida.name(UPDATED_NAME);
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(updatedUnidadMedida);

        restUnidadMedidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadMedidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();
        unidadMedida.setId(count.incrementAndGet());

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadMedidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();
        unidadMedida.setId(count.incrementAndGet());

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();
        unidadMedida.setId(count.incrementAndGet());

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadMedidaWithPatch() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida using partial update
        UnidadMedida partialUpdatedUnidadMedida = new UnidadMedida();
        partialUpdatedUnidadMedida.setId(unidadMedida.getId());

        restUnidadMedidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadMedida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadMedida))
            )
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateUnidadMedidaWithPatch() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida using partial update
        UnidadMedida partialUpdatedUnidadMedida = new UnidadMedida();
        partialUpdatedUnidadMedida.setId(unidadMedida.getId());

        partialUpdatedUnidadMedida.name(UPDATED_NAME);

        restUnidadMedidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadMedida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadMedida))
            )
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();
        unidadMedida.setId(count.incrementAndGet());

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadMedidaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();
        unidadMedida.setId(count.incrementAndGet());

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();
        unidadMedida.setId(count.incrementAndGet());

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeDelete = unidadMedidaRepository.findAll().size();

        // Delete the unidadMedida
        restUnidadMedidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadMedida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
