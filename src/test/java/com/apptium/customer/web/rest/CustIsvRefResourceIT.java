package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustIsvChar;
import com.apptium.customer.domain.CustIsvRef;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustIsvRefRepository;
import com.apptium.customer.service.criteria.CustIsvRefCriteria;
import com.apptium.customer.service.dto.CustIsvRefDTO;
import com.apptium.customer.service.mapper.CustIsvRefMapper;
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
 * Integration tests for the {@link CustIsvRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustIsvRefResourceIT {

    private static final Integer DEFAULT_ISV_ID = 1;
    private static final Integer UPDATED_ISV_ID = 2;
    private static final Integer SMALLER_ISV_ID = 1 - 1;

    private static final String DEFAULT_ISV_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ISV_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ISV_CUST_ID = 1L;
    private static final Long UPDATED_ISV_CUST_ID = 2L;
    private static final Long SMALLER_ISV_CUST_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-isv-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustIsvRefRepository custIsvRefRepository;

    @Autowired
    private CustIsvRefMapper custIsvRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustIsvRefMockMvc;

    private CustIsvRef custIsvRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustIsvRef createEntity(EntityManager em) {
        CustIsvRef custIsvRef = new CustIsvRef().isvId(DEFAULT_ISV_ID).isvName(DEFAULT_ISV_NAME).isvCustId(DEFAULT_ISV_CUST_ID);
        return custIsvRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustIsvRef createUpdatedEntity(EntityManager em) {
        CustIsvRef custIsvRef = new CustIsvRef().isvId(UPDATED_ISV_ID).isvName(UPDATED_ISV_NAME).isvCustId(UPDATED_ISV_CUST_ID);
        return custIsvRef;
    }

    @BeforeEach
    public void initTest() {
        custIsvRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustIsvRef() throws Exception {
        int databaseSizeBeforeCreate = custIsvRefRepository.findAll().size();
        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);
        restCustIsvRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO)))
            .andExpect(status().isCreated());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustIsvRef testCustIsvRef = custIsvRefList.get(custIsvRefList.size() - 1);
        assertThat(testCustIsvRef.getIsvId()).isEqualTo(DEFAULT_ISV_ID);
        assertThat(testCustIsvRef.getIsvName()).isEqualTo(DEFAULT_ISV_NAME);
        assertThat(testCustIsvRef.getIsvCustId()).isEqualTo(DEFAULT_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void createCustIsvRefWithExistingId() throws Exception {
        // Create the CustIsvRef with an existing ID
        custIsvRef.setId(1L);
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        int databaseSizeBeforeCreate = custIsvRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustIsvRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsvIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custIsvRefRepository.findAll().size();
        // set the field null
        custIsvRef.setIsvId(null);

        // Create the CustIsvRef, which fails.
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        restCustIsvRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsvNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custIsvRefRepository.findAll().size();
        // set the field null
        custIsvRef.setIsvName(null);

        // Create the CustIsvRef, which fails.
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        restCustIsvRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsvCustIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custIsvRefRepository.findAll().size();
        // set the field null
        custIsvRef.setIsvCustId(null);

        // Create the CustIsvRef, which fails.
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        restCustIsvRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustIsvRefs() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList
        restCustIsvRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custIsvRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].isvId").value(hasItem(DEFAULT_ISV_ID)))
            .andExpect(jsonPath("$.[*].isvName").value(hasItem(DEFAULT_ISV_NAME)))
            .andExpect(jsonPath("$.[*].isvCustId").value(hasItem(DEFAULT_ISV_CUST_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustIsvRef() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get the custIsvRef
        restCustIsvRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custIsvRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custIsvRef.getId().intValue()))
            .andExpect(jsonPath("$.isvId").value(DEFAULT_ISV_ID))
            .andExpect(jsonPath("$.isvName").value(DEFAULT_ISV_NAME))
            .andExpect(jsonPath("$.isvCustId").value(DEFAULT_ISV_CUST_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustIsvRefsByIdFiltering() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        Long id = custIsvRef.getId();

        defaultCustIsvRefShouldBeFound("id.equals=" + id);
        defaultCustIsvRefShouldNotBeFound("id.notEquals=" + id);

        defaultCustIsvRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustIsvRefShouldNotBeFound("id.greaterThan=" + id);

        defaultCustIsvRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustIsvRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId equals to DEFAULT_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.equals=" + DEFAULT_ISV_ID);

        // Get all the custIsvRefList where isvId equals to UPDATED_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.equals=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId not equals to DEFAULT_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.notEquals=" + DEFAULT_ISV_ID);

        // Get all the custIsvRefList where isvId not equals to UPDATED_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.notEquals=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsInShouldWork() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId in DEFAULT_ISV_ID or UPDATED_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.in=" + DEFAULT_ISV_ID + "," + UPDATED_ISV_ID);

        // Get all the custIsvRefList where isvId equals to UPDATED_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.in=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId is not null
        defaultCustIsvRefShouldBeFound("isvId.specified=true");

        // Get all the custIsvRefList where isvId is null
        defaultCustIsvRefShouldNotBeFound("isvId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId is greater than or equal to DEFAULT_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.greaterThanOrEqual=" + DEFAULT_ISV_ID);

        // Get all the custIsvRefList where isvId is greater than or equal to UPDATED_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.greaterThanOrEqual=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId is less than or equal to DEFAULT_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.lessThanOrEqual=" + DEFAULT_ISV_ID);

        // Get all the custIsvRefList where isvId is less than or equal to SMALLER_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.lessThanOrEqual=" + SMALLER_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId is less than DEFAULT_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.lessThan=" + DEFAULT_ISV_ID);

        // Get all the custIsvRefList where isvId is less than UPDATED_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.lessThan=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvId is greater than DEFAULT_ISV_ID
        defaultCustIsvRefShouldNotBeFound("isvId.greaterThan=" + DEFAULT_ISV_ID);

        // Get all the custIsvRefList where isvId is greater than SMALLER_ISV_ID
        defaultCustIsvRefShouldBeFound("isvId.greaterThan=" + SMALLER_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvName equals to DEFAULT_ISV_NAME
        defaultCustIsvRefShouldBeFound("isvName.equals=" + DEFAULT_ISV_NAME);

        // Get all the custIsvRefList where isvName equals to UPDATED_ISV_NAME
        defaultCustIsvRefShouldNotBeFound("isvName.equals=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvName not equals to DEFAULT_ISV_NAME
        defaultCustIsvRefShouldNotBeFound("isvName.notEquals=" + DEFAULT_ISV_NAME);

        // Get all the custIsvRefList where isvName not equals to UPDATED_ISV_NAME
        defaultCustIsvRefShouldBeFound("isvName.notEquals=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvNameIsInShouldWork() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvName in DEFAULT_ISV_NAME or UPDATED_ISV_NAME
        defaultCustIsvRefShouldBeFound("isvName.in=" + DEFAULT_ISV_NAME + "," + UPDATED_ISV_NAME);

        // Get all the custIsvRefList where isvName equals to UPDATED_ISV_NAME
        defaultCustIsvRefShouldNotBeFound("isvName.in=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvName is not null
        defaultCustIsvRefShouldBeFound("isvName.specified=true");

        // Get all the custIsvRefList where isvName is null
        defaultCustIsvRefShouldNotBeFound("isvName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvNameContainsSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvName contains DEFAULT_ISV_NAME
        defaultCustIsvRefShouldBeFound("isvName.contains=" + DEFAULT_ISV_NAME);

        // Get all the custIsvRefList where isvName contains UPDATED_ISV_NAME
        defaultCustIsvRefShouldNotBeFound("isvName.contains=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvNameNotContainsSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvName does not contain DEFAULT_ISV_NAME
        defaultCustIsvRefShouldNotBeFound("isvName.doesNotContain=" + DEFAULT_ISV_NAME);

        // Get all the custIsvRefList where isvName does not contain UPDATED_ISV_NAME
        defaultCustIsvRefShouldBeFound("isvName.doesNotContain=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId equals to DEFAULT_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.equals=" + DEFAULT_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId equals to UPDATED_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.equals=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId not equals to DEFAULT_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.notEquals=" + DEFAULT_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId not equals to UPDATED_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.notEquals=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsInShouldWork() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId in DEFAULT_ISV_CUST_ID or UPDATED_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.in=" + DEFAULT_ISV_CUST_ID + "," + UPDATED_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId equals to UPDATED_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.in=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId is not null
        defaultCustIsvRefShouldBeFound("isvCustId.specified=true");

        // Get all the custIsvRefList where isvCustId is null
        defaultCustIsvRefShouldNotBeFound("isvCustId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId is greater than or equal to DEFAULT_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.greaterThanOrEqual=" + DEFAULT_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId is greater than or equal to UPDATED_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.greaterThanOrEqual=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId is less than or equal to DEFAULT_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.lessThanOrEqual=" + DEFAULT_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId is less than or equal to SMALLER_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.lessThanOrEqual=" + SMALLER_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId is less than DEFAULT_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.lessThan=" + DEFAULT_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId is less than UPDATED_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.lessThan=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByIsvCustIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        // Get all the custIsvRefList where isvCustId is greater than DEFAULT_ISV_CUST_ID
        defaultCustIsvRefShouldNotBeFound("isvCustId.greaterThan=" + DEFAULT_ISV_CUST_ID);

        // Get all the custIsvRefList where isvCustId is greater than SMALLER_ISV_CUST_ID
        defaultCustIsvRefShouldBeFound("isvCustId.greaterThan=" + SMALLER_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByCustIsvCharIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);
        CustIsvChar custIsvChar = CustIsvCharResourceIT.createEntity(em);
        em.persist(custIsvChar);
        em.flush();
        custIsvRef.addCustIsvChar(custIsvChar);
        custIsvRefRepository.saveAndFlush(custIsvRef);
        Long custIsvCharId = custIsvChar.getId();

        // Get all the custIsvRefList where custIsvChar equals to custIsvCharId
        defaultCustIsvRefShouldBeFound("custIsvCharId.equals=" + custIsvCharId);

        // Get all the custIsvRefList where custIsvChar equals to (custIsvCharId + 1)
        defaultCustIsvRefShouldNotBeFound("custIsvCharId.equals=" + (custIsvCharId + 1));
    }

    @Test
    @Transactional
    void getAllCustIsvRefsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custIsvRef.setCustomer(customer);
        custIsvRefRepository.saveAndFlush(custIsvRef);
        Long customerId = customer.getId();

        // Get all the custIsvRefList where customer equals to customerId
        defaultCustIsvRefShouldBeFound("customerId.equals=" + customerId);

        // Get all the custIsvRefList where customer equals to (customerId + 1)
        defaultCustIsvRefShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustIsvRefShouldBeFound(String filter) throws Exception {
        restCustIsvRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custIsvRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].isvId").value(hasItem(DEFAULT_ISV_ID)))
            .andExpect(jsonPath("$.[*].isvName").value(hasItem(DEFAULT_ISV_NAME)))
            .andExpect(jsonPath("$.[*].isvCustId").value(hasItem(DEFAULT_ISV_CUST_ID.intValue())));

        // Check, that the count call also returns 1
        restCustIsvRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustIsvRefShouldNotBeFound(String filter) throws Exception {
        restCustIsvRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustIsvRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustIsvRef() throws Exception {
        // Get the custIsvRef
        restCustIsvRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustIsvRef() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();

        // Update the custIsvRef
        CustIsvRef updatedCustIsvRef = custIsvRefRepository.findById(custIsvRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustIsvRef are not directly saved in db
        em.detach(updatedCustIsvRef);
        updatedCustIsvRef.isvId(UPDATED_ISV_ID).isvName(UPDATED_ISV_NAME).isvCustId(UPDATED_ISV_CUST_ID);
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(updatedCustIsvRef);

        restCustIsvRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custIsvRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
        CustIsvRef testCustIsvRef = custIsvRefList.get(custIsvRefList.size() - 1);
        assertThat(testCustIsvRef.getIsvId()).isEqualTo(UPDATED_ISV_ID);
        assertThat(testCustIsvRef.getIsvName()).isEqualTo(UPDATED_ISV_NAME);
        assertThat(testCustIsvRef.getIsvCustId()).isEqualTo(UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustIsvRef() throws Exception {
        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();
        custIsvRef.setId(count.incrementAndGet());

        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustIsvRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custIsvRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustIsvRef() throws Exception {
        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();
        custIsvRef.setId(count.incrementAndGet());

        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustIsvRef() throws Exception {
        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();
        custIsvRef.setId(count.incrementAndGet());

        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvRefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustIsvRefWithPatch() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();

        // Update the custIsvRef using partial update
        CustIsvRef partialUpdatedCustIsvRef = new CustIsvRef();
        partialUpdatedCustIsvRef.setId(custIsvRef.getId());

        partialUpdatedCustIsvRef.isvId(UPDATED_ISV_ID);

        restCustIsvRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustIsvRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustIsvRef))
            )
            .andExpect(status().isOk());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
        CustIsvRef testCustIsvRef = custIsvRefList.get(custIsvRefList.size() - 1);
        assertThat(testCustIsvRef.getIsvId()).isEqualTo(UPDATED_ISV_ID);
        assertThat(testCustIsvRef.getIsvName()).isEqualTo(DEFAULT_ISV_NAME);
        assertThat(testCustIsvRef.getIsvCustId()).isEqualTo(DEFAULT_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustIsvRefWithPatch() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();

        // Update the custIsvRef using partial update
        CustIsvRef partialUpdatedCustIsvRef = new CustIsvRef();
        partialUpdatedCustIsvRef.setId(custIsvRef.getId());

        partialUpdatedCustIsvRef.isvId(UPDATED_ISV_ID).isvName(UPDATED_ISV_NAME).isvCustId(UPDATED_ISV_CUST_ID);

        restCustIsvRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustIsvRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustIsvRef))
            )
            .andExpect(status().isOk());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
        CustIsvRef testCustIsvRef = custIsvRefList.get(custIsvRefList.size() - 1);
        assertThat(testCustIsvRef.getIsvId()).isEqualTo(UPDATED_ISV_ID);
        assertThat(testCustIsvRef.getIsvName()).isEqualTo(UPDATED_ISV_NAME);
        assertThat(testCustIsvRef.getIsvCustId()).isEqualTo(UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustIsvRef() throws Exception {
        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();
        custIsvRef.setId(count.incrementAndGet());

        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustIsvRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custIsvRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustIsvRef() throws Exception {
        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();
        custIsvRef.setId(count.incrementAndGet());

        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustIsvRef() throws Exception {
        int databaseSizeBeforeUpdate = custIsvRefRepository.findAll().size();
        custIsvRef.setId(count.incrementAndGet());

        // Create the CustIsvRef
        CustIsvRefDTO custIsvRefDTO = custIsvRefMapper.toDto(custIsvRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvRefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custIsvRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustIsvRef in the database
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustIsvRef() throws Exception {
        // Initialize the database
        custIsvRefRepository.saveAndFlush(custIsvRef);

        int databaseSizeBeforeDelete = custIsvRefRepository.findAll().size();

        // Delete the custIsvRef
        restCustIsvRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custIsvRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustIsvRef> custIsvRefList = custIsvRefRepository.findAll();
        assertThat(custIsvRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
