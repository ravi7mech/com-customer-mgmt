package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndAuditTrial;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndAuditTrialRepository;
import com.apptium.customer.service.criteria.IndAuditTrialCriteria;
import com.apptium.customer.service.dto.IndAuditTrialDTO;
import com.apptium.customer.service.mapper.IndAuditTrialMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link IndAuditTrialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndAuditTrialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ind-audit-trials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndAuditTrialRepository indAuditTrialRepository;

    @Autowired
    private IndAuditTrialMapper indAuditTrialMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndAuditTrialMockMvc;

    private IndAuditTrial indAuditTrial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndAuditTrial createEntity(EntityManager em) {
        IndAuditTrial indAuditTrial = new IndAuditTrial().name(DEFAULT_NAME).activity(DEFAULT_ACTIVITY).createdDate(DEFAULT_CREATED_DATE);
        return indAuditTrial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndAuditTrial createUpdatedEntity(EntityManager em) {
        IndAuditTrial indAuditTrial = new IndAuditTrial().name(UPDATED_NAME).activity(UPDATED_ACTIVITY).createdDate(UPDATED_CREATED_DATE);
        return indAuditTrial;
    }

    @BeforeEach
    public void initTest() {
        indAuditTrial = createEntity(em);
    }

    @Test
    @Transactional
    void createIndAuditTrial() throws Exception {
        int databaseSizeBeforeCreate = indAuditTrialRepository.findAll().size();
        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);
        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeCreate + 1);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createIndAuditTrialWithExistingId() throws Exception {
        // Create the IndAuditTrial with an existing ID
        indAuditTrial.setId(1L);
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        int databaseSizeBeforeCreate = indAuditTrialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setName(null);

        // Create the IndAuditTrial, which fails.
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setActivity(null);

        // Create the IndAuditTrial, which fails.
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndAuditTrials() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indAuditTrial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get the indAuditTrial
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL_ID, indAuditTrial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indAuditTrial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getIndAuditTrialsByIdFiltering() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        Long id = indAuditTrial.getId();

        defaultIndAuditTrialShouldBeFound("id.equals=" + id);
        defaultIndAuditTrialShouldNotBeFound("id.notEquals=" + id);

        defaultIndAuditTrialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndAuditTrialShouldNotBeFound("id.greaterThan=" + id);

        defaultIndAuditTrialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndAuditTrialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name equals to DEFAULT_NAME
        defaultIndAuditTrialShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name equals to UPDATED_NAME
        defaultIndAuditTrialShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name not equals to DEFAULT_NAME
        defaultIndAuditTrialShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name not equals to UPDATED_NAME
        defaultIndAuditTrialShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIndAuditTrialShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the indAuditTrialList where name equals to UPDATED_NAME
        defaultIndAuditTrialShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name is not null
        defaultIndAuditTrialShouldBeFound("name.specified=true");

        // Get all the indAuditTrialList where name is null
        defaultIndAuditTrialShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name contains DEFAULT_NAME
        defaultIndAuditTrialShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name contains UPDATED_NAME
        defaultIndAuditTrialShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name does not contain DEFAULT_NAME
        defaultIndAuditTrialShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name does not contain UPDATED_NAME
        defaultIndAuditTrialShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity equals to DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.equals=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity equals to UPDATED_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.equals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity not equals to DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.notEquals=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity not equals to UPDATED_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.notEquals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity in DEFAULT_ACTIVITY or UPDATED_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.in=" + DEFAULT_ACTIVITY + "," + UPDATED_ACTIVITY);

        // Get all the indAuditTrialList where activity equals to UPDATED_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.in=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity is not null
        defaultIndAuditTrialShouldBeFound("activity.specified=true");

        // Get all the indAuditTrialList where activity is null
        defaultIndAuditTrialShouldNotBeFound("activity.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity contains DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.contains=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity contains UPDATED_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.contains=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityNotContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity does not contain DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.doesNotContain=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity does not contain UPDATED_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.doesNotContain=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where createdDate equals to DEFAULT_CREATED_DATE
        defaultIndAuditTrialShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the indAuditTrialList where createdDate equals to UPDATED_CREATED_DATE
        defaultIndAuditTrialShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultIndAuditTrialShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the indAuditTrialList where createdDate not equals to UPDATED_CREATED_DATE
        defaultIndAuditTrialShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultIndAuditTrialShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the indAuditTrialList where createdDate equals to UPDATED_CREATED_DATE
        defaultIndAuditTrialShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where createdDate is not null
        defaultIndAuditTrialShouldBeFound("createdDate.specified=true");

        // Get all the indAuditTrialList where createdDate is null
        defaultIndAuditTrialShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        indAuditTrial.setIndividual(individual);
        indAuditTrialRepository.saveAndFlush(indAuditTrial);
        Long individualId = individual.getId();

        // Get all the indAuditTrialList where individual equals to individualId
        defaultIndAuditTrialShouldBeFound("individualId.equals=" + individualId);

        // Get all the indAuditTrialList where individual equals to (individualId + 1)
        defaultIndAuditTrialShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndAuditTrialShouldBeFound(String filter) throws Exception {
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indAuditTrial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndAuditTrialShouldNotBeFound(String filter) throws Exception {
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndAuditTrial() throws Exception {
        // Get the indAuditTrial
        restIndAuditTrialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial
        IndAuditTrial updatedIndAuditTrial = indAuditTrialRepository.findById(indAuditTrial.getId()).get();
        // Disconnect from session so that the updates on updatedIndAuditTrial are not directly saved in db
        em.detach(updatedIndAuditTrial);
        updatedIndAuditTrial.name(UPDATED_NAME).activity(UPDATED_ACTIVITY).createdDate(UPDATED_CREATED_DATE);
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(updatedIndAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indAuditTrialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indAuditTrialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndAuditTrialWithPatch() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial using partial update
        IndAuditTrial partialUpdatedIndAuditTrial = new IndAuditTrial();
        partialUpdatedIndAuditTrial.setId(indAuditTrial.getId());

        partialUpdatedIndAuditTrial.activity(UPDATED_ACTIVITY);

        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateIndAuditTrialWithPatch() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial using partial update
        IndAuditTrial partialUpdatedIndAuditTrial = new IndAuditTrial();
        partialUpdatedIndAuditTrial.setId(indAuditTrial.getId());

        partialUpdatedIndAuditTrial.name(UPDATED_NAME).activity(UPDATED_ACTIVITY).createdDate(UPDATED_CREATED_DATE);

        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indAuditTrialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeDelete = indAuditTrialRepository.findAll().size();

        // Delete the indAuditTrial
        restIndAuditTrialMockMvc
            .perform(delete(ENTITY_API_URL_ID, indAuditTrial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
