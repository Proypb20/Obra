package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.repository.AcopioRepository;
import com.ojeda.obras.service.AcopioService;
import com.ojeda.obras.service.criteria.AcopioCriteria;
import com.ojeda.obras.service.dto.AcopioDTO;
import com.ojeda.obras.service.mapper.AcopioMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AcopioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AcopioResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;
    private static final Long SMALLER_TOTAL_AMOUNT = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/acopios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcopioRepository acopioRepository;

    @Mock
    private AcopioRepository acopioRepositoryMock;

    @Autowired
    private AcopioMapper acopioMapper;

    @Mock
    private AcopioService acopioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcopioMockMvc;

    private Acopio acopio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acopio createEntity(EntityManager em) {
        Acopio acopio = new Acopio().date(DEFAULT_DATE).totalAmount(DEFAULT_TOTAL_AMOUNT);
        return acopio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acopio createUpdatedEntity(EntityManager em) {
        Acopio acopio = new Acopio().date(UPDATED_DATE).totalAmount(UPDATED_TOTAL_AMOUNT);
        return acopio;
    }

    @BeforeEach
    public void initTest() {
        acopio = createEntity(em);
    }

    @Test
    @Transactional
    void createAcopio() throws Exception {
        int databaseSizeBeforeCreate = acopioRepository.findAll().size();
        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);
        restAcopioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acopioDTO)))
            .andExpect(status().isCreated());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeCreate + 1);
        Acopio testAcopio = acopioList.get(acopioList.size() - 1);
        assertThat(testAcopio.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAcopio.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void createAcopioWithExistingId() throws Exception {
        // Create the Acopio with an existing ID
        acopio.setId(1L);
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        int databaseSizeBeforeCreate = acopioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcopioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acopioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcopios() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList
        restAcopioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acopio.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAcopiosWithEagerRelationshipsIsEnabled() throws Exception {
        when(acopioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAcopioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(acopioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAcopiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(acopioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAcopioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(acopioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAcopio() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get the acopio
        restAcopioMockMvc
            .perform(get(ENTITY_API_URL_ID, acopio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acopio.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    void getAcopiosByIdFiltering() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        Long id = acopio.getId();

        defaultAcopioShouldBeFound("id.equals=" + id);
        defaultAcopioShouldNotBeFound("id.notEquals=" + id);

        defaultAcopioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAcopioShouldNotBeFound("id.greaterThan=" + id);

        defaultAcopioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAcopioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date equals to DEFAULT_DATE
        defaultAcopioShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the acopioList where date equals to UPDATED_DATE
        defaultAcopioShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsInShouldWork() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date in DEFAULT_DATE or UPDATED_DATE
        defaultAcopioShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the acopioList where date equals to UPDATED_DATE
        defaultAcopioShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date is not null
        defaultAcopioShouldBeFound("date.specified=true");

        // Get all the acopioList where date is null
        defaultAcopioShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date is greater than or equal to DEFAULT_DATE
        defaultAcopioShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the acopioList where date is greater than or equal to UPDATED_DATE
        defaultAcopioShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date is less than or equal to DEFAULT_DATE
        defaultAcopioShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the acopioList where date is less than or equal to SMALLER_DATE
        defaultAcopioShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date is less than DEFAULT_DATE
        defaultAcopioShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the acopioList where date is less than UPDATED_DATE
        defaultAcopioShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAcopiosByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where date is greater than DEFAULT_DATE
        defaultAcopioShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the acopioList where date is greater than SMALLER_DATE
        defaultAcopioShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultAcopioShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the acopioList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultAcopioShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultAcopioShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the acopioList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultAcopioShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount is not null
        defaultAcopioShouldBeFound("totalAmount.specified=true");

        // Get all the acopioList where totalAmount is null
        defaultAcopioShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultAcopioShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the acopioList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultAcopioShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultAcopioShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the acopioList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultAcopioShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultAcopioShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the acopioList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultAcopioShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAcopiosByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        // Get all the acopioList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultAcopioShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the acopioList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultAcopioShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAcopiosByObraIsEqualToSomething() throws Exception {
        Obra obra;
        if (TestUtil.findAll(em, Obra.class).isEmpty()) {
            acopioRepository.saveAndFlush(acopio);
            obra = ObraResourceIT.createEntity(em);
        } else {
            obra = TestUtil.findAll(em, Obra.class).get(0);
        }
        em.persist(obra);
        em.flush();
        acopio.setObra(obra);
        acopioRepository.saveAndFlush(acopio);
        Long obraId = obra.getId();

        // Get all the acopioList where obra equals to obraId
        defaultAcopioShouldBeFound("obraId.equals=" + obraId);

        // Get all the acopioList where obra equals to (obraId + 1)
        defaultAcopioShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    @Test
    @Transactional
    void getAllAcopiosByProveedorIsEqualToSomething() throws Exception {
        Proveedor proveedor;
        if (TestUtil.findAll(em, Proveedor.class).isEmpty()) {
            acopioRepository.saveAndFlush(acopio);
            proveedor = ProveedorResourceIT.createEntity(em);
        } else {
            proveedor = TestUtil.findAll(em, Proveedor.class).get(0);
        }
        em.persist(proveedor);
        em.flush();
        acopio.setProveedor(proveedor);
        acopioRepository.saveAndFlush(acopio);
        Long proveedorId = proveedor.getId();

        // Get all the acopioList where proveedor equals to proveedorId
        defaultAcopioShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the acopioList where proveedor equals to (proveedorId + 1)
        defaultAcopioShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAcopioShouldBeFound(String filter) throws Exception {
        restAcopioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acopio.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restAcopioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAcopioShouldNotBeFound(String filter) throws Exception {
        restAcopioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAcopioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAcopio() throws Exception {
        // Get the acopio
        restAcopioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcopio() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();

        // Update the acopio
        Acopio updatedAcopio = acopioRepository.findById(acopio.getId()).get();
        // Disconnect from session so that the updates on updatedAcopio are not directly saved in db
        em.detach(updatedAcopio);
        updatedAcopio.date(UPDATED_DATE).totalAmount(UPDATED_TOTAL_AMOUNT);
        AcopioDTO acopioDTO = acopioMapper.toDto(updatedAcopio);

        restAcopioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acopioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acopioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
        Acopio testAcopio = acopioList.get(acopioList.size() - 1);
        assertThat(testAcopio.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAcopio.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingAcopio() throws Exception {
        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();
        acopio.setId(count.incrementAndGet());

        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcopioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acopioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcopio() throws Exception {
        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();
        acopio.setId(count.incrementAndGet());

        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcopioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcopio() throws Exception {
        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();
        acopio.setId(count.incrementAndGet());

        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcopioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acopioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcopioWithPatch() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();

        // Update the acopio using partial update
        Acopio partialUpdatedAcopio = new Acopio();
        partialUpdatedAcopio.setId(acopio.getId());

        partialUpdatedAcopio.date(UPDATED_DATE).totalAmount(UPDATED_TOTAL_AMOUNT);

        restAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcopio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcopio))
            )
            .andExpect(status().isOk());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
        Acopio testAcopio = acopioList.get(acopioList.size() - 1);
        assertThat(testAcopio.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAcopio.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateAcopioWithPatch() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();

        // Update the acopio using partial update
        Acopio partialUpdatedAcopio = new Acopio();
        partialUpdatedAcopio.setId(acopio.getId());

        partialUpdatedAcopio.date(UPDATED_DATE).totalAmount(UPDATED_TOTAL_AMOUNT);

        restAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcopio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcopio))
            )
            .andExpect(status().isOk());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
        Acopio testAcopio = acopioList.get(acopioList.size() - 1);
        assertThat(testAcopio.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAcopio.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingAcopio() throws Exception {
        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();
        acopio.setId(count.incrementAndGet());

        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acopioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcopio() throws Exception {
        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();
        acopio.setId(count.incrementAndGet());

        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcopio() throws Exception {
        int databaseSizeBeforeUpdate = acopioRepository.findAll().size();
        acopio.setId(count.incrementAndGet());

        // Create the Acopio
        AcopioDTO acopioDTO = acopioMapper.toDto(acopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(acopioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acopio in the database
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcopio() throws Exception {
        // Initialize the database
        acopioRepository.saveAndFlush(acopio);

        int databaseSizeBeforeDelete = acopioRepository.findAll().size();

        // Delete the acopio
        restAcopioMockMvc
            .perform(delete(ENTITY_API_URL_ID, acopio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acopio> acopioList = acopioRepository.findAll();
        assertThat(acopioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
