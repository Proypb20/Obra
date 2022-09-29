package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.repository.ConceptoRepository;
import com.ojeda.obras.service.criteria.ConceptoCriteria;
import com.ojeda.obras.service.dto.ConceptoDTO;
import com.ojeda.obras.service.mapper.ConceptoMapper;
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
 * Integration tests for the {@link ConceptoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConceptoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conceptos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConceptoRepository conceptoRepository;

    @Autowired
    private ConceptoMapper conceptoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptoMockMvc;

    private Concepto concepto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concepto createEntity(EntityManager em) {
        Concepto concepto = new Concepto().name(DEFAULT_NAME);
        return concepto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concepto createUpdatedEntity(EntityManager em) {
        Concepto concepto = new Concepto().name(UPDATED_NAME);
        return concepto;
    }

    @BeforeEach
    public void initTest() {
        concepto = createEntity(em);
    }

    @Test
    @Transactional
    void createConcepto() throws Exception {
        int databaseSizeBeforeCreate = conceptoRepository.findAll().size();
        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);
        restConceptoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conceptoDTO)))
            .andExpect(status().isCreated());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeCreate + 1);
        Concepto testConcepto = conceptoList.get(conceptoList.size() - 1);
        assertThat(testConcepto.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createConceptoWithExistingId() throws Exception {
        // Create the Concepto with an existing ID
        concepto.setId(1L);
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        int databaseSizeBeforeCreate = conceptoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conceptoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptoRepository.findAll().size();
        // set the field null
        concepto.setName(null);

        // Create the Concepto, which fails.
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        restConceptoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conceptoDTO)))
            .andExpect(status().isBadRequest());

        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConceptos() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get all the conceptoList
        restConceptoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concepto.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getConcepto() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get the concepto
        restConceptoMockMvc
            .perform(get(ENTITY_API_URL_ID, concepto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concepto.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getConceptosByIdFiltering() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        Long id = concepto.getId();

        defaultConceptoShouldBeFound("id.equals=" + id);
        defaultConceptoShouldNotBeFound("id.notEquals=" + id);

        defaultConceptoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConceptoShouldNotBeFound("id.greaterThan=" + id);

        defaultConceptoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConceptoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConceptosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get all the conceptoList where name equals to DEFAULT_NAME
        defaultConceptoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the conceptoList where name equals to UPDATED_NAME
        defaultConceptoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConceptosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get all the conceptoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultConceptoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the conceptoList where name equals to UPDATED_NAME
        defaultConceptoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConceptosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get all the conceptoList where name is not null
        defaultConceptoShouldBeFound("name.specified=true");

        // Get all the conceptoList where name is null
        defaultConceptoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllConceptosByNameContainsSomething() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get all the conceptoList where name contains DEFAULT_NAME
        defaultConceptoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the conceptoList where name contains UPDATED_NAME
        defaultConceptoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConceptosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        // Get all the conceptoList where name does not contain DEFAULT_NAME
        defaultConceptoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the conceptoList where name does not contain UPDATED_NAME
        defaultConceptoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConceptoShouldBeFound(String filter) throws Exception {
        restConceptoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concepto.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restConceptoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConceptoShouldNotBeFound(String filter) throws Exception {
        restConceptoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConceptoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConcepto() throws Exception {
        // Get the concepto
        restConceptoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConcepto() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();

        // Update the concepto
        Concepto updatedConcepto = conceptoRepository.findById(concepto.getId()).get();
        // Disconnect from session so that the updates on updatedConcepto are not directly saved in db
        em.detach(updatedConcepto);
        updatedConcepto.name(UPDATED_NAME);
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(updatedConcepto);

        restConceptoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conceptoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conceptoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
        Concepto testConcepto = conceptoList.get(conceptoList.size() - 1);
        assertThat(testConcepto.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingConcepto() throws Exception {
        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();
        concepto.setId(count.incrementAndGet());

        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conceptoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conceptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcepto() throws Exception {
        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();
        concepto.setId(count.incrementAndGet());

        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conceptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcepto() throws Exception {
        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();
        concepto.setId(count.incrementAndGet());

        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conceptoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConceptoWithPatch() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();

        // Update the concepto using partial update
        Concepto partialUpdatedConcepto = new Concepto();
        partialUpdatedConcepto.setId(concepto.getId());

        restConceptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcepto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcepto))
            )
            .andExpect(status().isOk());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
        Concepto testConcepto = conceptoList.get(conceptoList.size() - 1);
        assertThat(testConcepto.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateConceptoWithPatch() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();

        // Update the concepto using partial update
        Concepto partialUpdatedConcepto = new Concepto();
        partialUpdatedConcepto.setId(concepto.getId());

        partialUpdatedConcepto.name(UPDATED_NAME);

        restConceptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcepto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcepto))
            )
            .andExpect(status().isOk());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
        Concepto testConcepto = conceptoList.get(conceptoList.size() - 1);
        assertThat(testConcepto.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingConcepto() throws Exception {
        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();
        concepto.setId(count.incrementAndGet());

        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conceptoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conceptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcepto() throws Exception {
        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();
        concepto.setId(count.incrementAndGet());

        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conceptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcepto() throws Exception {
        int databaseSizeBeforeUpdate = conceptoRepository.findAll().size();
        concepto.setId(count.incrementAndGet());

        // Create the Concepto
        ConceptoDTO conceptoDTO = conceptoMapper.toDto(concepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conceptoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concepto in the database
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcepto() throws Exception {
        // Initialize the database
        conceptoRepository.saveAndFlush(concepto);

        int databaseSizeBeforeDelete = conceptoRepository.findAll().size();

        // Delete the concepto
        restConceptoMockMvc
            .perform(delete(ENTITY_API_URL_ID, concepto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concepto> conceptoList = conceptoRepository.findAll();
        assertThat(conceptoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
