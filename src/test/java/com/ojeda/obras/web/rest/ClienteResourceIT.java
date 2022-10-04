package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Cliente;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.repository.ClienteRepository;
import com.ojeda.obras.service.ClienteService;
import com.ojeda.obras.service.criteria.ClienteCriteria;
import com.ojeda.obras.service.dto.ClienteDTO;
import com.ojeda.obras.service.mapper.ClienteMapper;
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
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClienteResourceIT {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAXPAYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAXPAYER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @Autowired
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteService clienteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .taxpayerID(DEFAULT_TAXPAYER_ID)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY);
        return cliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .taxpayerID(UPDATED_TAXPAYER_ID)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();
        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCliente.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCliente.getTaxpayerID()).isEqualTo(DEFAULT_TAXPAYER_ID);
        assertThat(testCliente.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCliente.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    void createClienteWithExistingId() throws Exception {
        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].taxpayerID").value(hasItem(DEFAULT_TAXPAYER_ID)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientesWithEagerRelationshipsIsEnabled() throws Exception {
        when(clienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(clienteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.taxpayerID").value(DEFAULT_TAXPAYER_ID))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY));
    }

    @Test
    @Transactional
    void getClientesByIdFiltering() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        Long id = cliente.getId();

        defaultClienteShouldBeFound("id.equals=" + id);
        defaultClienteShouldNotBeFound("id.notEquals=" + id);

        defaultClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClientesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where lastName equals to DEFAULT_LAST_NAME
        defaultClienteShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the clienteList where lastName equals to UPDATED_LAST_NAME
        defaultClienteShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultClienteShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the clienteList where lastName equals to UPDATED_LAST_NAME
        defaultClienteShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where lastName is not null
        defaultClienteShouldBeFound("lastName.specified=true");

        // Get all the clienteList where lastName is null
        defaultClienteShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where lastName contains DEFAULT_LAST_NAME
        defaultClienteShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the clienteList where lastName contains UPDATED_LAST_NAME
        defaultClienteShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where lastName does not contain DEFAULT_LAST_NAME
        defaultClienteShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the clienteList where lastName does not contain UPDATED_LAST_NAME
        defaultClienteShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where firstName equals to DEFAULT_FIRST_NAME
        defaultClienteShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the clienteList where firstName equals to UPDATED_FIRST_NAME
        defaultClienteShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultClienteShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the clienteList where firstName equals to UPDATED_FIRST_NAME
        defaultClienteShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where firstName is not null
        defaultClienteShouldBeFound("firstName.specified=true");

        // Get all the clienteList where firstName is null
        defaultClienteShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where firstName contains DEFAULT_FIRST_NAME
        defaultClienteShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the clienteList where firstName contains UPDATED_FIRST_NAME
        defaultClienteShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where firstName does not contain DEFAULT_FIRST_NAME
        defaultClienteShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the clienteList where firstName does not contain UPDATED_FIRST_NAME
        defaultClienteShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllClientesByTaxpayerIDIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where taxpayerID equals to DEFAULT_TAXPAYER_ID
        defaultClienteShouldBeFound("taxpayerID.equals=" + DEFAULT_TAXPAYER_ID);

        // Get all the clienteList where taxpayerID equals to UPDATED_TAXPAYER_ID
        defaultClienteShouldNotBeFound("taxpayerID.equals=" + UPDATED_TAXPAYER_ID);
    }

    @Test
    @Transactional
    void getAllClientesByTaxpayerIDIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where taxpayerID in DEFAULT_TAXPAYER_ID or UPDATED_TAXPAYER_ID
        defaultClienteShouldBeFound("taxpayerID.in=" + DEFAULT_TAXPAYER_ID + "," + UPDATED_TAXPAYER_ID);

        // Get all the clienteList where taxpayerID equals to UPDATED_TAXPAYER_ID
        defaultClienteShouldNotBeFound("taxpayerID.in=" + UPDATED_TAXPAYER_ID);
    }

    @Test
    @Transactional
    void getAllClientesByTaxpayerIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where taxpayerID is not null
        defaultClienteShouldBeFound("taxpayerID.specified=true");

        // Get all the clienteList where taxpayerID is null
        defaultClienteShouldNotBeFound("taxpayerID.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByTaxpayerIDContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where taxpayerID contains DEFAULT_TAXPAYER_ID
        defaultClienteShouldBeFound("taxpayerID.contains=" + DEFAULT_TAXPAYER_ID);

        // Get all the clienteList where taxpayerID contains UPDATED_TAXPAYER_ID
        defaultClienteShouldNotBeFound("taxpayerID.contains=" + UPDATED_TAXPAYER_ID);
    }

    @Test
    @Transactional
    void getAllClientesByTaxpayerIDNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where taxpayerID does not contain DEFAULT_TAXPAYER_ID
        defaultClienteShouldNotBeFound("taxpayerID.doesNotContain=" + DEFAULT_TAXPAYER_ID);

        // Get all the clienteList where taxpayerID does not contain UPDATED_TAXPAYER_ID
        defaultClienteShouldBeFound("taxpayerID.doesNotContain=" + UPDATED_TAXPAYER_ID);
    }

    @Test
    @Transactional
    void getAllClientesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where address equals to DEFAULT_ADDRESS
        defaultClienteShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the clienteList where address equals to UPDATED_ADDRESS
        defaultClienteShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultClienteShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the clienteList where address equals to UPDATED_ADDRESS
        defaultClienteShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where address is not null
        defaultClienteShouldBeFound("address.specified=true");

        // Get all the clienteList where address is null
        defaultClienteShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByAddressContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where address contains DEFAULT_ADDRESS
        defaultClienteShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the clienteList where address contains UPDATED_ADDRESS
        defaultClienteShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where address does not contain DEFAULT_ADDRESS
        defaultClienteShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the clienteList where address does not contain UPDATED_ADDRESS
        defaultClienteShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where city equals to DEFAULT_CITY
        defaultClienteShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the clienteList where city equals to UPDATED_CITY
        defaultClienteShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllClientesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where city in DEFAULT_CITY or UPDATED_CITY
        defaultClienteShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the clienteList where city equals to UPDATED_CITY
        defaultClienteShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllClientesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where city is not null
        defaultClienteShouldBeFound("city.specified=true");

        // Get all the clienteList where city is null
        defaultClienteShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByCityContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where city contains DEFAULT_CITY
        defaultClienteShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the clienteList where city contains UPDATED_CITY
        defaultClienteShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllClientesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where city does not contain DEFAULT_CITY
        defaultClienteShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the clienteList where city does not contain UPDATED_CITY
        defaultClienteShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllClientesByProvinciaIsEqualToSomething() throws Exception {
        Provincia provincia;
        if (TestUtil.findAll(em, Provincia.class).isEmpty()) {
            clienteRepository.saveAndFlush(cliente);
            provincia = ProvinciaResourceIT.createEntity(em);
        } else {
            provincia = TestUtil.findAll(em, Provincia.class).get(0);
        }
        em.persist(provincia);
        em.flush();
        cliente.setProvincia(provincia);
        clienteRepository.saveAndFlush(cliente);
        Long provinciaId = provincia.getId();

        // Get all the clienteList where provincia equals to provinciaId
        defaultClienteShouldBeFound("provinciaId.equals=" + provinciaId);

        // Get all the clienteList where provincia equals to (provinciaId + 1)
        defaultClienteShouldNotBeFound("provinciaId.equals=" + (provinciaId + 1));
    }

    @Test
    @Transactional
    void getAllClientesByObraIsEqualToSomething() throws Exception {
        Obra obra;
        if (TestUtil.findAll(em, Obra.class).isEmpty()) {
            clienteRepository.saveAndFlush(cliente);
            obra = ObraResourceIT.createEntity(em);
        } else {
            obra = TestUtil.findAll(em, Obra.class).get(0);
        }
        em.persist(obra);
        em.flush();
        cliente.addObra(obra);
        clienteRepository.saveAndFlush(cliente);
        Long obraId = obra.getId();

        // Get all the clienteList where obra equals to obraId
        defaultClienteShouldBeFound("obraId.equals=" + obraId);

        // Get all the clienteList where obra equals to (obraId + 1)
        defaultClienteShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].taxpayerID").value(hasItem(DEFAULT_TAXPAYER_ID)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));

        // Check, that the count call also returns 1
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .taxpayerID(UPDATED_TAXPAYER_ID)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCliente.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCliente.getTaxpayerID()).isEqualTo(UPDATED_TAXPAYER_ID);
        assertThat(testCliente.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCliente.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void putNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente.taxpayerID(UPDATED_TAXPAYER_ID);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCliente.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCliente.getTaxpayerID()).isEqualTo(UPDATED_TAXPAYER_ID);
        assertThat(testCliente.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCliente.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    void fullUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .taxpayerID(UPDATED_TAXPAYER_ID)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCliente.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCliente.getTaxpayerID()).isEqualTo(UPDATED_TAXPAYER_ID);
        assertThat(testCliente.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCliente.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void patchNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, cliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
