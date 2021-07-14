package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustIsvChar;
import com.apptium.customer.domain.CustIsvRef;
import com.apptium.customer.repository.CustIsvCharRepository;
import com.apptium.customer.service.criteria.CustIsvCharCriteria;
import com.apptium.customer.service.dto.CustIsvCharDTO;
import com.apptium.customer.service.mapper.CustIsvCharMapper;
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
 * Integration tests for the {@link CustIsvCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustIsvCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;
    private static final Integer SMALLER_VALUE = 1 - 1;

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cust-isv-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustIsvCharRepository custIsvCharRepository;

    @Autowired
    private CustIsvCharMapper custIsvCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustIsvCharMockMvc;

    private CustIsvChar custIsvChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustIsvChar createEntity(EntityManager em) {
        CustIsvChar custIsvChar = new CustIsvChar().name(DEFAULT_NAME).value(DEFAULT_VALUE).valueType(DEFAULT_VALUE_TYPE);
        return custIsvChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustIsvChar createUpdatedEntity(EntityManager em) {
        CustIsvChar custIsvChar = new CustIsvChar().name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);
        return custIsvChar;
    }

    @BeforeEach
    public void initTest() {
        custIsvChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustIsvChar() throws Exception {
        int databaseSizeBeforeCreate = custIsvCharRepository.findAll().size();
        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);
        restCustIsvCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustIsvChar testCustIsvChar = custIsvCharList.get(custIsvCharList.size() - 1);
        assertThat(testCustIsvChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustIsvChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustIsvChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
    }

    @Test
    @Transactional
    void createCustIsvCharWithExistingId() throws Exception {
        // Create the CustIsvChar with an existing ID
        custIsvChar.setId(1L);
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        int databaseSizeBeforeCreate = custIsvCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustIsvCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custIsvCharRepository.findAll().size();
        // set the field null
        custIsvChar.setName(null);

        // Create the CustIsvChar, which fails.
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        restCustIsvCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custIsvCharRepository.findAll().size();
        // set the field null
        custIsvChar.setValue(null);

        // Create the CustIsvChar, which fails.
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        restCustIsvCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custIsvCharRepository.findAll().size();
        // set the field null
        custIsvChar.setValueType(null);

        // Create the CustIsvChar, which fails.
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        restCustIsvCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustIsvChars() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList
        restCustIsvCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custIsvChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)));
    }

    @Test
    @Transactional
    void getCustIsvChar() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get the custIsvChar
        restCustIsvCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custIsvChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custIsvChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE));
    }

    @Test
    @Transactional
    void getCustIsvCharsByIdFiltering() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        Long id = custIsvChar.getId();

        defaultCustIsvCharShouldBeFound("id.equals=" + id);
        defaultCustIsvCharShouldNotBeFound("id.notEquals=" + id);

        defaultCustIsvCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustIsvCharShouldNotBeFound("id.greaterThan=" + id);

        defaultCustIsvCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustIsvCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where name equals to DEFAULT_NAME
        defaultCustIsvCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custIsvCharList where name equals to UPDATED_NAME
        defaultCustIsvCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where name not equals to DEFAULT_NAME
        defaultCustIsvCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custIsvCharList where name not equals to UPDATED_NAME
        defaultCustIsvCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustIsvCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custIsvCharList where name equals to UPDATED_NAME
        defaultCustIsvCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where name is not null
        defaultCustIsvCharShouldBeFound("name.specified=true");

        // Get all the custIsvCharList where name is null
        defaultCustIsvCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where name contains DEFAULT_NAME
        defaultCustIsvCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custIsvCharList where name contains UPDATED_NAME
        defaultCustIsvCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where name does not contain DEFAULT_NAME
        defaultCustIsvCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custIsvCharList where name does not contain UPDATED_NAME
        defaultCustIsvCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value equals to DEFAULT_VALUE
        defaultCustIsvCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custIsvCharList where value equals to UPDATED_VALUE
        defaultCustIsvCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value not equals to DEFAULT_VALUE
        defaultCustIsvCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custIsvCharList where value not equals to UPDATED_VALUE
        defaultCustIsvCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustIsvCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custIsvCharList where value equals to UPDATED_VALUE
        defaultCustIsvCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value is not null
        defaultCustIsvCharShouldBeFound("value.specified=true");

        // Get all the custIsvCharList where value is null
        defaultCustIsvCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value is greater than or equal to DEFAULT_VALUE
        defaultCustIsvCharShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the custIsvCharList where value is greater than or equal to UPDATED_VALUE
        defaultCustIsvCharShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value is less than or equal to DEFAULT_VALUE
        defaultCustIsvCharShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the custIsvCharList where value is less than or equal to SMALLER_VALUE
        defaultCustIsvCharShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value is less than DEFAULT_VALUE
        defaultCustIsvCharShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the custIsvCharList where value is less than UPDATED_VALUE
        defaultCustIsvCharShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where value is greater than DEFAULT_VALUE
        defaultCustIsvCharShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the custIsvCharList where value is greater than SMALLER_VALUE
        defaultCustIsvCharShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultCustIsvCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the custIsvCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustIsvCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultCustIsvCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the custIsvCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultCustIsvCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultCustIsvCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the custIsvCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustIsvCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where valueType is not null
        defaultCustIsvCharShouldBeFound("valueType.specified=true");

        // Get all the custIsvCharList where valueType is null
        defaultCustIsvCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultCustIsvCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the custIsvCharList where valueType contains UPDATED_VALUE_TYPE
        defaultCustIsvCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        // Get all the custIsvCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultCustIsvCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the custIsvCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultCustIsvCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustIsvCharsByCustIsvRefIsEqualToSomething() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);
        CustIsvRef custIsvRef = CustIsvRefResourceIT.createEntity(em);
        em.persist(custIsvRef);
        em.flush();
        custIsvChar.setCustIsvRef(custIsvRef);
        custIsvCharRepository.saveAndFlush(custIsvChar);
        Long custIsvRefId = custIsvRef.getId();

        // Get all the custIsvCharList where custIsvRef equals to custIsvRefId
        defaultCustIsvCharShouldBeFound("custIsvRefId.equals=" + custIsvRefId);

        // Get all the custIsvCharList where custIsvRef equals to (custIsvRefId + 1)
        defaultCustIsvCharShouldNotBeFound("custIsvRefId.equals=" + (custIsvRefId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustIsvCharShouldBeFound(String filter) throws Exception {
        restCustIsvCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custIsvChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)));

        // Check, that the count call also returns 1
        restCustIsvCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustIsvCharShouldNotBeFound(String filter) throws Exception {
        restCustIsvCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustIsvCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustIsvChar() throws Exception {
        // Get the custIsvChar
        restCustIsvCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustIsvChar() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();

        // Update the custIsvChar
        CustIsvChar updatedCustIsvChar = custIsvCharRepository.findById(custIsvChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustIsvChar are not directly saved in db
        em.detach(updatedCustIsvChar);
        updatedCustIsvChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(updatedCustIsvChar);

        restCustIsvCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custIsvCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
        CustIsvChar testCustIsvChar = custIsvCharList.get(custIsvCharList.size() - 1);
        assertThat(testCustIsvChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustIsvChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustIsvChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCustIsvChar() throws Exception {
        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();
        custIsvChar.setId(count.incrementAndGet());

        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustIsvCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custIsvCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustIsvChar() throws Exception {
        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();
        custIsvChar.setId(count.incrementAndGet());

        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustIsvChar() throws Exception {
        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();
        custIsvChar.setId(count.incrementAndGet());

        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustIsvCharWithPatch() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();

        // Update the custIsvChar using partial update
        CustIsvChar partialUpdatedCustIsvChar = new CustIsvChar();
        partialUpdatedCustIsvChar.setId(custIsvChar.getId());

        partialUpdatedCustIsvChar.value(UPDATED_VALUE);

        restCustIsvCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustIsvChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustIsvChar))
            )
            .andExpect(status().isOk());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
        CustIsvChar testCustIsvChar = custIsvCharList.get(custIsvCharList.size() - 1);
        assertThat(testCustIsvChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustIsvChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustIsvChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCustIsvCharWithPatch() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();

        // Update the custIsvChar using partial update
        CustIsvChar partialUpdatedCustIsvChar = new CustIsvChar();
        partialUpdatedCustIsvChar.setId(custIsvChar.getId());

        partialUpdatedCustIsvChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);

        restCustIsvCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustIsvChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustIsvChar))
            )
            .andExpect(status().isOk());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
        CustIsvChar testCustIsvChar = custIsvCharList.get(custIsvCharList.size() - 1);
        assertThat(testCustIsvChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustIsvChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustIsvChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCustIsvChar() throws Exception {
        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();
        custIsvChar.setId(count.incrementAndGet());

        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustIsvCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custIsvCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustIsvChar() throws Exception {
        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();
        custIsvChar.setId(count.incrementAndGet());

        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustIsvChar() throws Exception {
        int databaseSizeBeforeUpdate = custIsvCharRepository.findAll().size();
        custIsvChar.setId(count.incrementAndGet());

        // Create the CustIsvChar
        CustIsvCharDTO custIsvCharDTO = custIsvCharMapper.toDto(custIsvChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustIsvCharMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custIsvCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustIsvChar in the database
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustIsvChar() throws Exception {
        // Initialize the database
        custIsvCharRepository.saveAndFlush(custIsvChar);

        int databaseSizeBeforeDelete = custIsvCharRepository.findAll().size();

        // Delete the custIsvChar
        restCustIsvCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custIsvChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustIsvChar> custIsvCharList = custIsvCharRepository.findAll();
        assertThat(custIsvCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
