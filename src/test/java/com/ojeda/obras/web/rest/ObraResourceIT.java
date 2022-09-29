package com.ojeda.obras.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ojeda.obras.IntegrationTest;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.repository.ObraRepository;
import com.ojeda.obras.service.ObraService;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.mapper.ObraMapper;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ObraResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ObraResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/obras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObraRepository obraRepository;

    @Mock
    private ObraRepository obraRepositoryMock;

    @Autowired
    private ObraMapper obraMapper;

    @Mock
    private ObraService obraServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObraMockMvc;

    private Obra obra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obra createEntity(EntityManager em) {
        Obra obra = new Obra().name(DEFAULT_NAME).address(DEFAULT_ADDRESS).city(DEFAULT_CITY).comments(DEFAULT_COMMENTS);
        return obra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obra createUpdatedEntity(EntityManager em) {
        Obra obra = new Obra().name(UPDATED_NAME).address(UPDATED_ADDRESS).city(UPDATED_CITY).comments(UPDATED_COMMENTS);
        return obra;
    }

    @BeforeEach
    public void initTest() {
        obra = createEntity(em);
    }

    @Test
    @Transactional
    void createObra() throws Exception {
        int databaseSizeBeforeCreate = obraRepository.findAll().size();
        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);
        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isCreated());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate + 1);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObra.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testObra.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testObra.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createObraWithExistingId() throws Exception {
        // Create the Obra with an existing ID
        obra.setId(1L);
        ObraDTO obraDTO = obraMapper.toDto(obra);

        int databaseSizeBeforeCreate = obraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setName(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObras() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_COMMENTS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObrasWithEagerRelationshipsIsEnabled() throws Exception {
        when(obraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(obraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObrasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(obraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(obraRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get the obra
        restObraMockMvc
            .perform(get(ENTITY_API_URL_ID, obra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obra.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getObrasByIdFiltering() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        Long id = obra.getId();

        defaultObraShouldBeFound("id.equals=" + id);
        defaultObraShouldNotBeFound("id.notEquals=" + id);

        defaultObraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultObraShouldNotBeFound("id.greaterThan=" + id);

        defaultObraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultObraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllObrasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where name equals to DEFAULT_NAME
        defaultObraShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the obraList where name equals to UPDATED_NAME
        defaultObraShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllObrasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where name in DEFAULT_NAME or UPDATED_NAME
        defaultObraShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the obraList where name equals to UPDATED_NAME
        defaultObraShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllObrasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where name is not null
        defaultObraShouldBeFound("name.specified=true");

        // Get all the obraList where name is null
        defaultObraShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByNameContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where name contains DEFAULT_NAME
        defaultObraShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the obraList where name contains UPDATED_NAME
        defaultObraShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllObrasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where name does not contain DEFAULT_NAME
        defaultObraShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the obraList where name does not contain UPDATED_NAME
        defaultObraShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllObrasByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where address equals to DEFAULT_ADDRESS
        defaultObraShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the obraList where address equals to UPDATED_ADDRESS
        defaultObraShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllObrasByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultObraShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the obraList where address equals to UPDATED_ADDRESS
        defaultObraShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllObrasByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where address is not null
        defaultObraShouldBeFound("address.specified=true");

        // Get all the obraList where address is null
        defaultObraShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByAddressContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where address contains DEFAULT_ADDRESS
        defaultObraShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the obraList where address contains UPDATED_ADDRESS
        defaultObraShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllObrasByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where address does not contain DEFAULT_ADDRESS
        defaultObraShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the obraList where address does not contain UPDATED_ADDRESS
        defaultObraShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllObrasByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where city equals to DEFAULT_CITY
        defaultObraShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the obraList where city equals to UPDATED_CITY
        defaultObraShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllObrasByCityIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where city in DEFAULT_CITY or UPDATED_CITY
        defaultObraShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the obraList where city equals to UPDATED_CITY
        defaultObraShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllObrasByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where city is not null
        defaultObraShouldBeFound("city.specified=true");

        // Get all the obraList where city is null
        defaultObraShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByCityContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where city contains DEFAULT_CITY
        defaultObraShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the obraList where city contains UPDATED_CITY
        defaultObraShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllObrasByCityNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where city does not contain DEFAULT_CITY
        defaultObraShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the obraList where city does not contain UPDATED_CITY
        defaultObraShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllObrasByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where initialDate equals to DEFAULT_COMMENTS
        defaultObraShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the obraList where initialDate equals to UPDATED_COMMENTS
        defaultObraShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllObrasByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where initialDate in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultObraShouldBeFound("initialDate.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the obraList where initialDate equals to UPDATED_COMMENTS
        defaultObraShouldNotBeFound("initialDate.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllObrasByProvinciaIsEqualToSomething() throws Exception {
        Provincia provincia;
        if (TestUtil.findAll(em, Provincia.class).isEmpty()) {
            obraRepository.saveAndFlush(obra);
            provincia = ProvinciaResourceIT.createEntity(em);
        } else {
            provincia = TestUtil.findAll(em, Provincia.class).get(0);
        }
        em.persist(provincia);
        em.flush();
        obra.setProvincia(provincia);
        obraRepository.saveAndFlush(obra);
        Long provinciaId = provincia.getId();

        // Get all the obraList where provincia equals to provinciaId
        defaultObraShouldBeFound("provinciaId.equals=" + provinciaId);

        // Get all the obraList where provincia equals to (provinciaId + 1)
        defaultObraShouldNotBeFound("provinciaId.equals=" + (provinciaId + 1));
    }

    @Test
    @Transactional
    void getAllObrasBySubcontratistaIsEqualToSomething() throws Exception {
        Subcontratista subcontratista;
        if (TestUtil.findAll(em, Subcontratista.class).isEmpty()) {
            obraRepository.saveAndFlush(obra);
            subcontratista = SubcontratistaResourceIT.createEntity(em);
        } else {
            subcontratista = TestUtil.findAll(em, Subcontratista.class).get(0);
        }
        em.persist(subcontratista);
        em.flush();
        obra.addSubcontratista(subcontratista);
        obraRepository.saveAndFlush(obra);
        Long subcontratistaId = subcontratista.getId();

        // Get all the obraList where subcontratista equals to subcontratistaId
        defaultObraShouldBeFound("subcontratistaId.equals=" + subcontratistaId);

        // Get all the obraList where subcontratista equals to (subcontratistaId + 1)
        defaultObraShouldNotBeFound("subcontratistaId.equals=" + (subcontratistaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObraShouldBeFound(String filter) throws Exception {
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObraShouldNotBeFound(String filter) throws Exception {
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingObra() throws Exception {
        // Get the obra
        restObraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra
        Obra updatedObra = obraRepository.findById(obra.getId()).get();
        // Disconnect from session so that the updates on updatedObra are not directly saved in db
        em.detach(updatedObra);
        updatedObra.name(UPDATED_NAME).address(UPDATED_ADDRESS).city(UPDATED_CITY).comments(UPDATED_COMMENTS);
        ObraDTO obraDTO = obraMapper.toDto(updatedObra);

        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObra.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testObra.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testObra.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObraWithPatch() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra using partial update
        Obra partialUpdatedObra = new Obra();
        partialUpdatedObra.setId(obra.getId());

        partialUpdatedObra.city(UPDATED_CITY).comments(UPDATED_COMMENTS);

        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObra.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testObra.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testObra.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateObraWithPatch() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra using partial update
        Obra partialUpdatedObra = new Obra();
        partialUpdatedObra.setId(obra.getId());

        partialUpdatedObra.name(UPDATED_NAME).address(UPDATED_ADDRESS).city(UPDATED_CITY).comments(UPDATED_COMMENTS);

        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObra.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testObra.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testObra.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, obraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeDelete = obraRepository.findAll().size();

        // Delete the obra
        restObraMockMvc
            .perform(delete(ENTITY_API_URL_ID, obra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
