package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.domain.Movimiento;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.domain.TipoComprobante;
import com.ojeda.obras.domain.enumeration.MetodoPago;
import com.ojeda.obras.repository.MovimientoRepository;
import com.ojeda.obras.service.MovimientoService;
import com.ojeda.obras.service.criteria.MovimientoCriteria;
import com.ojeda.obras.service.dto.MovimientoDTO;
import com.ojeda.obras.service.mapper.MovimientoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link MovimientoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MovimientoResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MetodoPago DEFAULT_METODO_PAGO = MetodoPago.EFECTIVO;
    private static final MetodoPago UPDATED_METODO_PAGO = MetodoPago.BANCO;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/movimientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Mock
    private MovimientoRepository movimientoRepositoryMock;

    @Autowired
    private MovimientoMapper movimientoMapper;

    @Mock
    private MovimientoService movimientoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovimientoMockMvc;

    private Movimiento movimiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimiento createEntity(EntityManager em) {
        Movimiento movimiento = new Movimiento()
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .metodoPago(DEFAULT_METODO_PAGO)
            .amount(DEFAULT_AMOUNT)
            .transactionNumber(DEFAULT_TRANSACTION_NUMBER);
        return movimiento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimiento createUpdatedEntity(EntityManager em) {
        Movimiento movimiento = new Movimiento()
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .metodoPago(UPDATED_METODO_PAGO)
            .amount(UPDATED_AMOUNT)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER);
        return movimiento;
    }

    @BeforeEach
    public void initTest() {
        movimiento = createEntity(em);
    }

    @Test
    @Transactional
    void createMovimiento() throws Exception {
        int databaseSizeBeforeCreate = movimientoRepository.findAll().size();
        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);
        restMovimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeCreate + 1);
        Movimiento testMovimiento = movimientoList.get(movimientoList.size() - 1);
        assertThat(testMovimiento.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMovimiento.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMovimiento.getMetodoPago()).isEqualTo(DEFAULT_METODO_PAGO);
        assertThat(testMovimiento.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMovimiento.getTransactionNumber()).isEqualTo(DEFAULT_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void createMovimientoWithExistingId() throws Exception {
        // Create the Movimiento with an existing ID
        movimiento.setId(1L);
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        int databaseSizeBeforeCreate = movimientoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientoRepository.findAll().size();
        // set the field null
        movimiento.setDate(null);

        // Create the Movimiento, which fails.
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        restMovimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isBadRequest());

        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientoRepository.findAll().size();
        // set the field null
        movimiento.setDescription(null);

        // Create the Movimiento, which fails.
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        restMovimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isBadRequest());

        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetodoPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientoRepository.findAll().size();
        // set the field null
        movimiento.setMetodoPago(null);

        // Create the Movimiento, which fails.
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        restMovimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isBadRequest());

        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientoRepository.findAll().size();
        // set the field null
        movimiento.setAmount(null);

        // Create the Movimiento, which fails.
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        restMovimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isBadRequest());

        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMovimientos() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList
        restMovimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].metodoPago").value(hasItem(DEFAULT_METODO_PAGO.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMovimientosWithEagerRelationshipsIsEnabled() throws Exception {
        when(movimientoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovimientoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(movimientoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMovimientosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(movimientoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovimientoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(movimientoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMovimiento() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get the movimiento
        restMovimientoMockMvc
            .perform(get(ENTITY_API_URL_ID, movimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movimiento.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.metodoPago").value(DEFAULT_METODO_PAGO.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionNumber").value(DEFAULT_TRANSACTION_NUMBER));
    }

    @Test
    @Transactional
    void getMovimientosByIdFiltering() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        Long id = movimiento.getId();

        defaultMovimientoShouldBeFound("id.equals=" + id);
        defaultMovimientoShouldNotBeFound("id.notEquals=" + id);

        defaultMovimientoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMovimientoShouldNotBeFound("id.greaterThan=" + id);

        defaultMovimientoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMovimientoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMovimientosByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where date equals to DEFAULT_DATE
        defaultMovimientoShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the movimientoList where date equals to UPDATED_DATE
        defaultMovimientoShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMovimientosByDateIsInShouldWork() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where date in DEFAULT_DATE or UPDATED_DATE
        defaultMovimientoShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the movimientoList where date equals to UPDATED_DATE
        defaultMovimientoShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMovimientosByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where date is not null
        defaultMovimientoShouldBeFound("date.specified=true");

        // Get all the movimientoList where date is null
        defaultMovimientoShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllMovimientosByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where description equals to DEFAULT_DESCRIPTION
        defaultMovimientoShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the movimientoList where description equals to UPDATED_DESCRIPTION
        defaultMovimientoShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMovimientosByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMovimientoShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the movimientoList where description equals to UPDATED_DESCRIPTION
        defaultMovimientoShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMovimientosByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where description is not null
        defaultMovimientoShouldBeFound("description.specified=true");

        // Get all the movimientoList where description is null
        defaultMovimientoShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllMovimientosByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where description contains DEFAULT_DESCRIPTION
        defaultMovimientoShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the movimientoList where description contains UPDATED_DESCRIPTION
        defaultMovimientoShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMovimientosByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where description does not contain DEFAULT_DESCRIPTION
        defaultMovimientoShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the movimientoList where description does not contain UPDATED_DESCRIPTION
        defaultMovimientoShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMovimientosByMetodoPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where metodoPago equals to DEFAULT_METODO_PAGO
        defaultMovimientoShouldBeFound("metodoPago.equals=" + DEFAULT_METODO_PAGO);

        // Get all the movimientoList where metodoPago equals to UPDATED_METODO_PAGO
        defaultMovimientoShouldNotBeFound("metodoPago.equals=" + UPDATED_METODO_PAGO);
    }

    @Test
    @Transactional
    void getAllMovimientosByMetodoPagoIsInShouldWork() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where metodoPago in DEFAULT_METODO_PAGO or UPDATED_METODO_PAGO
        defaultMovimientoShouldBeFound("metodoPago.in=" + DEFAULT_METODO_PAGO + "," + UPDATED_METODO_PAGO);

        // Get all the movimientoList where metodoPago equals to UPDATED_METODO_PAGO
        defaultMovimientoShouldNotBeFound("metodoPago.in=" + UPDATED_METODO_PAGO);
    }

    @Test
    @Transactional
    void getAllMovimientosByMetodoPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where metodoPago is not null
        defaultMovimientoShouldBeFound("metodoPago.specified=true");

        // Get all the movimientoList where metodoPago is null
        defaultMovimientoShouldNotBeFound("metodoPago.specified=false");
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount equals to DEFAULT_AMOUNT
        defaultMovimientoShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the movimientoList where amount equals to UPDATED_AMOUNT
        defaultMovimientoShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultMovimientoShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the movimientoList where amount equals to UPDATED_AMOUNT
        defaultMovimientoShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount is not null
        defaultMovimientoShouldBeFound("amount.specified=true");

        // Get all the movimientoList where amount is null
        defaultMovimientoShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultMovimientoShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the movimientoList where amount is greater than or equal to UPDATED_AMOUNT
        defaultMovimientoShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount is less than or equal to DEFAULT_AMOUNT
        defaultMovimientoShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the movimientoList where amount is less than or equal to SMALLER_AMOUNT
        defaultMovimientoShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount is less than DEFAULT_AMOUNT
        defaultMovimientoShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the movimientoList where amount is less than UPDATED_AMOUNT
        defaultMovimientoShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMovimientosByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where amount is greater than DEFAULT_AMOUNT
        defaultMovimientoShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the movimientoList where amount is greater than SMALLER_AMOUNT
        defaultMovimientoShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMovimientosByTransactionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where transactionNumber equals to DEFAULT_TRANSACTION_NUMBER
        defaultMovimientoShouldBeFound("transactionNumber.equals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the movimientoList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultMovimientoShouldNotBeFound("transactionNumber.equals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllMovimientosByTransactionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where transactionNumber in DEFAULT_TRANSACTION_NUMBER or UPDATED_TRANSACTION_NUMBER
        defaultMovimientoShouldBeFound("transactionNumber.in=" + DEFAULT_TRANSACTION_NUMBER + "," + UPDATED_TRANSACTION_NUMBER);

        // Get all the movimientoList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultMovimientoShouldNotBeFound("transactionNumber.in=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllMovimientosByTransactionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where transactionNumber is not null
        defaultMovimientoShouldBeFound("transactionNumber.specified=true");

        // Get all the movimientoList where transactionNumber is null
        defaultMovimientoShouldNotBeFound("transactionNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllMovimientosByTransactionNumberContainsSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where transactionNumber contains DEFAULT_TRANSACTION_NUMBER
        defaultMovimientoShouldBeFound("transactionNumber.contains=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the movimientoList where transactionNumber contains UPDATED_TRANSACTION_NUMBER
        defaultMovimientoShouldNotBeFound("transactionNumber.contains=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllMovimientosByTransactionNumberNotContainsSomething() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        // Get all the movimientoList where transactionNumber does not contain DEFAULT_TRANSACTION_NUMBER
        defaultMovimientoShouldNotBeFound("transactionNumber.doesNotContain=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the movimientoList where transactionNumber does not contain UPDATED_TRANSACTION_NUMBER
        defaultMovimientoShouldBeFound("transactionNumber.doesNotContain=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllMovimientosByObraIsEqualToSomething() throws Exception {
        Obra obra;
        if (TestUtil.findAll(em, Obra.class).isEmpty()) {
            movimientoRepository.saveAndFlush(movimiento);
            obra = ObraResourceIT.createEntity(em);
        } else {
            obra = TestUtil.findAll(em, Obra.class).get(0);
        }
        em.persist(obra);
        em.flush();
        movimiento.setObra(obra);
        movimientoRepository.saveAndFlush(movimiento);
        Long obraId = obra.getId();

        // Get all the movimientoList where obra equals to obraId
        defaultMovimientoShouldBeFound("obraId.equals=" + obraId);

        // Get all the movimientoList where obra equals to (obraId + 1)
        defaultMovimientoShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    @Test
    @Transactional
    void getAllMovimientosBySubcontratistaIsEqualToSomething() throws Exception {
        Subcontratista subcontratista;
        if (TestUtil.findAll(em, Subcontratista.class).isEmpty()) {
            movimientoRepository.saveAndFlush(movimiento);
            subcontratista = SubcontratistaResourceIT.createEntity(em);
        } else {
            subcontratista = TestUtil.findAll(em, Subcontratista.class).get(0);
        }
        em.persist(subcontratista);
        em.flush();
        movimiento.setSubcontratista(subcontratista);
        movimientoRepository.saveAndFlush(movimiento);
        Long subcontratistaId = subcontratista.getId();

        // Get all the movimientoList where subcontratista equals to subcontratistaId
        defaultMovimientoShouldBeFound("subcontratistaId.equals=" + subcontratistaId);

        // Get all the movimientoList where subcontratista equals to (subcontratistaId + 1)
        defaultMovimientoShouldNotBeFound("subcontratistaId.equals=" + (subcontratistaId + 1));
    }

    @Test
    @Transactional
    void getAllMovimientosByConceptoIsEqualToSomething() throws Exception {
        Concepto concepto;
        if (TestUtil.findAll(em, Concepto.class).isEmpty()) {
            movimientoRepository.saveAndFlush(movimiento);
            concepto = ConceptoResourceIT.createEntity(em);
        } else {
            concepto = TestUtil.findAll(em, Concepto.class).get(0);
        }
        em.persist(concepto);
        em.flush();
        movimiento.setConcepto(concepto);
        movimientoRepository.saveAndFlush(movimiento);
        Long conceptoId = concepto.getId();

        // Get all the movimientoList where concepto equals to conceptoId
        defaultMovimientoShouldBeFound("conceptoId.equals=" + conceptoId);

        // Get all the movimientoList where concepto equals to (conceptoId + 1)
        defaultMovimientoShouldNotBeFound("conceptoId.equals=" + (conceptoId + 1));
    }

    @Test
    @Transactional
    void getAllMovimientosByTipoComprobanteIsEqualToSomething() throws Exception {
        TipoComprobante tipoComprobante;
        if (TestUtil.findAll(em, TipoComprobante.class).isEmpty()) {
            movimientoRepository.saveAndFlush(movimiento);
            tipoComprobante = TipoComprobanteResourceIT.createEntity(em);
        } else {
            tipoComprobante = TestUtil.findAll(em, TipoComprobante.class).get(0);
        }
        em.persist(tipoComprobante);
        em.flush();
        movimiento.setTipoComprobante(tipoComprobante);
        movimientoRepository.saveAndFlush(movimiento);
        Long tipoComprobanteId = tipoComprobante.getId();

        // Get all the movimientoList where tipoComprobante equals to tipoComprobanteId
        defaultMovimientoShouldBeFound("tipoComprobanteId.equals=" + tipoComprobanteId);

        // Get all the movimientoList where tipoComprobante equals to (tipoComprobanteId + 1)
        defaultMovimientoShouldNotBeFound("tipoComprobanteId.equals=" + (tipoComprobanteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMovimientoShouldBeFound(String filter) throws Exception {
        restMovimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].metodoPago").value(hasItem(DEFAULT_METODO_PAGO.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)));

        // Check, that the count call also returns 1
        restMovimientoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMovimientoShouldNotBeFound(String filter) throws Exception {
        restMovimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMovimientoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMovimiento() throws Exception {
        // Get the movimiento
        restMovimientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMovimiento() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();

        // Update the movimiento
        Movimiento updatedMovimiento = movimientoRepository.findById(movimiento.getId()).get();
        // Disconnect from session so that the updates on updatedMovimiento are not directly saved in db
        em.detach(updatedMovimiento);
        updatedMovimiento
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .metodoPago(UPDATED_METODO_PAGO)
            .amount(UPDATED_AMOUNT)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER);
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(updatedMovimiento);

        restMovimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movimientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movimientoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
        Movimiento testMovimiento = movimientoList.get(movimientoList.size() - 1);
        assertThat(testMovimiento.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMovimiento.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovimiento.getMetodoPago()).isEqualTo(UPDATED_METODO_PAGO);
        assertThat(testMovimiento.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMovimiento.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();
        movimiento.setId(count.incrementAndGet());

        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movimientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();
        movimiento.setId(count.incrementAndGet());

        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();
        movimiento.setId(count.incrementAndGet());

        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movimientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovimientoWithPatch() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();

        // Update the movimiento using partial update
        Movimiento partialUpdatedMovimiento = new Movimiento();
        partialUpdatedMovimiento.setId(movimiento.getId());

        partialUpdatedMovimiento.amount(UPDATED_AMOUNT).transactionNumber(UPDATED_TRANSACTION_NUMBER);

        restMovimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMovimiento))
            )
            .andExpect(status().isOk());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
        Movimiento testMovimiento = movimientoList.get(movimientoList.size() - 1);
        assertThat(testMovimiento.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMovimiento.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMovimiento.getMetodoPago()).isEqualTo(DEFAULT_METODO_PAGO);
        assertThat(testMovimiento.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMovimiento.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateMovimientoWithPatch() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();

        // Update the movimiento using partial update
        Movimiento partialUpdatedMovimiento = new Movimiento();
        partialUpdatedMovimiento.setId(movimiento.getId());

        partialUpdatedMovimiento
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .metodoPago(UPDATED_METODO_PAGO)
            .amount(UPDATED_AMOUNT)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER);

        restMovimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMovimiento))
            )
            .andExpect(status().isOk());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
        Movimiento testMovimiento = movimientoList.get(movimientoList.size() - 1);
        assertThat(testMovimiento.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMovimiento.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovimiento.getMetodoPago()).isEqualTo(UPDATED_METODO_PAGO);
        assertThat(testMovimiento.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMovimiento.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();
        movimiento.setId(count.incrementAndGet());

        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movimientoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(movimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();
        movimiento.setId(count.incrementAndGet());

        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(movimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = movimientoRepository.findAll().size();
        movimiento.setId(count.incrementAndGet());

        // Create the Movimiento
        MovimientoDTO movimientoDTO = movimientoMapper.toDto(movimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimientoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(movimientoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movimiento in the database
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovimiento() throws Exception {
        // Initialize the database
        movimientoRepository.saveAndFlush(movimiento);

        int databaseSizeBeforeDelete = movimientoRepository.findAll().size();

        // Delete the movimiento
        restMovimientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, movimiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movimiento> movimientoList = movimientoRepository.findAll();
        assertThat(movimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
