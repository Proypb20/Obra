package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.repository.DetalleListaPrecioRepository;
import com.ojeda.obras.service.DetalleListaPrecioService;
import com.ojeda.obras.service.criteria.DetalleListaPrecioCriteria;
import com.ojeda.obras.service.dto.DetalleListaPrecioDTO;
import com.ojeda.obras.service.mapper.DetalleListaPrecioMapper;
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
 * Integration tests for the {@link DetalleListaPrecioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DetalleListaPrecioResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/detalle-lista-precios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetalleListaPrecioRepository detalleListaPrecioRepository;

    @Mock
    private DetalleListaPrecioRepository detalleListaPrecioRepositoryMock;

    @Autowired
    private DetalleListaPrecioMapper detalleListaPrecioMapper;

    @Mock
    private DetalleListaPrecioService detalleListaPrecioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalleListaPrecioMockMvc;

    private DetalleListaPrecio detalleListaPrecio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleListaPrecio createEntity(EntityManager em) {
        DetalleListaPrecio detalleListaPrecio = new DetalleListaPrecio().product(DEFAULT_PRODUCT).amount(DEFAULT_AMOUNT);
        return detalleListaPrecio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleListaPrecio createUpdatedEntity(EntityManager em) {
        DetalleListaPrecio detalleListaPrecio = new DetalleListaPrecio().product(UPDATED_PRODUCT).amount(UPDATED_AMOUNT);
        return detalleListaPrecio;
    }

    @BeforeEach
    public void initTest() {
        detalleListaPrecio = createEntity(em);
    }

    @Test
    @Transactional
    void createDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeCreate = detalleListaPrecioRepository.findAll().size();
        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);
        restDetalleListaPrecioMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleListaPrecio testDetalleListaPrecio = detalleListaPrecioList.get(detalleListaPrecioList.size() - 1);
        assertThat(testDetalleListaPrecio.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testDetalleListaPrecio.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createDetalleListaPrecioWithExistingId() throws Exception {
        // Create the DetalleListaPrecio with an existing ID
        detalleListaPrecio.setId(1L);
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        int databaseSizeBeforeCreate = detalleListaPrecioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleListaPrecioMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetalleListaPrecios() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList
        restDetalleListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleListaPrecio.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetalleListaPreciosWithEagerRelationshipsIsEnabled() throws Exception {
        when(detalleListaPrecioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetalleListaPrecioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(detalleListaPrecioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetalleListaPreciosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(detalleListaPrecioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetalleListaPrecioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(detalleListaPrecioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDetalleListaPrecio() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get the detalleListaPrecio
        restDetalleListaPrecioMockMvc
            .perform(get(ENTITY_API_URL_ID, detalleListaPrecio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalleListaPrecio.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getDetalleListaPreciosByIdFiltering() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        Long id = detalleListaPrecio.getId();

        defaultDetalleListaPrecioShouldBeFound("id.equals=" + id);
        defaultDetalleListaPrecioShouldNotBeFound("id.notEquals=" + id);

        defaultDetalleListaPrecioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalleListaPrecioShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalleListaPrecioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalleListaPrecioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where code equals to DEFAULT_CODE
        defaultDetalleListaPrecioShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the detalleListaPrecioList where code equals to UPDATED_CODE
        defaultDetalleListaPrecioShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDetalleListaPrecioShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the detalleListaPrecioList where code equals to UPDATED_CODE
        defaultDetalleListaPrecioShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where code is not null
        defaultDetalleListaPrecioShouldBeFound("code.specified=true");

        // Get all the detalleListaPrecioList where code is null
        defaultDetalleListaPrecioShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByCodeContainsSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where code contains DEFAULT_CODE
        defaultDetalleListaPrecioShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the detalleListaPrecioList where code contains UPDATED_CODE
        defaultDetalleListaPrecioShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where code does not contain DEFAULT_CODE
        defaultDetalleListaPrecioShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the detalleListaPrecioList where code does not contain UPDATED_CODE
        defaultDetalleListaPrecioShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where product equals to DEFAULT_PRODUCT
        defaultDetalleListaPrecioShouldBeFound("product.equals=" + DEFAULT_PRODUCT);

        // Get all the detalleListaPrecioList where product equals to UPDATED_PRODUCT
        defaultDetalleListaPrecioShouldNotBeFound("product.equals=" + UPDATED_PRODUCT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByProductIsInShouldWork() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where product in DEFAULT_PRODUCT or UPDATED_PRODUCT
        defaultDetalleListaPrecioShouldBeFound("product.in=" + DEFAULT_PRODUCT + "," + UPDATED_PRODUCT);

        // Get all the detalleListaPrecioList where product equals to UPDATED_PRODUCT
        defaultDetalleListaPrecioShouldNotBeFound("product.in=" + UPDATED_PRODUCT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByProductIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where product is not null
        defaultDetalleListaPrecioShouldBeFound("product.specified=true");

        // Get all the detalleListaPrecioList where product is null
        defaultDetalleListaPrecioShouldNotBeFound("product.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByProductContainsSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where product contains DEFAULT_PRODUCT
        defaultDetalleListaPrecioShouldBeFound("product.contains=" + DEFAULT_PRODUCT);

        // Get all the detalleListaPrecioList where product contains UPDATED_PRODUCT
        defaultDetalleListaPrecioShouldNotBeFound("product.contains=" + UPDATED_PRODUCT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByProductNotContainsSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where product does not contain DEFAULT_PRODUCT
        defaultDetalleListaPrecioShouldNotBeFound("product.doesNotContain=" + DEFAULT_PRODUCT);

        // Get all the detalleListaPrecioList where product does not contain UPDATED_PRODUCT
        defaultDetalleListaPrecioShouldBeFound("product.doesNotContain=" + UPDATED_PRODUCT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount equals to DEFAULT_AMOUNT
        defaultDetalleListaPrecioShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the detalleListaPrecioList where amount equals to UPDATED_AMOUNT
        defaultDetalleListaPrecioShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultDetalleListaPrecioShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the detalleListaPrecioList where amount equals to UPDATED_AMOUNT
        defaultDetalleListaPrecioShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount is not null
        defaultDetalleListaPrecioShouldBeFound("amount.specified=true");

        // Get all the detalleListaPrecioList where amount is null
        defaultDetalleListaPrecioShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultDetalleListaPrecioShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the detalleListaPrecioList where amount is greater than or equal to UPDATED_AMOUNT
        defaultDetalleListaPrecioShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount is less than or equal to DEFAULT_AMOUNT
        defaultDetalleListaPrecioShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the detalleListaPrecioList where amount is less than or equal to SMALLER_AMOUNT
        defaultDetalleListaPrecioShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount is less than DEFAULT_AMOUNT
        defaultDetalleListaPrecioShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the detalleListaPrecioList where amount is less than UPDATED_AMOUNT
        defaultDetalleListaPrecioShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        // Get all the detalleListaPrecioList where amount is greater than DEFAULT_AMOUNT
        defaultDetalleListaPrecioShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the detalleListaPrecioList where amount is greater than SMALLER_AMOUNT
        defaultDetalleListaPrecioShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleListaPreciosByListaPrecioIsEqualToSomething() throws Exception {
        ListaPrecio listaPrecio;
        if (TestUtil.findAll(em, ListaPrecio.class).isEmpty()) {
            detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);
            listaPrecio = ListaPrecioResourceIT.createEntity(em);
        } else {
            listaPrecio = TestUtil.findAll(em, ListaPrecio.class).get(0);
        }
        em.persist(listaPrecio);
        em.flush();
        detalleListaPrecio.setListaPrecio(listaPrecio);
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);
        Long listaPrecioId = listaPrecio.getId();

        // Get all the detalleListaPrecioList where listaPrecio equals to listaPrecioId
        defaultDetalleListaPrecioShouldBeFound("listaPrecioId.equals=" + listaPrecioId);

        // Get all the detalleListaPrecioList where listaPrecio equals to (listaPrecioId + 1)
        defaultDetalleListaPrecioShouldNotBeFound("listaPrecioId.equals=" + (listaPrecioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalleListaPrecioShouldBeFound(String filter) throws Exception {
        restDetalleListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleListaPrecio.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));

        // Check, that the count call also returns 1
        restDetalleListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalleListaPrecioShouldNotBeFound(String filter) throws Exception {
        restDetalleListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalleListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDetalleListaPrecio() throws Exception {
        // Get the detalleListaPrecio
        restDetalleListaPrecioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetalleListaPrecio() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();

        // Update the detalleListaPrecio
        DetalleListaPrecio updatedDetalleListaPrecio = detalleListaPrecioRepository.findById(detalleListaPrecio.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleListaPrecio are not directly saved in db
        em.detach(updatedDetalleListaPrecio);
        updatedDetalleListaPrecio.product(UPDATED_PRODUCT).amount(UPDATED_AMOUNT);
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(updatedDetalleListaPrecio);

        restDetalleListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalleListaPrecioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
        DetalleListaPrecio testDetalleListaPrecio = detalleListaPrecioList.get(detalleListaPrecioList.size() - 1);
        assertThat(testDetalleListaPrecio.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testDetalleListaPrecio.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();
        detalleListaPrecio.setId(count.incrementAndGet());

        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalleListaPrecioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();
        detalleListaPrecio.setId(count.incrementAndGet());

        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();
        detalleListaPrecio.setId(count.incrementAndGet());

        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetalleListaPrecioWithPatch() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();

        // Update the detalleListaPrecio using partial update
        DetalleListaPrecio partialUpdatedDetalleListaPrecio = new DetalleListaPrecio();
        partialUpdatedDetalleListaPrecio.setId(detalleListaPrecio.getId());

        restDetalleListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalleListaPrecio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalleListaPrecio))
            )
            .andExpect(status().isOk());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
        DetalleListaPrecio testDetalleListaPrecio = detalleListaPrecioList.get(detalleListaPrecioList.size() - 1);
        assertThat(testDetalleListaPrecio.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testDetalleListaPrecio.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateDetalleListaPrecioWithPatch() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();

        // Update the detalleListaPrecio using partial update
        DetalleListaPrecio partialUpdatedDetalleListaPrecio = new DetalleListaPrecio();
        partialUpdatedDetalleListaPrecio.setId(detalleListaPrecio.getId());

        partialUpdatedDetalleListaPrecio.product(UPDATED_PRODUCT).amount(UPDATED_AMOUNT);

        restDetalleListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalleListaPrecio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalleListaPrecio))
            )
            .andExpect(status().isOk());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
        DetalleListaPrecio testDetalleListaPrecio = detalleListaPrecioList.get(detalleListaPrecioList.size() - 1);
        assertThat(testDetalleListaPrecio.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testDetalleListaPrecio.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();
        detalleListaPrecio.setId(count.incrementAndGet());

        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detalleListaPrecioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();
        detalleListaPrecio.setId(count.incrementAndGet());

        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetalleListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = detalleListaPrecioRepository.findAll().size();
        detalleListaPrecio.setId(count.incrementAndGet());

        // Create the DetalleListaPrecio
        DetalleListaPrecioDTO detalleListaPrecioDTO = detalleListaPrecioMapper.toDto(detalleListaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalleListaPrecioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalleListaPrecio in the database
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetalleListaPrecio() throws Exception {
        // Initialize the database
        detalleListaPrecioRepository.saveAndFlush(detalleListaPrecio);

        int databaseSizeBeforeDelete = detalleListaPrecioRepository.findAll().size();

        // Delete the detalleListaPrecio
        restDetalleListaPrecioMockMvc
            .perform(delete(ENTITY_API_URL_ID, detalleListaPrecio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalleListaPrecio> detalleListaPrecioList = detalleListaPrecioRepository.findAll();
        assertThat(detalleListaPrecioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
