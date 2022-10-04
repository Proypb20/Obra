package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.domain.enumeration.Estado;
import com.ojeda.obras.repository.DetalleAcopioRepository;
import com.ojeda.obras.service.criteria.DetalleAcopioCriteria;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import com.ojeda.obras.service.mapper.DetalleAcopioMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DetalleAcopioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetalleAcopioResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;
    private static final Float SMALLER_QUANTITY = 1F - 1F;

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;
    private static final Float SMALLER_UNIT_PRICE = 1F - 1F;

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REQUEST_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_PROMISE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROMISE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PROMISE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Estado DEFAULT_DELIVERY_STATUS = Estado.Pendiente;
    private static final Estado UPDATED_DELIVERY_STATUS = Estado.Entregado;

    private static final String ENTITY_API_URL = "/api/detalle-acopios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetalleAcopioRepository detalleAcopioRepository;

    @Autowired
    private DetalleAcopioMapper detalleAcopioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalleAcopioMockMvc;

    private DetalleAcopio detalleAcopio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleAcopio createEntity(EntityManager em) {
        DetalleAcopio detalleAcopio = new DetalleAcopio()
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .amount(DEFAULT_AMOUNT)
            .requestDate(DEFAULT_REQUEST_DATE)
            .promiseDate(DEFAULT_PROMISE_DATE)
            .deliveryStatus(DEFAULT_DELIVERY_STATUS);
        return detalleAcopio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleAcopio createUpdatedEntity(EntityManager em) {
        DetalleAcopio detalleAcopio = new DetalleAcopio()
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT)
            .requestDate(UPDATED_REQUEST_DATE)
            .promiseDate(UPDATED_PROMISE_DATE)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);
        return detalleAcopio;
    }

    @BeforeEach
    public void initTest() {
        detalleAcopio = createEntity(em);
    }

    @Test
    @Transactional
    void createDetalleAcopio() throws Exception {
        int databaseSizeBeforeCreate = detalleAcopioRepository.findAll().size();
        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);
        restDetalleAcopioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleAcopio testDetalleAcopio = detalleAcopioList.get(detalleAcopioList.size() - 1);
        assertThat(testDetalleAcopio.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDetalleAcopio.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDetalleAcopio.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDetalleAcopio.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testDetalleAcopio.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDetalleAcopio.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testDetalleAcopio.getPromiseDate()).isEqualTo(DEFAULT_PROMISE_DATE);
        assertThat(testDetalleAcopio.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void createDetalleAcopioWithExistingId() throws Exception {
        // Create the DetalleAcopio with an existing ID
        detalleAcopio.setId(1L);
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        int databaseSizeBeforeCreate = detalleAcopioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleAcopioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleAcopioRepository.findAll().size();
        // set the field null
        detalleAcopio.setQuantity(null);

        // Create the DetalleAcopio, which fails.
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        restDetalleAcopioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleAcopioRepository.findAll().size();
        // set the field null
        detalleAcopio.setUnitPrice(null);

        // Create the DetalleAcopio, which fails.
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        restDetalleAcopioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleAcopioRepository.findAll().size();
        // set the field null
        detalleAcopio.setAmount(null);

        // Create the DetalleAcopio, which fails.
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        restDetalleAcopioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleAcopioRepository.findAll().size();
        // set the field null
        detalleAcopio.setRequestDate(null);

        // Create the DetalleAcopio, which fails.
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        restDetalleAcopioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetalleAcopios() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList
        restDetalleAcopioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleAcopio.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].promiseDate").value(hasItem(DEFAULT_PROMISE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())));
    }

    @Test
    @Transactional
    void getDetalleAcopio() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get the detalleAcopio
        restDetalleAcopioMockMvc
            .perform(get(ENTITY_API_URL_ID, detalleAcopio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalleAcopio.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.promiseDate").value(DEFAULT_PROMISE_DATE.toString()))
            .andExpect(jsonPath("$.deliveryStatus").value(DEFAULT_DELIVERY_STATUS.toString()));
    }

    @Test
    @Transactional
    void getDetalleAcopiosByIdFiltering() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        Long id = detalleAcopio.getId();

        defaultDetalleAcopioShouldBeFound("id.equals=" + id);
        defaultDetalleAcopioShouldNotBeFound("id.notEquals=" + id);

        defaultDetalleAcopioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalleAcopioShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalleAcopioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalleAcopioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date equals to DEFAULT_DATE
        defaultDetalleAcopioShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the detalleAcopioList where date equals to UPDATED_DATE
        defaultDetalleAcopioShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date in DEFAULT_DATE or UPDATED_DATE
        defaultDetalleAcopioShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the detalleAcopioList where date equals to UPDATED_DATE
        defaultDetalleAcopioShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date is not null
        defaultDetalleAcopioShouldBeFound("date.specified=true");

        // Get all the detalleAcopioList where date is null
        defaultDetalleAcopioShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date is greater than or equal to DEFAULT_DATE
        defaultDetalleAcopioShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the detalleAcopioList where date is greater than or equal to UPDATED_DATE
        defaultDetalleAcopioShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date is less than or equal to DEFAULT_DATE
        defaultDetalleAcopioShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the detalleAcopioList where date is less than or equal to SMALLER_DATE
        defaultDetalleAcopioShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date is less than DEFAULT_DATE
        defaultDetalleAcopioShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the detalleAcopioList where date is less than UPDATED_DATE
        defaultDetalleAcopioShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where date is greater than DEFAULT_DATE
        defaultDetalleAcopioShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the detalleAcopioList where date is greater than SMALLER_DATE
        defaultDetalleAcopioShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where description equals to DEFAULT_DESCRIPTION
        defaultDetalleAcopioShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the detalleAcopioList where description equals to UPDATED_DESCRIPTION
        defaultDetalleAcopioShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDetalleAcopioShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the detalleAcopioList where description equals to UPDATED_DESCRIPTION
        defaultDetalleAcopioShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where description is not null
        defaultDetalleAcopioShouldBeFound("description.specified=true");

        // Get all the detalleAcopioList where description is null
        defaultDetalleAcopioShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where description contains DEFAULT_DESCRIPTION
        defaultDetalleAcopioShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the detalleAcopioList where description contains UPDATED_DESCRIPTION
        defaultDetalleAcopioShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where description does not contain DEFAULT_DESCRIPTION
        defaultDetalleAcopioShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the detalleAcopioList where description does not contain UPDATED_DESCRIPTION
        defaultDetalleAcopioShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity equals to DEFAULT_QUANTITY
        defaultDetalleAcopioShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the detalleAcopioList where quantity equals to UPDATED_QUANTITY
        defaultDetalleAcopioShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultDetalleAcopioShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the detalleAcopioList where quantity equals to UPDATED_QUANTITY
        defaultDetalleAcopioShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity is not null
        defaultDetalleAcopioShouldBeFound("quantity.specified=true");

        // Get all the detalleAcopioList where quantity is null
        defaultDetalleAcopioShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultDetalleAcopioShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the detalleAcopioList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultDetalleAcopioShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultDetalleAcopioShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the detalleAcopioList where quantity is less than or equal to SMALLER_QUANTITY
        defaultDetalleAcopioShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity is less than DEFAULT_QUANTITY
        defaultDetalleAcopioShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the detalleAcopioList where quantity is less than UPDATED_QUANTITY
        defaultDetalleAcopioShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where quantity is greater than DEFAULT_QUANTITY
        defaultDetalleAcopioShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the detalleAcopioList where quantity is greater than SMALLER_QUANTITY
        defaultDetalleAcopioShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultDetalleAcopioShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the detalleAcopioList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultDetalleAcopioShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultDetalleAcopioShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the detalleAcopioList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultDetalleAcopioShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice is not null
        defaultDetalleAcopioShouldBeFound("unitPrice.specified=true");

        // Get all the detalleAcopioList where unitPrice is null
        defaultDetalleAcopioShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultDetalleAcopioShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the detalleAcopioList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultDetalleAcopioShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultDetalleAcopioShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the detalleAcopioList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultDetalleAcopioShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultDetalleAcopioShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the detalleAcopioList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultDetalleAcopioShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultDetalleAcopioShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the detalleAcopioList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultDetalleAcopioShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount equals to DEFAULT_AMOUNT
        defaultDetalleAcopioShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the detalleAcopioList where amount equals to UPDATED_AMOUNT
        defaultDetalleAcopioShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultDetalleAcopioShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the detalleAcopioList where amount equals to UPDATED_AMOUNT
        defaultDetalleAcopioShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount is not null
        defaultDetalleAcopioShouldBeFound("amount.specified=true");

        // Get all the detalleAcopioList where amount is null
        defaultDetalleAcopioShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultDetalleAcopioShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the detalleAcopioList where amount is greater than or equal to UPDATED_AMOUNT
        defaultDetalleAcopioShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount is less than or equal to DEFAULT_AMOUNT
        defaultDetalleAcopioShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the detalleAcopioList where amount is less than or equal to SMALLER_AMOUNT
        defaultDetalleAcopioShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount is less than DEFAULT_AMOUNT
        defaultDetalleAcopioShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the detalleAcopioList where amount is less than UPDATED_AMOUNT
        defaultDetalleAcopioShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where amount is greater than DEFAULT_AMOUNT
        defaultDetalleAcopioShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the detalleAcopioList where amount is greater than SMALLER_AMOUNT
        defaultDetalleAcopioShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate equals to DEFAULT_REQUEST_DATE
        defaultDetalleAcopioShouldBeFound("requestDate.equals=" + DEFAULT_REQUEST_DATE);

        // Get all the detalleAcopioList where requestDate equals to UPDATED_REQUEST_DATE
        defaultDetalleAcopioShouldNotBeFound("requestDate.equals=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate in DEFAULT_REQUEST_DATE or UPDATED_REQUEST_DATE
        defaultDetalleAcopioShouldBeFound("requestDate.in=" + DEFAULT_REQUEST_DATE + "," + UPDATED_REQUEST_DATE);

        // Get all the detalleAcopioList where requestDate equals to UPDATED_REQUEST_DATE
        defaultDetalleAcopioShouldNotBeFound("requestDate.in=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate is not null
        defaultDetalleAcopioShouldBeFound("requestDate.specified=true");

        // Get all the detalleAcopioList where requestDate is null
        defaultDetalleAcopioShouldNotBeFound("requestDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate is greater than or equal to DEFAULT_REQUEST_DATE
        defaultDetalleAcopioShouldBeFound("requestDate.greaterThanOrEqual=" + DEFAULT_REQUEST_DATE);

        // Get all the detalleAcopioList where requestDate is greater than or equal to UPDATED_REQUEST_DATE
        defaultDetalleAcopioShouldNotBeFound("requestDate.greaterThanOrEqual=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate is less than or equal to DEFAULT_REQUEST_DATE
        defaultDetalleAcopioShouldBeFound("requestDate.lessThanOrEqual=" + DEFAULT_REQUEST_DATE);

        // Get all the detalleAcopioList where requestDate is less than or equal to SMALLER_REQUEST_DATE
        defaultDetalleAcopioShouldNotBeFound("requestDate.lessThanOrEqual=" + SMALLER_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate is less than DEFAULT_REQUEST_DATE
        defaultDetalleAcopioShouldNotBeFound("requestDate.lessThan=" + DEFAULT_REQUEST_DATE);

        // Get all the detalleAcopioList where requestDate is less than UPDATED_REQUEST_DATE
        defaultDetalleAcopioShouldBeFound("requestDate.lessThan=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByRequestDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where requestDate is greater than DEFAULT_REQUEST_DATE
        defaultDetalleAcopioShouldNotBeFound("requestDate.greaterThan=" + DEFAULT_REQUEST_DATE);

        // Get all the detalleAcopioList where requestDate is greater than SMALLER_REQUEST_DATE
        defaultDetalleAcopioShouldBeFound("requestDate.greaterThan=" + SMALLER_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate equals to DEFAULT_PROMISE_DATE
        defaultDetalleAcopioShouldBeFound("promiseDate.equals=" + DEFAULT_PROMISE_DATE);

        // Get all the detalleAcopioList where promiseDate equals to UPDATED_PROMISE_DATE
        defaultDetalleAcopioShouldNotBeFound("promiseDate.equals=" + UPDATED_PROMISE_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate in DEFAULT_PROMISE_DATE or UPDATED_PROMISE_DATE
        defaultDetalleAcopioShouldBeFound("promiseDate.in=" + DEFAULT_PROMISE_DATE + "," + UPDATED_PROMISE_DATE);

        // Get all the detalleAcopioList where promiseDate equals to UPDATED_PROMISE_DATE
        defaultDetalleAcopioShouldNotBeFound("promiseDate.in=" + UPDATED_PROMISE_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate is not null
        defaultDetalleAcopioShouldBeFound("promiseDate.specified=true");

        // Get all the detalleAcopioList where promiseDate is null
        defaultDetalleAcopioShouldNotBeFound("promiseDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate is greater than or equal to DEFAULT_PROMISE_DATE
        defaultDetalleAcopioShouldBeFound("promiseDate.greaterThanOrEqual=" + DEFAULT_PROMISE_DATE);

        // Get all the detalleAcopioList where promiseDate is greater than or equal to UPDATED_PROMISE_DATE
        defaultDetalleAcopioShouldNotBeFound("promiseDate.greaterThanOrEqual=" + UPDATED_PROMISE_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate is less than or equal to DEFAULT_PROMISE_DATE
        defaultDetalleAcopioShouldBeFound("promiseDate.lessThanOrEqual=" + DEFAULT_PROMISE_DATE);

        // Get all the detalleAcopioList where promiseDate is less than or equal to SMALLER_PROMISE_DATE
        defaultDetalleAcopioShouldNotBeFound("promiseDate.lessThanOrEqual=" + SMALLER_PROMISE_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsLessThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate is less than DEFAULT_PROMISE_DATE
        defaultDetalleAcopioShouldNotBeFound("promiseDate.lessThan=" + DEFAULT_PROMISE_DATE);

        // Get all the detalleAcopioList where promiseDate is less than UPDATED_PROMISE_DATE
        defaultDetalleAcopioShouldBeFound("promiseDate.lessThan=" + UPDATED_PROMISE_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByPromiseDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where promiseDate is greater than DEFAULT_PROMISE_DATE
        defaultDetalleAcopioShouldNotBeFound("promiseDate.greaterThan=" + DEFAULT_PROMISE_DATE);

        // Get all the detalleAcopioList where promiseDate is greater than SMALLER_PROMISE_DATE
        defaultDetalleAcopioShouldBeFound("promiseDate.greaterThan=" + SMALLER_PROMISE_DATE);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDeliveryStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where deliveryStatus equals to DEFAULT_DELIVERY_STATUS
        defaultDetalleAcopioShouldBeFound("deliveryStatus.equals=" + DEFAULT_DELIVERY_STATUS);

        // Get all the detalleAcopioList where deliveryStatus equals to UPDATED_DELIVERY_STATUS
        defaultDetalleAcopioShouldNotBeFound("deliveryStatus.equals=" + UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDeliveryStatusIsInShouldWork() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where deliveryStatus in DEFAULT_DELIVERY_STATUS or UPDATED_DELIVERY_STATUS
        defaultDetalleAcopioShouldBeFound("deliveryStatus.in=" + DEFAULT_DELIVERY_STATUS + "," + UPDATED_DELIVERY_STATUS);

        // Get all the detalleAcopioList where deliveryStatus equals to UPDATED_DELIVERY_STATUS
        defaultDetalleAcopioShouldNotBeFound("deliveryStatus.in=" + UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByDeliveryStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        // Get all the detalleAcopioList where deliveryStatus is not null
        defaultDetalleAcopioShouldBeFound("deliveryStatus.specified=true");

        // Get all the detalleAcopioList where deliveryStatus is null
        defaultDetalleAcopioShouldNotBeFound("deliveryStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalleAcopiosByAcopioIsEqualToSomething() throws Exception {
        Acopio acopio;
        if (TestUtil.findAll(em, Acopio.class).isEmpty()) {
            detalleAcopioRepository.saveAndFlush(detalleAcopio);
            acopio = AcopioResourceIT.createEntity(em);
        } else {
            acopio = TestUtil.findAll(em, Acopio.class).get(0);
        }
        em.persist(acopio);
        em.flush();
        detalleAcopio.setAcopio(acopio);
        detalleAcopioRepository.saveAndFlush(detalleAcopio);
        Long acopioId = acopio.getId();

        // Get all the detalleAcopioList where acopio equals to acopioId
        defaultDetalleAcopioShouldBeFound("acopioId.equals=" + acopioId);

        // Get all the detalleAcopioList where acopio equals to (acopioId + 1)
        defaultDetalleAcopioShouldNotBeFound("acopioId.equals=" + (acopioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalleAcopioShouldBeFound(String filter) throws Exception {
        restDetalleAcopioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleAcopio.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].promiseDate").value(hasItem(DEFAULT_PROMISE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())));

        // Check, that the count call also returns 1
        restDetalleAcopioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalleAcopioShouldNotBeFound(String filter) throws Exception {
        restDetalleAcopioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalleAcopioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDetalleAcopio() throws Exception {
        // Get the detalleAcopio
        restDetalleAcopioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetalleAcopio() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();

        // Update the detalleAcopio
        DetalleAcopio updatedDetalleAcopio = detalleAcopioRepository.findById(detalleAcopio.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleAcopio are not directly saved in db
        em.detach(updatedDetalleAcopio);
        updatedDetalleAcopio
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT)
            .requestDate(UPDATED_REQUEST_DATE)
            .promiseDate(UPDATED_PROMISE_DATE)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(updatedDetalleAcopio);

        restDetalleAcopioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalleAcopioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
        DetalleAcopio testDetalleAcopio = detalleAcopioList.get(detalleAcopioList.size() - 1);
        assertThat(testDetalleAcopio.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDetalleAcopio.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDetalleAcopio.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDetalleAcopio.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testDetalleAcopio.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDetalleAcopio.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testDetalleAcopio.getPromiseDate()).isEqualTo(UPDATED_PROMISE_DATE);
        assertThat(testDetalleAcopio.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDetalleAcopio() throws Exception {
        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();
        detalleAcopio.setId(count.incrementAndGet());

        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleAcopioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalleAcopioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetalleAcopio() throws Exception {
        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();
        detalleAcopio.setId(count.incrementAndGet());

        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleAcopioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetalleAcopio() throws Exception {
        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();
        detalleAcopio.setId(count.incrementAndGet());

        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleAcopioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetalleAcopioWithPatch() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();

        // Update the detalleAcopio using partial update
        DetalleAcopio partialUpdatedDetalleAcopio = new DetalleAcopio();
        partialUpdatedDetalleAcopio.setId(detalleAcopio.getId());

        partialUpdatedDetalleAcopio.deliveryStatus(UPDATED_DELIVERY_STATUS);

        restDetalleAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalleAcopio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalleAcopio))
            )
            .andExpect(status().isOk());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
        DetalleAcopio testDetalleAcopio = detalleAcopioList.get(detalleAcopioList.size() - 1);
        assertThat(testDetalleAcopio.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDetalleAcopio.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDetalleAcopio.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDetalleAcopio.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testDetalleAcopio.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDetalleAcopio.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testDetalleAcopio.getPromiseDate()).isEqualTo(DEFAULT_PROMISE_DATE);
        assertThat(testDetalleAcopio.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDetalleAcopioWithPatch() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();

        // Update the detalleAcopio using partial update
        DetalleAcopio partialUpdatedDetalleAcopio = new DetalleAcopio();
        partialUpdatedDetalleAcopio.setId(detalleAcopio.getId());

        partialUpdatedDetalleAcopio
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT)
            .requestDate(UPDATED_REQUEST_DATE)
            .promiseDate(UPDATED_PROMISE_DATE)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);

        restDetalleAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalleAcopio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalleAcopio))
            )
            .andExpect(status().isOk());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
        DetalleAcopio testDetalleAcopio = detalleAcopioList.get(detalleAcopioList.size() - 1);
        assertThat(testDetalleAcopio.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDetalleAcopio.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDetalleAcopio.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDetalleAcopio.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testDetalleAcopio.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDetalleAcopio.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testDetalleAcopio.getPromiseDate()).isEqualTo(UPDATED_PROMISE_DATE);
        assertThat(testDetalleAcopio.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDetalleAcopio() throws Exception {
        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();
        detalleAcopio.setId(count.incrementAndGet());

        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detalleAcopioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetalleAcopio() throws Exception {
        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();
        detalleAcopio.setId(count.incrementAndGet());

        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetalleAcopio() throws Exception {
        int databaseSizeBeforeUpdate = detalleAcopioRepository.findAll().size();
        detalleAcopio.setId(count.incrementAndGet());

        // Create the DetalleAcopio
        DetalleAcopioDTO detalleAcopioDTO = detalleAcopioMapper.toDto(detalleAcopio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleAcopioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalleAcopioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalleAcopio in the database
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetalleAcopio() throws Exception {
        // Initialize the database
        detalleAcopioRepository.saveAndFlush(detalleAcopio);

        int databaseSizeBeforeDelete = detalleAcopioRepository.findAll().size();

        // Delete the detalleAcopio
        restDetalleAcopioMockMvc
            .perform(delete(ENTITY_API_URL_ID, detalleAcopio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalleAcopio> detalleAcopioList = detalleAcopioRepository.findAll();
        assertThat(detalleAcopioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
