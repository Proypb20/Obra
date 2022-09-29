package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.repository.ListaPrecioRepository;
import com.ojeda.obras.service.ListaPrecioService;
import com.ojeda.obras.service.criteria.ListaPrecioCriteria;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
import com.ojeda.obras.service.mapper.ListaPrecioMapper;
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
 * Integration tests for the {@link ListaPrecioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ListaPrecioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/lista-precios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListaPrecioRepository listaPrecioRepository;

    @Mock
    private ListaPrecioRepository listaPrecioRepositoryMock;

    @Autowired
    private ListaPrecioMapper listaPrecioMapper;

    @Mock
    private ListaPrecioService listaPrecioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListaPrecioMockMvc;

    private ListaPrecio listaPrecio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaPrecio createEntity(EntityManager em) {
        ListaPrecio listaPrecio = new ListaPrecio().name(DEFAULT_NAME).date(DEFAULT_DATE);
        return listaPrecio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaPrecio createUpdatedEntity(EntityManager em) {
        ListaPrecio listaPrecio = new ListaPrecio().name(UPDATED_NAME).date(UPDATED_DATE);
        return listaPrecio;
    }

    @BeforeEach
    public void initTest() {
        listaPrecio = createEntity(em);
    }

    @Test
    @Transactional
    void createListaPrecio() throws Exception {
        int databaseSizeBeforeCreate = listaPrecioRepository.findAll().size();
        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);
        restListaPrecioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeCreate + 1);
        ListaPrecio testListaPrecio = listaPrecioList.get(listaPrecioList.size() - 1);
        assertThat(testListaPrecio.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testListaPrecio.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createListaPrecioWithExistingId() throws Exception {
        // Create the ListaPrecio with an existing ID
        listaPrecio.setId(1L);
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        int databaseSizeBeforeCreate = listaPrecioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaPrecioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = listaPrecioRepository.findAll().size();
        // set the field null
        listaPrecio.setName(null);

        // Create the ListaPrecio, which fails.
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        restListaPrecioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllListaPrecios() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList
        restListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaPrecio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllListaPreciosWithEagerRelationshipsIsEnabled() throws Exception {
        when(listaPrecioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restListaPrecioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(listaPrecioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllListaPreciosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(listaPrecioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restListaPrecioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(listaPrecioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get the listaPrecio
        restListaPrecioMockMvc
            .perform(get(ENTITY_API_URL_ID, listaPrecio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listaPrecio.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getListaPreciosByIdFiltering() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        Long id = listaPrecio.getId();

        defaultListaPrecioShouldBeFound("id.equals=" + id);
        defaultListaPrecioShouldNotBeFound("id.notEquals=" + id);

        defaultListaPrecioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultListaPrecioShouldNotBeFound("id.greaterThan=" + id);

        defaultListaPrecioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultListaPrecioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllListaPreciosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where name equals to DEFAULT_NAME
        defaultListaPrecioShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the listaPrecioList where name equals to UPDATED_NAME
        defaultListaPrecioShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllListaPreciosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where name in DEFAULT_NAME or UPDATED_NAME
        defaultListaPrecioShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the listaPrecioList where name equals to UPDATED_NAME
        defaultListaPrecioShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllListaPreciosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where name is not null
        defaultListaPrecioShouldBeFound("name.specified=true");

        // Get all the listaPrecioList where name is null
        defaultListaPrecioShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllListaPreciosByNameContainsSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where name contains DEFAULT_NAME
        defaultListaPrecioShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the listaPrecioList where name contains UPDATED_NAME
        defaultListaPrecioShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllListaPreciosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where name does not contain DEFAULT_NAME
        defaultListaPrecioShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the listaPrecioList where name does not contain UPDATED_NAME
        defaultListaPrecioShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date equals to DEFAULT_DATE
        defaultListaPrecioShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the listaPrecioList where date equals to UPDATED_DATE
        defaultListaPrecioShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsInShouldWork() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date in DEFAULT_DATE or UPDATED_DATE
        defaultListaPrecioShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the listaPrecioList where date equals to UPDATED_DATE
        defaultListaPrecioShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date is not null
        defaultListaPrecioShouldBeFound("date.specified=true");

        // Get all the listaPrecioList where date is null
        defaultListaPrecioShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date is greater than or equal to DEFAULT_DATE
        defaultListaPrecioShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the listaPrecioList where date is greater than or equal to UPDATED_DATE
        defaultListaPrecioShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date is less than or equal to DEFAULT_DATE
        defaultListaPrecioShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the listaPrecioList where date is less than or equal to SMALLER_DATE
        defaultListaPrecioShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date is less than DEFAULT_DATE
        defaultListaPrecioShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the listaPrecioList where date is less than UPDATED_DATE
        defaultListaPrecioShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllListaPreciosByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList where date is greater than DEFAULT_DATE
        defaultListaPrecioShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the listaPrecioList where date is greater than SMALLER_DATE
        defaultListaPrecioShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllListaPreciosByProveedorIsEqualToSomething() throws Exception {
        Proveedor proveedor;
        if (TestUtil.findAll(em, Proveedor.class).isEmpty()) {
            listaPrecioRepository.saveAndFlush(listaPrecio);
            proveedor = ProveedorResourceIT.createEntity(em);
        } else {
            proveedor = TestUtil.findAll(em, Proveedor.class).get(0);
        }
        em.persist(proveedor);
        em.flush();
        listaPrecio.setProveedor(proveedor);
        listaPrecioRepository.saveAndFlush(listaPrecio);
        Long proveedorId = proveedor.getId();

        // Get all the listaPrecioList where proveedor equals to proveedorId
        defaultListaPrecioShouldBeFound("proveedorId.equals=" + proveedorId);

        // Get all the listaPrecioList where proveedor equals to (proveedorId + 1)
        defaultListaPrecioShouldNotBeFound("proveedorId.equals=" + (proveedorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultListaPrecioShouldBeFound(String filter) throws Exception {
        restListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaPrecio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultListaPrecioShouldNotBeFound(String filter) throws Exception {
        restListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restListaPrecioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingListaPrecio() throws Exception {
        // Get the listaPrecio
        restListaPrecioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();

        // Update the listaPrecio
        ListaPrecio updatedListaPrecio = listaPrecioRepository.findById(listaPrecio.getId()).get();
        // Disconnect from session so that the updates on updatedListaPrecio are not directly saved in db
        em.detach(updatedListaPrecio);
        updatedListaPrecio.name(UPDATED_NAME).date(UPDATED_DATE);
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(updatedListaPrecio);

        restListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listaPrecioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isOk());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
        ListaPrecio testListaPrecio = listaPrecioList.get(listaPrecioList.size() - 1);
        assertThat(testListaPrecio.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testListaPrecio.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();
        listaPrecio.setId(count.incrementAndGet());

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listaPrecioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();
        listaPrecio.setId(count.incrementAndGet());

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();
        listaPrecio.setId(count.incrementAndGet());

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListaPrecioWithPatch() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();

        // Update the listaPrecio using partial update
        ListaPrecio partialUpdatedListaPrecio = new ListaPrecio();
        partialUpdatedListaPrecio.setId(listaPrecio.getId());

        partialUpdatedListaPrecio.name(UPDATED_NAME).date(UPDATED_DATE);

        restListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListaPrecio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListaPrecio))
            )
            .andExpect(status().isOk());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
        ListaPrecio testListaPrecio = listaPrecioList.get(listaPrecioList.size() - 1);
        assertThat(testListaPrecio.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testListaPrecio.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateListaPrecioWithPatch() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();

        // Update the listaPrecio using partial update
        ListaPrecio partialUpdatedListaPrecio = new ListaPrecio();
        partialUpdatedListaPrecio.setId(listaPrecio.getId());

        partialUpdatedListaPrecio.name(UPDATED_NAME).date(UPDATED_DATE);

        restListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListaPrecio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListaPrecio))
            )
            .andExpect(status().isOk());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
        ListaPrecio testListaPrecio = listaPrecioList.get(listaPrecioList.size() - 1);
        assertThat(testListaPrecio.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testListaPrecio.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();
        listaPrecio.setId(count.incrementAndGet());

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listaPrecioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();
        listaPrecio.setId(count.incrementAndGet());

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();
        listaPrecio.setId(count.incrementAndGet());

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        int databaseSizeBeforeDelete = listaPrecioRepository.findAll().size();

        // Delete the listaPrecio
        restListaPrecioMockMvc
            .perform(delete(ENTITY_API_URL_ID, listaPrecio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
