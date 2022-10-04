package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.domain.TipoComprobante;
import com.ojeda.obras.domain.Transaccion;
import com.ojeda.obras.domain.enumeration.MetodoPago;
import com.ojeda.obras.repository.TransaccionRepository;
import com.ojeda.obras.service.TransaccionService;
import com.ojeda.obras.service.criteria.TransaccionCriteria;
import com.ojeda.obras.service.dto.TransaccionDTO;
import com.ojeda.obras.service.mapper.TransaccionMapper;
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
 * Integration tests for the {@link TransaccionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TransaccionResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final MetodoPago DEFAULT_PAYMENT_METHOD = MetodoPago.Efectivo;
    private static final MetodoPago UPDATED_PAYMENT_METHOD = MetodoPago.Banco;

    private static final String DEFAULT_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transaccions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Mock
    private TransaccionRepository transaccionRepositoryMock;

    @Autowired
    private TransaccionMapper transaccionMapper;

    @Mock
    private TransaccionService transaccionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransaccionMockMvc;

    private Transaccion transaccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaccion createEntity(EntityManager em) {
        Transaccion transaccion = new Transaccion()
            .date(DEFAULT_DATE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .transactionNumber(DEFAULT_TRANSACTION_NUMBER)
            .amount(DEFAULT_AMOUNT)
            .note(DEFAULT_NOTE);
        return transaccion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaccion createUpdatedEntity(EntityManager em) {
        Transaccion transaccion = new Transaccion()
            .date(UPDATED_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .amount(UPDATED_AMOUNT)
            .note(UPDATED_NOTE);
        return transaccion;
    }

    @BeforeEach
    public void initTest() {
        transaccion = createEntity(em);
    }

    @Test
    @Transactional
    void createTransaccion() throws Exception {
        int databaseSizeBeforeCreate = transaccionRepository.findAll().size();
        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);
        restTransaccionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaccion testTransaccion = transaccionList.get(transaccionList.size() - 1);
        assertThat(testTransaccion.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTransaccion.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testTransaccion.getTransactionNumber()).isEqualTo(DEFAULT_TRANSACTION_NUMBER);
        assertThat(testTransaccion.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransaccion.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createTransaccionWithExistingId() throws Exception {
        // Create the Transaccion with an existing ID
        transaccion.setId(1L);
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        int databaseSizeBeforeCreate = transaccionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransaccionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransaccions() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList
        restTransaccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransaccionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(transaccionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransaccionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(transaccionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransaccionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(transaccionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransaccionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(transaccionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTransaccion() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get the transaccion
        restTransaccionMockMvc
            .perform(get(ENTITY_API_URL_ID, transaccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaccion.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.transactionNumber").value(DEFAULT_TRANSACTION_NUMBER))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getTransaccionsByIdFiltering() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        Long id = transaccion.getId();

        defaultTransaccionShouldBeFound("id.equals=" + id);
        defaultTransaccionShouldNotBeFound("id.notEquals=" + id);

        defaultTransaccionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransaccionShouldNotBeFound("id.greaterThan=" + id);

        defaultTransaccionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransaccionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date equals to DEFAULT_DATE
        defaultTransaccionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the transaccionList where date equals to UPDATED_DATE
        defaultTransaccionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultTransaccionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the transaccionList where date equals to UPDATED_DATE
        defaultTransaccionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date is not null
        defaultTransaccionShouldBeFound("date.specified=true");

        // Get all the transaccionList where date is null
        defaultTransaccionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date is greater than or equal to DEFAULT_DATE
        defaultTransaccionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the transaccionList where date is greater than or equal to UPDATED_DATE
        defaultTransaccionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date is less than or equal to DEFAULT_DATE
        defaultTransaccionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the transaccionList where date is less than or equal to SMALLER_DATE
        defaultTransaccionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date is less than DEFAULT_DATE
        defaultTransaccionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the transaccionList where date is less than UPDATED_DATE
        defaultTransaccionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where date is greater than DEFAULT_DATE
        defaultTransaccionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the transaccionList where date is greater than SMALLER_DATE
        defaultTransaccionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where paymentMethod equals to DEFAULT_PAYMENT_METHOD
        defaultTransaccionShouldBeFound("paymentMethod.equals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the transaccionList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultTransaccionShouldNotBeFound("paymentMethod.equals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllTransaccionsByPaymentMethodIsInShouldWork() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where paymentMethod in DEFAULT_PAYMENT_METHOD or UPDATED_PAYMENT_METHOD
        defaultTransaccionShouldBeFound("paymentMethod.in=" + DEFAULT_PAYMENT_METHOD + "," + UPDATED_PAYMENT_METHOD);

        // Get all the transaccionList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultTransaccionShouldNotBeFound("paymentMethod.in=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllTransaccionsByPaymentMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where paymentMethod is not null
        defaultTransaccionShouldBeFound("paymentMethod.specified=true");

        // Get all the transaccionList where paymentMethod is null
        defaultTransaccionShouldNotBeFound("paymentMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllTransaccionsByTransactionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where transactionNumber equals to DEFAULT_TRANSACTION_NUMBER
        defaultTransaccionShouldBeFound("transactionNumber.equals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transaccionList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultTransaccionShouldNotBeFound("transactionNumber.equals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransaccionsByTransactionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where transactionNumber in DEFAULT_TRANSACTION_NUMBER or UPDATED_TRANSACTION_NUMBER
        defaultTransaccionShouldBeFound("transactionNumber.in=" + DEFAULT_TRANSACTION_NUMBER + "," + UPDATED_TRANSACTION_NUMBER);

        // Get all the transaccionList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultTransaccionShouldNotBeFound("transactionNumber.in=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransaccionsByTransactionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where transactionNumber is not null
        defaultTransaccionShouldBeFound("transactionNumber.specified=true");

        // Get all the transaccionList where transactionNumber is null
        defaultTransaccionShouldNotBeFound("transactionNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllTransaccionsByTransactionNumberContainsSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where transactionNumber contains DEFAULT_TRANSACTION_NUMBER
        defaultTransaccionShouldBeFound("transactionNumber.contains=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transaccionList where transactionNumber contains UPDATED_TRANSACTION_NUMBER
        defaultTransaccionShouldNotBeFound("transactionNumber.contains=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransaccionsByTransactionNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where transactionNumber does not contain DEFAULT_TRANSACTION_NUMBER
        defaultTransaccionShouldNotBeFound("transactionNumber.doesNotContain=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the transaccionList where transactionNumber does not contain UPDATED_TRANSACTION_NUMBER
        defaultTransaccionShouldBeFound("transactionNumber.doesNotContain=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount equals to DEFAULT_AMOUNT
        defaultTransaccionShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the transaccionList where amount equals to UPDATED_AMOUNT
        defaultTransaccionShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultTransaccionShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the transaccionList where amount equals to UPDATED_AMOUNT
        defaultTransaccionShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount is not null
        defaultTransaccionShouldBeFound("amount.specified=true");

        // Get all the transaccionList where amount is null
        defaultTransaccionShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultTransaccionShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transaccionList where amount is greater than or equal to UPDATED_AMOUNT
        defaultTransaccionShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount is less than or equal to DEFAULT_AMOUNT
        defaultTransaccionShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transaccionList where amount is less than or equal to SMALLER_AMOUNT
        defaultTransaccionShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount is less than DEFAULT_AMOUNT
        defaultTransaccionShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the transaccionList where amount is less than UPDATED_AMOUNT
        defaultTransaccionShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransaccionsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where amount is greater than DEFAULT_AMOUNT
        defaultTransaccionShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the transaccionList where amount is greater than SMALLER_AMOUNT
        defaultTransaccionShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransaccionsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where note equals to DEFAULT_NOTE
        defaultTransaccionShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the transaccionList where note equals to UPDATED_NOTE
        defaultTransaccionShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTransaccionShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the transaccionList where note equals to UPDATED_NOTE
        defaultTransaccionShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where note is not null
        defaultTransaccionShouldBeFound("note.specified=true");

        // Get all the transaccionList where note is null
        defaultTransaccionShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    void getAllTransaccionsByNoteContainsSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where note contains DEFAULT_NOTE
        defaultTransaccionShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the transaccionList where note contains UPDATED_NOTE
        defaultTransaccionShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        // Get all the transaccionList where note does not contain DEFAULT_NOTE
        defaultTransaccionShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the transaccionList where note does not contain UPDATED_NOTE
        defaultTransaccionShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTransaccionsByObraIsEqualToSomething() throws Exception {
        Obra obra;
        if (TestUtil.findAll(em, Obra.class).isEmpty()) {
            transaccionRepository.saveAndFlush(transaccion);
            obra = ObraResourceIT.createEntity(em);
        } else {
            obra = TestUtil.findAll(em, Obra.class).get(0);
        }
        em.persist(obra);
        em.flush();
        transaccion.setObra(obra);
        transaccionRepository.saveAndFlush(transaccion);
        Long obraId = obra.getId();

        // Get all the transaccionList where obra equals to obraId
        defaultTransaccionShouldBeFound("obraId.equals=" + obraId);

        // Get all the transaccionList where obra equals to (obraId + 1)
        defaultTransaccionShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    @Test
    @Transactional
    void getAllTransaccionsBySubcontratistaIsEqualToSomething() throws Exception {
        Subcontratista subcontratista;
        if (TestUtil.findAll(em, Subcontratista.class).isEmpty()) {
            transaccionRepository.saveAndFlush(transaccion);
            subcontratista = SubcontratistaResourceIT.createEntity(em);
        } else {
            subcontratista = TestUtil.findAll(em, Subcontratista.class).get(0);
        }
        em.persist(subcontratista);
        em.flush();
        transaccion.setSubcontratista(subcontratista);
        transaccionRepository.saveAndFlush(transaccion);
        Long subcontratistaId = subcontratista.getId();

        // Get all the transaccionList where subcontratista equals to subcontratistaId
        defaultTransaccionShouldBeFound("subcontratistaId.equals=" + subcontratistaId);

        // Get all the transaccionList where subcontratista equals to (subcontratistaId + 1)
        defaultTransaccionShouldNotBeFound("subcontratistaId.equals=" + (subcontratistaId + 1));
    }

    @Test
    @Transactional
    void getAllTransaccionsByTipoComprobanteIsEqualToSomething() throws Exception {
        TipoComprobante tipoComprobante;
        if (TestUtil.findAll(em, TipoComprobante.class).isEmpty()) {
            transaccionRepository.saveAndFlush(transaccion);
            tipoComprobante = TipoComprobanteResourceIT.createEntity(em);
        } else {
            tipoComprobante = TestUtil.findAll(em, TipoComprobante.class).get(0);
        }
        em.persist(tipoComprobante);
        em.flush();
        transaccion.setTipoComprobante(tipoComprobante);
        transaccionRepository.saveAndFlush(transaccion);
        Long tipoComprobanteId = tipoComprobante.getId();

        // Get all the transaccionList where tipoComprobante equals to tipoComprobanteId
        defaultTransaccionShouldBeFound("tipoComprobanteId.equals=" + tipoComprobanteId);

        // Get all the transaccionList where tipoComprobante equals to (tipoComprobanteId + 1)
        defaultTransaccionShouldNotBeFound("tipoComprobanteId.equals=" + (tipoComprobanteId + 1));
    }

    @Test
    @Transactional
    void getAllTransaccionsByConceptoIsEqualToSomething() throws Exception {
        Concepto concepto;
        if (TestUtil.findAll(em, Concepto.class).isEmpty()) {
            transaccionRepository.saveAndFlush(transaccion);
            concepto = ConceptoResourceIT.createEntity(em);
        } else {
            concepto = TestUtil.findAll(em, Concepto.class).get(0);
        }
        em.persist(concepto);
        em.flush();
        transaccion.setConcepto(concepto);
        transaccionRepository.saveAndFlush(transaccion);
        Long conceptoId = concepto.getId();

        // Get all the transaccionList where concepto equals to conceptoId
        defaultTransaccionShouldBeFound("conceptoId.equals=" + conceptoId);

        // Get all the transaccionList where concepto equals to (conceptoId + 1)
        defaultTransaccionShouldNotBeFound("conceptoId.equals=" + (conceptoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransaccionShouldBeFound(String filter) throws Exception {
        restTransaccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));

        // Check, that the count call also returns 1
        restTransaccionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransaccionShouldNotBeFound(String filter) throws Exception {
        restTransaccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransaccionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransaccion() throws Exception {
        // Get the transaccion
        restTransaccionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransaccion() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();

        // Update the transaccion
        Transaccion updatedTransaccion = transaccionRepository.findById(transaccion.getId()).get();
        // Disconnect from session so that the updates on updatedTransaccion are not directly saved in db
        em.detach(updatedTransaccion);
        updatedTransaccion
            .date(UPDATED_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .amount(UPDATED_AMOUNT)
            .note(UPDATED_NOTE);
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(updatedTransaccion);

        restTransaccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transaccionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
        Transaccion testTransaccion = transaccionList.get(transaccionList.size() - 1);
        assertThat(testTransaccion.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransaccion.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testTransaccion.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
        assertThat(testTransaccion.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaccion.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingTransaccion() throws Exception {
        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();
        transaccion.setId(count.incrementAndGet());

        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransaccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transaccionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransaccion() throws Exception {
        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();
        transaccion.setId(count.incrementAndGet());

        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransaccion() throws Exception {
        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();
        transaccion.setId(count.incrementAndGet());

        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transaccionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransaccionWithPatch() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();

        // Update the transaccion using partial update
        Transaccion partialUpdatedTransaccion = new Transaccion();
        partialUpdatedTransaccion.setId(transaccion.getId());

        partialUpdatedTransaccion.amount(UPDATED_AMOUNT).note(UPDATED_NOTE);

        restTransaccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransaccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaccion))
            )
            .andExpect(status().isOk());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
        Transaccion testTransaccion = transaccionList.get(transaccionList.size() - 1);
        assertThat(testTransaccion.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTransaccion.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testTransaccion.getTransactionNumber()).isEqualTo(DEFAULT_TRANSACTION_NUMBER);
        assertThat(testTransaccion.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaccion.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateTransaccionWithPatch() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();

        // Update the transaccion using partial update
        Transaccion partialUpdatedTransaccion = new Transaccion();
        partialUpdatedTransaccion.setId(transaccion.getId());

        partialUpdatedTransaccion
            .date(UPDATED_DATE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .amount(UPDATED_AMOUNT)
            .note(UPDATED_NOTE);

        restTransaccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransaccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaccion))
            )
            .andExpect(status().isOk());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
        Transaccion testTransaccion = transaccionList.get(transaccionList.size() - 1);
        assertThat(testTransaccion.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransaccion.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testTransaccion.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
        assertThat(testTransaccion.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaccion.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingTransaccion() throws Exception {
        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();
        transaccion.setId(count.incrementAndGet());

        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransaccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transaccionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransaccion() throws Exception {
        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();
        transaccion.setId(count.incrementAndGet());

        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransaccion() throws Exception {
        int databaseSizeBeforeUpdate = transaccionRepository.findAll().size();
        transaccion.setId(count.incrementAndGet());

        // Create the Transaccion
        TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transaccionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transaccion in the database
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransaccion() throws Exception {
        // Initialize the database
        transaccionRepository.saveAndFlush(transaccion);

        int databaseSizeBeforeDelete = transaccionRepository.findAll().size();

        // Delete the transaccion
        restTransaccionMockMvc
            .perform(delete(ENTITY_API_URL_ID, transaccion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaccion> transaccionList = transaccionRepository.findAll();
        assertThat(transaccionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
