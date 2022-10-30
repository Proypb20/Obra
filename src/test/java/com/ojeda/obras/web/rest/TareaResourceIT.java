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
import com.ojeda.obras.domain.Tarea;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.TareaService;
import com.ojeda.obras.service.criteria.TareaCriteria;
import com.ojeda.obras.service.dto.TareaDTO;
import com.ojeda.obras.service.mapper.TareaMapper;
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
 * Integration tests for the {@link TareaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TareaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;
    private static final Double SMALLER_COST = 1D - 1D;

    private static final Double DEFAULT_ADVANCE_STATUS = 100D;
    private static final Double UPDATED_ADVANCE_STATUS = 99D;
    private static final Double SMALLER_ADVANCE_STATUS = 100D - 1D;

    private static final String ENTITY_API_URL = "/api/tareas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TareaRepository tareaRepository;

    @Mock
    private TareaRepository tareaRepositoryMock;

    @Autowired
    private TareaMapper tareaMapper;

    @Mock
    private TareaService tareaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTareaMockMvc;

    private Tarea tarea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarea createEntity(EntityManager em) {
        Tarea tarea = new Tarea().name(DEFAULT_NAME).quantity(DEFAULT_QUANTITY).cost(DEFAULT_COST).advanceStatus(DEFAULT_ADVANCE_STATUS);
        return tarea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarea createUpdatedEntity(EntityManager em) {
        Tarea tarea = new Tarea().name(UPDATED_NAME).quantity(UPDATED_QUANTITY).cost(UPDATED_COST).advanceStatus(UPDATED_ADVANCE_STATUS);
        return tarea;
    }

    @BeforeEach
    public void initTest() {
        tarea = createEntity(em);
    }

    @Test
    @Transactional
    void createTarea() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();
        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);
        restTareaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTarea.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTarea.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testTarea.getAdvanceStatus()).isEqualTo(DEFAULT_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void createTareaWithExistingId() throws Exception {
        // Create the Tarea with an existing ID
        tarea.setId(1L);
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTareaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tareaRepository.findAll().size();
        // set the field null
        tarea.setName(null);

        // Create the Tarea, which fails.
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        restTareaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTareas() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList
        restTareaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].advanceStatus").value(hasItem(DEFAULT_ADVANCE_STATUS.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTareasWithEagerRelationshipsIsEnabled() throws Exception {
        when(tareaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTareaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tareaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTareasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tareaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTareaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tareaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get the tarea
        restTareaMockMvc
            .perform(get(ENTITY_API_URL_ID, tarea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarea.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.advanceStatus").value(DEFAULT_ADVANCE_STATUS.doubleValue()));
    }

    @Test
    @Transactional
    void getTareasByIdFiltering() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        Long id = tarea.getId();

        defaultTareaShouldBeFound("id.equals=" + id);
        defaultTareaShouldNotBeFound("id.notEquals=" + id);

        defaultTareaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTareaShouldNotBeFound("id.greaterThan=" + id);

        defaultTareaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTareaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTareasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where name equals to DEFAULT_NAME
        defaultTareaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tareaList where name equals to UPDATED_NAME
        defaultTareaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTareasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTareaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tareaList where name equals to UPDATED_NAME
        defaultTareaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTareasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where name is not null
        defaultTareaShouldBeFound("name.specified=true");

        // Get all the tareaList where name is null
        defaultTareaShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTareasByNameContainsSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where name contains DEFAULT_NAME
        defaultTareaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tareaList where name contains UPDATED_NAME
        defaultTareaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTareasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where name does not contain DEFAULT_NAME
        defaultTareaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tareaList where name does not contain UPDATED_NAME
        defaultTareaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity equals to DEFAULT_QUANTITY
        defaultTareaShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the tareaList where quantity equals to UPDATED_QUANTITY
        defaultTareaShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultTareaShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the tareaList where quantity equals to UPDATED_QUANTITY
        defaultTareaShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity is not null
        defaultTareaShouldBeFound("quantity.specified=true");

        // Get all the tareaList where quantity is null
        defaultTareaShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultTareaShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the tareaList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultTareaShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultTareaShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the tareaList where quantity is less than or equal to SMALLER_QUANTITY
        defaultTareaShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity is less than DEFAULT_QUANTITY
        defaultTareaShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the tareaList where quantity is less than UPDATED_QUANTITY
        defaultTareaShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTareasByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where quantity is greater than DEFAULT_QUANTITY
        defaultTareaShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the tareaList where quantity is greater than SMALLER_QUANTITY
        defaultTareaShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTareasByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost equals to DEFAULT_COST
        defaultTareaShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the tareaList where cost equals to UPDATED_COST
        defaultTareaShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllTareasByCostIsInShouldWork() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost in DEFAULT_COST or UPDATED_COST
        defaultTareaShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the tareaList where cost equals to UPDATED_COST
        defaultTareaShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllTareasByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost is not null
        defaultTareaShouldBeFound("cost.specified=true");

        // Get all the tareaList where cost is null
        defaultTareaShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    void getAllTareasByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost is greater than or equal to DEFAULT_COST
        defaultTareaShouldBeFound("cost.greaterThanOrEqual=" + DEFAULT_COST);

        // Get all the tareaList where cost is greater than or equal to UPDATED_COST
        defaultTareaShouldNotBeFound("cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllTareasByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost is less than or equal to DEFAULT_COST
        defaultTareaShouldBeFound("cost.lessThanOrEqual=" + DEFAULT_COST);

        // Get all the tareaList where cost is less than or equal to SMALLER_COST
        defaultTareaShouldNotBeFound("cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllTareasByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost is less than DEFAULT_COST
        defaultTareaShouldNotBeFound("cost.lessThan=" + DEFAULT_COST);

        // Get all the tareaList where cost is less than UPDATED_COST
        defaultTareaShouldBeFound("cost.lessThan=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllTareasByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where cost is greater than DEFAULT_COST
        defaultTareaShouldNotBeFound("cost.greaterThan=" + DEFAULT_COST);

        // Get all the tareaList where cost is greater than SMALLER_COST
        defaultTareaShouldBeFound("cost.greaterThan=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus equals to DEFAULT_ADVANCE_STATUS
        defaultTareaShouldBeFound("advanceStatus.equals=" + DEFAULT_ADVANCE_STATUS);

        // Get all the tareaList where advanceStatus equals to UPDATED_ADVANCE_STATUS
        defaultTareaShouldNotBeFound("advanceStatus.equals=" + UPDATED_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus in DEFAULT_ADVANCE_STATUS or UPDATED_ADVANCE_STATUS
        defaultTareaShouldBeFound("advanceStatus.in=" + DEFAULT_ADVANCE_STATUS + "," + UPDATED_ADVANCE_STATUS);

        // Get all the tareaList where advanceStatus equals to UPDATED_ADVANCE_STATUS
        defaultTareaShouldNotBeFound("advanceStatus.in=" + UPDATED_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus is not null
        defaultTareaShouldBeFound("advanceStatus.specified=true");

        // Get all the tareaList where advanceStatus is null
        defaultTareaShouldNotBeFound("advanceStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus is greater than or equal to DEFAULT_ADVANCE_STATUS
        defaultTareaShouldBeFound("advanceStatus.greaterThanOrEqual=" + DEFAULT_ADVANCE_STATUS);

        // Get all the tareaList where advanceStatus is greater than or equal to (DEFAULT_ADVANCE_STATUS + 1)
        defaultTareaShouldNotBeFound("advanceStatus.greaterThanOrEqual=" + (DEFAULT_ADVANCE_STATUS + 1));
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus is less than or equal to DEFAULT_ADVANCE_STATUS
        defaultTareaShouldBeFound("advanceStatus.lessThanOrEqual=" + DEFAULT_ADVANCE_STATUS);

        // Get all the tareaList where advanceStatus is less than or equal to SMALLER_ADVANCE_STATUS
        defaultTareaShouldNotBeFound("advanceStatus.lessThanOrEqual=" + SMALLER_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus is less than DEFAULT_ADVANCE_STATUS
        defaultTareaShouldNotBeFound("advanceStatus.lessThan=" + DEFAULT_ADVANCE_STATUS);

        // Get all the tareaList where advanceStatus is less than (DEFAULT_ADVANCE_STATUS + 1)
        defaultTareaShouldBeFound("advanceStatus.lessThan=" + (DEFAULT_ADVANCE_STATUS + 1));
    }

    @Test
    @Transactional
    void getAllTareasByAdvanceStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where advanceStatus is greater than DEFAULT_ADVANCE_STATUS
        defaultTareaShouldNotBeFound("advanceStatus.greaterThan=" + DEFAULT_ADVANCE_STATUS);

        // Get all the tareaList where advanceStatus is greater than SMALLER_ADVANCE_STATUS
        defaultTareaShouldBeFound("advanceStatus.greaterThan=" + SMALLER_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void getAllTareasByObraIsEqualToSomething() throws Exception {
        Obra obra;
        if (TestUtil.findAll(em, Obra.class).isEmpty()) {
            tareaRepository.saveAndFlush(tarea);
            obra = ObraResourceIT.createEntity(em);
        } else {
            obra = TestUtil.findAll(em, Obra.class).get(0);
        }
        em.persist(obra);
        em.flush();
        tarea.setObra(obra);
        tareaRepository.saveAndFlush(tarea);
        Long obraId = obra.getId();

        // Get all the tareaList where obra equals to obraId
        defaultTareaShouldBeFound("obraId.equals=" + obraId);

        // Get all the tareaList where obra equals to (obraId + 1)
        defaultTareaShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    @Test
    @Transactional
    void getAllTareasBySubcontratistaIsEqualToSomething() throws Exception {
        Subcontratista subcontratista;
        if (TestUtil.findAll(em, Subcontratista.class).isEmpty()) {
            tareaRepository.saveAndFlush(tarea);
            subcontratista = SubcontratistaResourceIT.createEntity(em);
        } else {
            subcontratista = TestUtil.findAll(em, Subcontratista.class).get(0);
        }
        em.persist(subcontratista);
        em.flush();
        tarea.setSubcontratista(subcontratista);
        tareaRepository.saveAndFlush(tarea);
        Long subcontratistaId = subcontratista.getId();

        // Get all the tareaList where subcontratista equals to subcontratistaId
        defaultTareaShouldBeFound("subcontratistaId.equals=" + subcontratistaId);

        // Get all the tareaList where subcontratista equals to (subcontratistaId + 1)
        defaultTareaShouldNotBeFound("subcontratistaId.equals=" + (subcontratistaId + 1));
    }

    @Test
    @Transactional
    void getAllTareasByConceptoIsEqualToSomething() throws Exception {
        Concepto concepto;
        if (TestUtil.findAll(em, Concepto.class).isEmpty()) {
            tareaRepository.saveAndFlush(tarea);
            concepto = ConceptoResourceIT.createEntity(em);
        } else {
            concepto = TestUtil.findAll(em, Concepto.class).get(0);
        }
        em.persist(concepto);
        em.flush();
        tarea.setConcepto(concepto);
        tareaRepository.saveAndFlush(tarea);
        Long conceptoId = concepto.getId();

        // Get all the tareaList where concepto equals to conceptoId
        defaultTareaShouldBeFound("conceptoId.equals=" + conceptoId);

        // Get all the tareaList where concepto equals to (conceptoId + 1)
        defaultTareaShouldNotBeFound("conceptoId.equals=" + (conceptoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTareaShouldBeFound(String filter) throws Exception {
        restTareaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].advanceStatus").value(hasItem(DEFAULT_ADVANCE_STATUS.doubleValue())));

        // Check, that the count call also returns 1
        restTareaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTareaShouldNotBeFound(String filter) throws Exception {
        restTareaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTareaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTarea() throws Exception {
        // Get the tarea
        restTareaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Update the tarea
        Tarea updatedTarea = tareaRepository.findById(tarea.getId()).get();
        // Disconnect from session so that the updates on updatedTarea are not directly saved in db
        em.detach(updatedTarea);
        updatedTarea.name(UPDATED_NAME).quantity(UPDATED_QUANTITY).cost(UPDATED_COST).advanceStatus(UPDATED_ADVANCE_STATUS);
        TareaDTO tareaDTO = tareaMapper.toDto(updatedTarea);

        restTareaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tareaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tareaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTarea.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTarea.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testTarea.getAdvanceStatus()).isEqualTo(UPDATED_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();
        tarea.setId(count.incrementAndGet());

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTareaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tareaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tareaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();
        tarea.setId(count.incrementAndGet());

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTareaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tareaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();
        tarea.setId(count.incrementAndGet());

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTareaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTareaWithPatch() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Update the tarea using partial update
        Tarea partialUpdatedTarea = new Tarea();
        partialUpdatedTarea.setId(tarea.getId());

        partialUpdatedTarea.cost(UPDATED_COST).advanceStatus(UPDATED_ADVANCE_STATUS);

        restTareaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarea))
            )
            .andExpect(status().isOk());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTarea.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTarea.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testTarea.getAdvanceStatus()).isEqualTo(UPDATED_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTareaWithPatch() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Update the tarea using partial update
        Tarea partialUpdatedTarea = new Tarea();
        partialUpdatedTarea.setId(tarea.getId());

        partialUpdatedTarea.name(UPDATED_NAME).quantity(UPDATED_QUANTITY).cost(UPDATED_COST).advanceStatus(UPDATED_ADVANCE_STATUS);

        restTareaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarea))
            )
            .andExpect(status().isOk());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTarea.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTarea.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testTarea.getAdvanceStatus()).isEqualTo(UPDATED_ADVANCE_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();
        tarea.setId(count.incrementAndGet());

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTareaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tareaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tareaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();
        tarea.setId(count.incrementAndGet());

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTareaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tareaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();
        tarea.setId(count.incrementAndGet());

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTareaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeDelete = tareaRepository.findAll().size();

        // Delete the tarea
        restTareaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
