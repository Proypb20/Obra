package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.repository.SubcontratistaRepository;
import com.ojeda.obras.service.criteria.SubcontratistaCriteria;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import com.ojeda.obras.service.mapper.SubcontratistaMapper;
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
 * Integration tests for the {@link SubcontratistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubcontratistaResourceIT {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subcontratistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubcontratistaRepository subcontratistaRepository;

    @Autowired
    private SubcontratistaMapper subcontratistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubcontratistaMockMvc;

    private Subcontratista subcontratista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subcontratista createEntity(EntityManager em) {
        Subcontratista subcontratista = new Subcontratista()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL);
        return subcontratista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subcontratista createUpdatedEntity(EntityManager em) {
        Subcontratista subcontratista = new Subcontratista()
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);
        return subcontratista;
    }

    @BeforeEach
    public void initTest() {
        subcontratista = createEntity(em);
    }

    @Test
    @Transactional
    void createSubcontratista() throws Exception {
        int databaseSizeBeforeCreate = subcontratistaRepository.findAll().size();
        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);
        restSubcontratistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeCreate + 1);
        Subcontratista testSubcontratista = subcontratistaList.get(subcontratistaList.size() - 1);
        assertThat(testSubcontratista.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSubcontratista.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSubcontratista.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSubcontratista.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createSubcontratistaWithExistingId() throws Exception {
        // Create the Subcontratista with an existing ID
        subcontratista.setId(1L);
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        int databaseSizeBeforeCreate = subcontratistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubcontratistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontratistaRepository.findAll().size();
        // set the field null
        subcontratista.setLastName(null);

        // Create the Subcontratista, which fails.
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        restSubcontratistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontratistaRepository.findAll().size();
        // set the field null
        subcontratista.setFirstName(null);

        // Create the Subcontratista, which fails.
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        restSubcontratistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubcontratistas() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList
        restSubcontratistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subcontratista.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getSubcontratista() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get the subcontratista
        restSubcontratistaMockMvc
            .perform(get(ENTITY_API_URL_ID, subcontratista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subcontratista.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getSubcontratistasByIdFiltering() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        Long id = subcontratista.getId();

        defaultSubcontratistaShouldBeFound("id.equals=" + id);
        defaultSubcontratistaShouldNotBeFound("id.notEquals=" + id);

        defaultSubcontratistaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubcontratistaShouldNotBeFound("id.greaterThan=" + id);

        defaultSubcontratistaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubcontratistaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where lastName equals to DEFAULT_LAST_NAME
        defaultSubcontratistaShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the subcontratistaList where lastName equals to UPDATED_LAST_NAME
        defaultSubcontratistaShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultSubcontratistaShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the subcontratistaList where lastName equals to UPDATED_LAST_NAME
        defaultSubcontratistaShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where lastName is not null
        defaultSubcontratistaShouldBeFound("lastName.specified=true");

        // Get all the subcontratistaList where lastName is null
        defaultSubcontratistaShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllSubcontratistasByLastNameContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where lastName contains DEFAULT_LAST_NAME
        defaultSubcontratistaShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the subcontratistaList where lastName contains UPDATED_LAST_NAME
        defaultSubcontratistaShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where lastName does not contain DEFAULT_LAST_NAME
        defaultSubcontratistaShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the subcontratistaList where lastName does not contain UPDATED_LAST_NAME
        defaultSubcontratistaShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where firstName equals to DEFAULT_FIRST_NAME
        defaultSubcontratistaShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the subcontratistaList where firstName equals to UPDATED_FIRST_NAME
        defaultSubcontratistaShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultSubcontratistaShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the subcontratistaList where firstName equals to UPDATED_FIRST_NAME
        defaultSubcontratistaShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where firstName is not null
        defaultSubcontratistaShouldBeFound("firstName.specified=true");

        // Get all the subcontratistaList where firstName is null
        defaultSubcontratistaShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllSubcontratistasByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where firstName contains DEFAULT_FIRST_NAME
        defaultSubcontratistaShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the subcontratistaList where firstName contains UPDATED_FIRST_NAME
        defaultSubcontratistaShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where firstName does not contain DEFAULT_FIRST_NAME
        defaultSubcontratistaShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the subcontratistaList where firstName does not contain UPDATED_FIRST_NAME
        defaultSubcontratistaShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where phone equals to DEFAULT_PHONE
        defaultSubcontratistaShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the subcontratistaList where phone equals to UPDATED_PHONE
        defaultSubcontratistaShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultSubcontratistaShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the subcontratistaList where phone equals to UPDATED_PHONE
        defaultSubcontratistaShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where phone is not null
        defaultSubcontratistaShouldBeFound("phone.specified=true");

        // Get all the subcontratistaList where phone is null
        defaultSubcontratistaShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllSubcontratistasByPhoneContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where phone contains DEFAULT_PHONE
        defaultSubcontratistaShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the subcontratistaList where phone contains UPDATED_PHONE
        defaultSubcontratistaShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where phone does not contain DEFAULT_PHONE
        defaultSubcontratistaShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the subcontratistaList where phone does not contain UPDATED_PHONE
        defaultSubcontratistaShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where email equals to DEFAULT_EMAIL
        defaultSubcontratistaShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the subcontratistaList where email equals to UPDATED_EMAIL
        defaultSubcontratistaShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSubcontratistaShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the subcontratistaList where email equals to UPDATED_EMAIL
        defaultSubcontratistaShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where email is not null
        defaultSubcontratistaShouldBeFound("email.specified=true");

        // Get all the subcontratistaList where email is null
        defaultSubcontratistaShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllSubcontratistasByEmailContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where email contains DEFAULT_EMAIL
        defaultSubcontratistaShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the subcontratistaList where email contains UPDATED_EMAIL
        defaultSubcontratistaShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        // Get all the subcontratistaList where email does not contain DEFAULT_EMAIL
        defaultSubcontratistaShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the subcontratistaList where email does not contain UPDATED_EMAIL
        defaultSubcontratistaShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSubcontratistasByObraIsEqualToSomething() throws Exception {
        Obra obra;
        if (TestUtil.findAll(em, Obra.class).isEmpty()) {
            subcontratistaRepository.saveAndFlush(subcontratista);
            obra = ObraResourceIT.createEntity(em);
        } else {
            obra = TestUtil.findAll(em, Obra.class).get(0);
        }
        em.persist(obra);
        em.flush();
        subcontratista.addObra(obra);
        subcontratistaRepository.saveAndFlush(subcontratista);
        Long obraId = obra.getId();

        // Get all the subcontratistaList where obra equals to obraId
        defaultSubcontratistaShouldBeFound("obraId.equals=" + obraId);

        // Get all the subcontratistaList where obra equals to (obraId + 1)
        defaultSubcontratistaShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubcontratistaShouldBeFound(String filter) throws Exception {
        restSubcontratistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subcontratista.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restSubcontratistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubcontratistaShouldNotBeFound(String filter) throws Exception {
        restSubcontratistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubcontratistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubcontratista() throws Exception {
        // Get the subcontratista
        restSubcontratistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubcontratista() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();

        // Update the subcontratista
        Subcontratista updatedSubcontratista = subcontratistaRepository.findById(subcontratista.getId()).get();
        // Disconnect from session so that the updates on updatedSubcontratista are not directly saved in db
        em.detach(updatedSubcontratista);
        updatedSubcontratista.lastName(UPDATED_LAST_NAME).firstName(UPDATED_FIRST_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL);
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(updatedSubcontratista);

        restSubcontratistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subcontratistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
        Subcontratista testSubcontratista = subcontratistaList.get(subcontratistaList.size() - 1);
        assertThat(testSubcontratista.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSubcontratista.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSubcontratista.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSubcontratista.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingSubcontratista() throws Exception {
        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();
        subcontratista.setId(count.incrementAndGet());

        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubcontratistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subcontratistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubcontratista() throws Exception {
        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();
        subcontratista.setId(count.incrementAndGet());

        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubcontratistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubcontratista() throws Exception {
        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();
        subcontratista.setId(count.incrementAndGet());

        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubcontratistaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubcontratistaWithPatch() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();

        // Update the subcontratista using partial update
        Subcontratista partialUpdatedSubcontratista = new Subcontratista();
        partialUpdatedSubcontratista.setId(subcontratista.getId());

        restSubcontratistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubcontratista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubcontratista))
            )
            .andExpect(status().isOk());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
        Subcontratista testSubcontratista = subcontratistaList.get(subcontratistaList.size() - 1);
        assertThat(testSubcontratista.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSubcontratista.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSubcontratista.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSubcontratista.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateSubcontratistaWithPatch() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();

        // Update the subcontratista using partial update
        Subcontratista partialUpdatedSubcontratista = new Subcontratista();
        partialUpdatedSubcontratista.setId(subcontratista.getId());

        partialUpdatedSubcontratista.lastName(UPDATED_LAST_NAME).firstName(UPDATED_FIRST_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL);

        restSubcontratistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubcontratista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubcontratista))
            )
            .andExpect(status().isOk());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
        Subcontratista testSubcontratista = subcontratistaList.get(subcontratistaList.size() - 1);
        assertThat(testSubcontratista.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSubcontratista.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSubcontratista.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSubcontratista.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingSubcontratista() throws Exception {
        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();
        subcontratista.setId(count.incrementAndGet());

        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubcontratistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subcontratistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubcontratista() throws Exception {
        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();
        subcontratista.setId(count.incrementAndGet());

        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubcontratistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubcontratista() throws Exception {
        int databaseSizeBeforeUpdate = subcontratistaRepository.findAll().size();
        subcontratista.setId(count.incrementAndGet());

        // Create the Subcontratista
        SubcontratistaDTO subcontratistaDTO = subcontratistaMapper.toDto(subcontratista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubcontratistaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subcontratistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subcontratista in the database
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubcontratista() throws Exception {
        // Initialize the database
        subcontratistaRepository.saveAndFlush(subcontratista);

        int databaseSizeBeforeDelete = subcontratistaRepository.findAll().size();

        // Delete the subcontratista
        restSubcontratistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, subcontratista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subcontratista> subcontratistaList = subcontratistaRepository.findAll();
        assertThat(subcontratistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
