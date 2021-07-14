package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.AutoPay;
import com.apptium.customer.domain.CustBillArrangement;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustBillArrangementRepository;
import com.apptium.customer.service.criteria.CustBillArrangementCriteria;
import com.apptium.customer.service.dto.CustBillArrangementDTO;
import com.apptium.customer.service.mapper.CustBillArrangementMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CustBillArrangementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustBillArrangementResourceIT {

    private static final Long DEFAULT_BILL_ARRANGEMENT_ID = 1L;
    private static final Long UPDATED_BILL_ARRANGEMENT_ID = 2L;
    private static final Long SMALLER_BILL_ARRANGEMENT_ID = 1L - 1L;

    private static final Long DEFAULT_STATEMENT_ACC_NO = 1L;
    private static final Long UPDATED_STATEMENT_ACC_NO = 2L;
    private static final Long SMALLER_STATEMENT_ACC_NO = 1L - 1L;

    private static final Long DEFAULT_BILL_ACC_NO = 1L;
    private static final Long UPDATED_BILL_ACC_NO = 2L;
    private static final Long SMALLER_BILL_ACC_NO = 1L - 1L;

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CYCLE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CYCLE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CYCLE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_PROMISE_TO_PAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROMISE_TO_PAY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PROMISE_TO_PAY_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_STATUS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STATUS_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STATUS_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS_REASON = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_STATEMENT_FORMAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATEMENT_FORMAT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cust-bill-arrangements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustBillArrangementRepository custBillArrangementRepository;

    @Autowired
    private CustBillArrangementMapper custBillArrangementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustBillArrangementMockMvc;

    private CustBillArrangement custBillArrangement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillArrangement createEntity(EntityManager em) {
        CustBillArrangement custBillArrangement = new CustBillArrangement()
            .billArrangementId(DEFAULT_BILL_ARRANGEMENT_ID)
            .statementAccNo(DEFAULT_STATEMENT_ACC_NO)
            .billAccNo(DEFAULT_BILL_ACC_NO)
            .dueDate(DEFAULT_DUE_DATE)
            .cycleDate(DEFAULT_CYCLE_DATE)
            .promiseToPayDate(DEFAULT_PROMISE_TO_PAY_DATE)
            .startDate(DEFAULT_START_DATE)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .statusDate(DEFAULT_STATUS_DATE)
            .statusReason(DEFAULT_STATUS_REASON)
            .statementFormatId(DEFAULT_STATEMENT_FORMAT_ID);
        return custBillArrangement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillArrangement createUpdatedEntity(EntityManager em) {
        CustBillArrangement custBillArrangement = new CustBillArrangement()
            .billArrangementId(UPDATED_BILL_ARRANGEMENT_ID)
            .statementAccNo(UPDATED_STATEMENT_ACC_NO)
            .billAccNo(UPDATED_BILL_ACC_NO)
            .dueDate(UPDATED_DUE_DATE)
            .cycleDate(UPDATED_CYCLE_DATE)
            .promiseToPayDate(UPDATED_PROMISE_TO_PAY_DATE)
            .startDate(UPDATED_START_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .statementFormatId(UPDATED_STATEMENT_FORMAT_ID);
        return custBillArrangement;
    }

    @BeforeEach
    public void initTest() {
        custBillArrangement = createEntity(em);
    }

    @Test
    @Transactional
    void createCustBillArrangement() throws Exception {
        int databaseSizeBeforeCreate = custBillArrangementRepository.findAll().size();
        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);
        restCustBillArrangementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeCreate + 1);
        CustBillArrangement testCustBillArrangement = custBillArrangementList.get(custBillArrangementList.size() - 1);
        assertThat(testCustBillArrangement.getBillArrangementId()).isEqualTo(DEFAULT_BILL_ARRANGEMENT_ID);
        assertThat(testCustBillArrangement.getStatementAccNo()).isEqualTo(DEFAULT_STATEMENT_ACC_NO);
        assertThat(testCustBillArrangement.getBillAccNo()).isEqualTo(DEFAULT_BILL_ACC_NO);
        assertThat(testCustBillArrangement.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCustBillArrangement.getCycleDate()).isEqualTo(DEFAULT_CYCLE_DATE);
        assertThat(testCustBillArrangement.getPromiseToPayDate()).isEqualTo(DEFAULT_PROMISE_TO_PAY_DATE);
        assertThat(testCustBillArrangement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCustBillArrangement.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustBillArrangement.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCustBillArrangement.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testCustBillArrangement.getStatusReason()).isEqualTo(DEFAULT_STATUS_REASON);
        assertThat(testCustBillArrangement.getStatementFormatId()).isEqualTo(DEFAULT_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void createCustBillArrangementWithExistingId() throws Exception {
        // Create the CustBillArrangement with an existing ID
        custBillArrangement.setId(1L);
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        int databaseSizeBeforeCreate = custBillArrangementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustBillArrangementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustBillArrangements() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList
        restCustBillArrangementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillArrangement.getId().intValue())))
            .andExpect(jsonPath("$.[*].billArrangementId").value(hasItem(DEFAULT_BILL_ARRANGEMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].statementAccNo").value(hasItem(DEFAULT_STATEMENT_ACC_NO.intValue())))
            .andExpect(jsonPath("$.[*].billAccNo").value(hasItem(DEFAULT_BILL_ACC_NO.intValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].cycleDate").value(hasItem(DEFAULT_CYCLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].promiseToPayDate").value(hasItem(DEFAULT_PROMISE_TO_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusReason").value(hasItem(DEFAULT_STATUS_REASON)))
            .andExpect(jsonPath("$.[*].statementFormatId").value(hasItem(DEFAULT_STATEMENT_FORMAT_ID)));
    }

    @Test
    @Transactional
    void getCustBillArrangement() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get the custBillArrangement
        restCustBillArrangementMockMvc
            .perform(get(ENTITY_API_URL_ID, custBillArrangement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custBillArrangement.getId().intValue()))
            .andExpect(jsonPath("$.billArrangementId").value(DEFAULT_BILL_ARRANGEMENT_ID.intValue()))
            .andExpect(jsonPath("$.statementAccNo").value(DEFAULT_STATEMENT_ACC_NO.intValue()))
            .andExpect(jsonPath("$.billAccNo").value(DEFAULT_BILL_ACC_NO.intValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.cycleDate").value(DEFAULT_CYCLE_DATE.toString()))
            .andExpect(jsonPath("$.promiseToPayDate").value(DEFAULT_PROMISE_TO_PAY_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.statusReason").value(DEFAULT_STATUS_REASON))
            .andExpect(jsonPath("$.statementFormatId").value(DEFAULT_STATEMENT_FORMAT_ID));
    }

    @Test
    @Transactional
    void getCustBillArrangementsByIdFiltering() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        Long id = custBillArrangement.getId();

        defaultCustBillArrangementShouldBeFound("id.equals=" + id);
        defaultCustBillArrangementShouldNotBeFound("id.notEquals=" + id);

        defaultCustBillArrangementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustBillArrangementShouldNotBeFound("id.greaterThan=" + id);

        defaultCustBillArrangementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustBillArrangementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId equals to DEFAULT_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.equals=" + DEFAULT_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId equals to UPDATED_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.equals=" + UPDATED_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId not equals to DEFAULT_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.notEquals=" + DEFAULT_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId not equals to UPDATED_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.notEquals=" + UPDATED_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId in DEFAULT_BILL_ARRANGEMENT_ID or UPDATED_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.in=" + DEFAULT_BILL_ARRANGEMENT_ID + "," + UPDATED_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId equals to UPDATED_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.in=" + UPDATED_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId is not null
        defaultCustBillArrangementShouldBeFound("billArrangementId.specified=true");

        // Get all the custBillArrangementList where billArrangementId is null
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId is greater than or equal to DEFAULT_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.greaterThanOrEqual=" + DEFAULT_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId is greater than or equal to UPDATED_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.greaterThanOrEqual=" + UPDATED_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId is less than or equal to DEFAULT_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.lessThanOrEqual=" + DEFAULT_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId is less than or equal to SMALLER_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.lessThanOrEqual=" + SMALLER_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId is less than DEFAULT_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.lessThan=" + DEFAULT_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId is less than UPDATED_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.lessThan=" + UPDATED_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillArrangementIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billArrangementId is greater than DEFAULT_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldNotBeFound("billArrangementId.greaterThan=" + DEFAULT_BILL_ARRANGEMENT_ID);

        // Get all the custBillArrangementList where billArrangementId is greater than SMALLER_BILL_ARRANGEMENT_ID
        defaultCustBillArrangementShouldBeFound("billArrangementId.greaterThan=" + SMALLER_BILL_ARRANGEMENT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo equals to DEFAULT_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.equals=" + DEFAULT_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo equals to UPDATED_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.equals=" + UPDATED_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo not equals to DEFAULT_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.notEquals=" + DEFAULT_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo not equals to UPDATED_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.notEquals=" + UPDATED_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo in DEFAULT_STATEMENT_ACC_NO or UPDATED_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.in=" + DEFAULT_STATEMENT_ACC_NO + "," + UPDATED_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo equals to UPDATED_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.in=" + UPDATED_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo is not null
        defaultCustBillArrangementShouldBeFound("statementAccNo.specified=true");

        // Get all the custBillArrangementList where statementAccNo is null
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo is greater than or equal to DEFAULT_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.greaterThanOrEqual=" + DEFAULT_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo is greater than or equal to UPDATED_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.greaterThanOrEqual=" + UPDATED_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo is less than or equal to DEFAULT_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.lessThanOrEqual=" + DEFAULT_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo is less than or equal to SMALLER_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.lessThanOrEqual=" + SMALLER_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo is less than DEFAULT_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.lessThan=" + DEFAULT_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo is less than UPDATED_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.lessThan=" + UPDATED_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementAccNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementAccNo is greater than DEFAULT_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("statementAccNo.greaterThan=" + DEFAULT_STATEMENT_ACC_NO);

        // Get all the custBillArrangementList where statementAccNo is greater than SMALLER_STATEMENT_ACC_NO
        defaultCustBillArrangementShouldBeFound("statementAccNo.greaterThan=" + SMALLER_STATEMENT_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo equals to DEFAULT_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.equals=" + DEFAULT_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo equals to UPDATED_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.equals=" + UPDATED_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo not equals to DEFAULT_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.notEquals=" + DEFAULT_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo not equals to UPDATED_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.notEquals=" + UPDATED_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo in DEFAULT_BILL_ACC_NO or UPDATED_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.in=" + DEFAULT_BILL_ACC_NO + "," + UPDATED_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo equals to UPDATED_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.in=" + UPDATED_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo is not null
        defaultCustBillArrangementShouldBeFound("billAccNo.specified=true");

        // Get all the custBillArrangementList where billAccNo is null
        defaultCustBillArrangementShouldNotBeFound("billAccNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo is greater than or equal to DEFAULT_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.greaterThanOrEqual=" + DEFAULT_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo is greater than or equal to UPDATED_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.greaterThanOrEqual=" + UPDATED_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo is less than or equal to DEFAULT_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.lessThanOrEqual=" + DEFAULT_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo is less than or equal to SMALLER_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.lessThanOrEqual=" + SMALLER_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo is less than DEFAULT_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.lessThan=" + DEFAULT_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo is less than UPDATED_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.lessThan=" + UPDATED_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByBillAccNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where billAccNo is greater than DEFAULT_BILL_ACC_NO
        defaultCustBillArrangementShouldNotBeFound("billAccNo.greaterThan=" + DEFAULT_BILL_ACC_NO);

        // Get all the custBillArrangementList where billAccNo is greater than SMALLER_BILL_ACC_NO
        defaultCustBillArrangementShouldBeFound("billAccNo.greaterThan=" + SMALLER_BILL_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate equals to DEFAULT_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the custBillArrangementList where dueDate equals to UPDATED_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate not equals to DEFAULT_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the custBillArrangementList where dueDate not equals to UPDATED_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the custBillArrangementList where dueDate equals to UPDATED_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate is not null
        defaultCustBillArrangementShouldBeFound("dueDate.specified=true");

        // Get all the custBillArrangementList where dueDate is null
        defaultCustBillArrangementShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the custBillArrangementList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the custBillArrangementList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate is less than DEFAULT_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the custBillArrangementList where dueDate is less than UPDATED_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where dueDate is greater than DEFAULT_DUE_DATE
        defaultCustBillArrangementShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the custBillArrangementList where dueDate is greater than SMALLER_DUE_DATE
        defaultCustBillArrangementShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate equals to DEFAULT_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.equals=" + DEFAULT_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate equals to UPDATED_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.equals=" + UPDATED_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate not equals to DEFAULT_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.notEquals=" + DEFAULT_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate not equals to UPDATED_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.notEquals=" + UPDATED_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate in DEFAULT_CYCLE_DATE or UPDATED_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.in=" + DEFAULT_CYCLE_DATE + "," + UPDATED_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate equals to UPDATED_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.in=" + UPDATED_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate is not null
        defaultCustBillArrangementShouldBeFound("cycleDate.specified=true");

        // Get all the custBillArrangementList where cycleDate is null
        defaultCustBillArrangementShouldNotBeFound("cycleDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate is greater than or equal to DEFAULT_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.greaterThanOrEqual=" + DEFAULT_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate is greater than or equal to UPDATED_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.greaterThanOrEqual=" + UPDATED_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate is less than or equal to DEFAULT_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.lessThanOrEqual=" + DEFAULT_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate is less than or equal to SMALLER_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.lessThanOrEqual=" + SMALLER_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate is less than DEFAULT_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.lessThan=" + DEFAULT_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate is less than UPDATED_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.lessThan=" + UPDATED_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCycleDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where cycleDate is greater than DEFAULT_CYCLE_DATE
        defaultCustBillArrangementShouldNotBeFound("cycleDate.greaterThan=" + DEFAULT_CYCLE_DATE);

        // Get all the custBillArrangementList where cycleDate is greater than SMALLER_CYCLE_DATE
        defaultCustBillArrangementShouldBeFound("cycleDate.greaterThan=" + SMALLER_CYCLE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate equals to DEFAULT_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.equals=" + DEFAULT_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate equals to UPDATED_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.equals=" + UPDATED_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate not equals to DEFAULT_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.notEquals=" + DEFAULT_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate not equals to UPDATED_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.notEquals=" + UPDATED_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate in DEFAULT_PROMISE_TO_PAY_DATE or UPDATED_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.in=" + DEFAULT_PROMISE_TO_PAY_DATE + "," + UPDATED_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate equals to UPDATED_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.in=" + UPDATED_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate is not null
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.specified=true");

        // Get all the custBillArrangementList where promiseToPayDate is null
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate is greater than or equal to DEFAULT_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.greaterThanOrEqual=" + DEFAULT_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate is greater than or equal to UPDATED_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.greaterThanOrEqual=" + UPDATED_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate is less than or equal to DEFAULT_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.lessThanOrEqual=" + DEFAULT_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate is less than or equal to SMALLER_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.lessThanOrEqual=" + SMALLER_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate is less than DEFAULT_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.lessThan=" + DEFAULT_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate is less than UPDATED_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.lessThan=" + UPDATED_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByPromiseToPayDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where promiseToPayDate is greater than DEFAULT_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldNotBeFound("promiseToPayDate.greaterThan=" + DEFAULT_PROMISE_TO_PAY_DATE);

        // Get all the custBillArrangementList where promiseToPayDate is greater than SMALLER_PROMISE_TO_PAY_DATE
        defaultCustBillArrangementShouldBeFound("promiseToPayDate.greaterThan=" + SMALLER_PROMISE_TO_PAY_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate equals to DEFAULT_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the custBillArrangementList where startDate equals to UPDATED_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate not equals to DEFAULT_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the custBillArrangementList where startDate not equals to UPDATED_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the custBillArrangementList where startDate equals to UPDATED_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate is not null
        defaultCustBillArrangementShouldBeFound("startDate.specified=true");

        // Get all the custBillArrangementList where startDate is null
        defaultCustBillArrangementShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the custBillArrangementList where startDate is greater than or equal to UPDATED_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate is less than or equal to DEFAULT_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the custBillArrangementList where startDate is less than or equal to SMALLER_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate is less than DEFAULT_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the custBillArrangementList where startDate is less than UPDATED_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where startDate is greater than DEFAULT_START_DATE
        defaultCustBillArrangementShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the custBillArrangementList where startDate is greater than SMALLER_START_DATE
        defaultCustBillArrangementShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where status equals to DEFAULT_STATUS
        defaultCustBillArrangementShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the custBillArrangementList where status equals to UPDATED_STATUS
        defaultCustBillArrangementShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where status not equals to DEFAULT_STATUS
        defaultCustBillArrangementShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the custBillArrangementList where status not equals to UPDATED_STATUS
        defaultCustBillArrangementShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustBillArrangementShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the custBillArrangementList where status equals to UPDATED_STATUS
        defaultCustBillArrangementShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where status is not null
        defaultCustBillArrangementShouldBeFound("status.specified=true");

        // Get all the custBillArrangementList where status is null
        defaultCustBillArrangementShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusContainsSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where status contains DEFAULT_STATUS
        defaultCustBillArrangementShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the custBillArrangementList where status contains UPDATED_STATUS
        defaultCustBillArrangementShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where status does not contain DEFAULT_STATUS
        defaultCustBillArrangementShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the custBillArrangementList where status does not contain UPDATED_STATUS
        defaultCustBillArrangementShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCustBillArrangementShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the custBillArrangementList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustBillArrangementShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultCustBillArrangementShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the custBillArrangementList where createdDate not equals to UPDATED_CREATED_DATE
        defaultCustBillArrangementShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCustBillArrangementShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the custBillArrangementList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustBillArrangementShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where createdDate is not null
        defaultCustBillArrangementShouldBeFound("createdDate.specified=true");

        // Get all the custBillArrangementList where createdDate is null
        defaultCustBillArrangementShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate equals to DEFAULT_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.equals=" + DEFAULT_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate equals to UPDATED_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.equals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate not equals to DEFAULT_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.notEquals=" + DEFAULT_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate not equals to UPDATED_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.notEquals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate in DEFAULT_STATUS_DATE or UPDATED_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.in=" + DEFAULT_STATUS_DATE + "," + UPDATED_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate equals to UPDATED_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.in=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate is not null
        defaultCustBillArrangementShouldBeFound("statusDate.specified=true");

        // Get all the custBillArrangementList where statusDate is null
        defaultCustBillArrangementShouldNotBeFound("statusDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate is greater than or equal to DEFAULT_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.greaterThanOrEqual=" + DEFAULT_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate is greater than or equal to UPDATED_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.greaterThanOrEqual=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate is less than or equal to DEFAULT_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.lessThanOrEqual=" + DEFAULT_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate is less than or equal to SMALLER_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.lessThanOrEqual=" + SMALLER_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate is less than DEFAULT_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.lessThan=" + DEFAULT_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate is less than UPDATED_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.lessThan=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusDate is greater than DEFAULT_STATUS_DATE
        defaultCustBillArrangementShouldNotBeFound("statusDate.greaterThan=" + DEFAULT_STATUS_DATE);

        // Get all the custBillArrangementList where statusDate is greater than SMALLER_STATUS_DATE
        defaultCustBillArrangementShouldBeFound("statusDate.greaterThan=" + SMALLER_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusReason equals to DEFAULT_STATUS_REASON
        defaultCustBillArrangementShouldBeFound("statusReason.equals=" + DEFAULT_STATUS_REASON);

        // Get all the custBillArrangementList where statusReason equals to UPDATED_STATUS_REASON
        defaultCustBillArrangementShouldNotBeFound("statusReason.equals=" + UPDATED_STATUS_REASON);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusReason not equals to DEFAULT_STATUS_REASON
        defaultCustBillArrangementShouldNotBeFound("statusReason.notEquals=" + DEFAULT_STATUS_REASON);

        // Get all the custBillArrangementList where statusReason not equals to UPDATED_STATUS_REASON
        defaultCustBillArrangementShouldBeFound("statusReason.notEquals=" + UPDATED_STATUS_REASON);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusReasonIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusReason in DEFAULT_STATUS_REASON or UPDATED_STATUS_REASON
        defaultCustBillArrangementShouldBeFound("statusReason.in=" + DEFAULT_STATUS_REASON + "," + UPDATED_STATUS_REASON);

        // Get all the custBillArrangementList where statusReason equals to UPDATED_STATUS_REASON
        defaultCustBillArrangementShouldNotBeFound("statusReason.in=" + UPDATED_STATUS_REASON);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusReason is not null
        defaultCustBillArrangementShouldBeFound("statusReason.specified=true");

        // Get all the custBillArrangementList where statusReason is null
        defaultCustBillArrangementShouldNotBeFound("statusReason.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusReasonContainsSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusReason contains DEFAULT_STATUS_REASON
        defaultCustBillArrangementShouldBeFound("statusReason.contains=" + DEFAULT_STATUS_REASON);

        // Get all the custBillArrangementList where statusReason contains UPDATED_STATUS_REASON
        defaultCustBillArrangementShouldNotBeFound("statusReason.contains=" + UPDATED_STATUS_REASON);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatusReasonNotContainsSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statusReason does not contain DEFAULT_STATUS_REASON
        defaultCustBillArrangementShouldNotBeFound("statusReason.doesNotContain=" + DEFAULT_STATUS_REASON);

        // Get all the custBillArrangementList where statusReason does not contain UPDATED_STATUS_REASON
        defaultCustBillArrangementShouldBeFound("statusReason.doesNotContain=" + UPDATED_STATUS_REASON);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementFormatIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementFormatId equals to DEFAULT_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldBeFound("statementFormatId.equals=" + DEFAULT_STATEMENT_FORMAT_ID);

        // Get all the custBillArrangementList where statementFormatId equals to UPDATED_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldNotBeFound("statementFormatId.equals=" + UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementFormatIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementFormatId not equals to DEFAULT_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldNotBeFound("statementFormatId.notEquals=" + DEFAULT_STATEMENT_FORMAT_ID);

        // Get all the custBillArrangementList where statementFormatId not equals to UPDATED_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldBeFound("statementFormatId.notEquals=" + UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementFormatIdIsInShouldWork() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementFormatId in DEFAULT_STATEMENT_FORMAT_ID or UPDATED_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldBeFound("statementFormatId.in=" + DEFAULT_STATEMENT_FORMAT_ID + "," + UPDATED_STATEMENT_FORMAT_ID);

        // Get all the custBillArrangementList where statementFormatId equals to UPDATED_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldNotBeFound("statementFormatId.in=" + UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementFormatIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementFormatId is not null
        defaultCustBillArrangementShouldBeFound("statementFormatId.specified=true");

        // Get all the custBillArrangementList where statementFormatId is null
        defaultCustBillArrangementShouldNotBeFound("statementFormatId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementFormatIdContainsSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementFormatId contains DEFAULT_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldBeFound("statementFormatId.contains=" + DEFAULT_STATEMENT_FORMAT_ID);

        // Get all the custBillArrangementList where statementFormatId contains UPDATED_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldNotBeFound("statementFormatId.contains=" + UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByStatementFormatIdNotContainsSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        // Get all the custBillArrangementList where statementFormatId does not contain DEFAULT_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldNotBeFound("statementFormatId.doesNotContain=" + DEFAULT_STATEMENT_FORMAT_ID);

        // Get all the custBillArrangementList where statementFormatId does not contain UPDATED_STATEMENT_FORMAT_ID
        defaultCustBillArrangementShouldBeFound("statementFormatId.doesNotContain=" + UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByAutoPayIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);
        AutoPay autoPay = AutoPayResourceIT.createEntity(em);
        em.persist(autoPay);
        em.flush();
        custBillArrangement.setAutoPay(autoPay);
        custBillArrangementRepository.saveAndFlush(custBillArrangement);
        Long autoPayId = autoPay.getId();

        // Get all the custBillArrangementList where autoPay equals to autoPayId
        defaultCustBillArrangementShouldBeFound("autoPayId.equals=" + autoPayId);

        // Get all the custBillArrangementList where autoPay equals to (autoPayId + 1)
        defaultCustBillArrangementShouldNotBeFound("autoPayId.equals=" + (autoPayId + 1));
    }

    @Test
    @Transactional
    void getAllCustBillArrangementsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custBillArrangement.setCustomer(customer);
        customer.setCustBillArrangement(custBillArrangement);
        custBillArrangementRepository.saveAndFlush(custBillArrangement);
        Long customerId = customer.getId();

        // Get all the custBillArrangementList where customer equals to customerId
        defaultCustBillArrangementShouldBeFound("customerId.equals=" + customerId);

        // Get all the custBillArrangementList where customer equals to (customerId + 1)
        defaultCustBillArrangementShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustBillArrangementShouldBeFound(String filter) throws Exception {
        restCustBillArrangementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillArrangement.getId().intValue())))
            .andExpect(jsonPath("$.[*].billArrangementId").value(hasItem(DEFAULT_BILL_ARRANGEMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].statementAccNo").value(hasItem(DEFAULT_STATEMENT_ACC_NO.intValue())))
            .andExpect(jsonPath("$.[*].billAccNo").value(hasItem(DEFAULT_BILL_ACC_NO.intValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].cycleDate").value(hasItem(DEFAULT_CYCLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].promiseToPayDate").value(hasItem(DEFAULT_PROMISE_TO_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusReason").value(hasItem(DEFAULT_STATUS_REASON)))
            .andExpect(jsonPath("$.[*].statementFormatId").value(hasItem(DEFAULT_STATEMENT_FORMAT_ID)));

        // Check, that the count call also returns 1
        restCustBillArrangementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustBillArrangementShouldNotBeFound(String filter) throws Exception {
        restCustBillArrangementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustBillArrangementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustBillArrangement() throws Exception {
        // Get the custBillArrangement
        restCustBillArrangementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustBillArrangement() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();

        // Update the custBillArrangement
        CustBillArrangement updatedCustBillArrangement = custBillArrangementRepository.findById(custBillArrangement.getId()).get();
        // Disconnect from session so that the updates on updatedCustBillArrangement are not directly saved in db
        em.detach(updatedCustBillArrangement);
        updatedCustBillArrangement
            .billArrangementId(UPDATED_BILL_ARRANGEMENT_ID)
            .statementAccNo(UPDATED_STATEMENT_ACC_NO)
            .billAccNo(UPDATED_BILL_ACC_NO)
            .dueDate(UPDATED_DUE_DATE)
            .cycleDate(UPDATED_CYCLE_DATE)
            .promiseToPayDate(UPDATED_PROMISE_TO_PAY_DATE)
            .startDate(UPDATED_START_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .statementFormatId(UPDATED_STATEMENT_FORMAT_ID);
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(updatedCustBillArrangement);

        restCustBillArrangementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillArrangementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
        CustBillArrangement testCustBillArrangement = custBillArrangementList.get(custBillArrangementList.size() - 1);
        assertThat(testCustBillArrangement.getBillArrangementId()).isEqualTo(UPDATED_BILL_ARRANGEMENT_ID);
        assertThat(testCustBillArrangement.getStatementAccNo()).isEqualTo(UPDATED_STATEMENT_ACC_NO);
        assertThat(testCustBillArrangement.getBillAccNo()).isEqualTo(UPDATED_BILL_ACC_NO);
        assertThat(testCustBillArrangement.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCustBillArrangement.getCycleDate()).isEqualTo(UPDATED_CYCLE_DATE);
        assertThat(testCustBillArrangement.getPromiseToPayDate()).isEqualTo(UPDATED_PROMISE_TO_PAY_DATE);
        assertThat(testCustBillArrangement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCustBillArrangement.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustBillArrangement.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCustBillArrangement.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustBillArrangement.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testCustBillArrangement.getStatementFormatId()).isEqualTo(UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustBillArrangement() throws Exception {
        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();
        custBillArrangement.setId(count.incrementAndGet());

        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillArrangementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillArrangementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustBillArrangement() throws Exception {
        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();
        custBillArrangement.setId(count.incrementAndGet());

        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillArrangementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustBillArrangement() throws Exception {
        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();
        custBillArrangement.setId(count.incrementAndGet());

        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillArrangementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustBillArrangementWithPatch() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();

        // Update the custBillArrangement using partial update
        CustBillArrangement partialUpdatedCustBillArrangement = new CustBillArrangement();
        partialUpdatedCustBillArrangement.setId(custBillArrangement.getId());

        partialUpdatedCustBillArrangement
            .billArrangementId(UPDATED_BILL_ARRANGEMENT_ID)
            .promiseToPayDate(UPDATED_PROMISE_TO_PAY_DATE)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .statementFormatId(UPDATED_STATEMENT_FORMAT_ID);

        restCustBillArrangementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillArrangement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillArrangement))
            )
            .andExpect(status().isOk());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
        CustBillArrangement testCustBillArrangement = custBillArrangementList.get(custBillArrangementList.size() - 1);
        assertThat(testCustBillArrangement.getBillArrangementId()).isEqualTo(UPDATED_BILL_ARRANGEMENT_ID);
        assertThat(testCustBillArrangement.getStatementAccNo()).isEqualTo(DEFAULT_STATEMENT_ACC_NO);
        assertThat(testCustBillArrangement.getBillAccNo()).isEqualTo(DEFAULT_BILL_ACC_NO);
        assertThat(testCustBillArrangement.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCustBillArrangement.getCycleDate()).isEqualTo(DEFAULT_CYCLE_DATE);
        assertThat(testCustBillArrangement.getPromiseToPayDate()).isEqualTo(UPDATED_PROMISE_TO_PAY_DATE);
        assertThat(testCustBillArrangement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCustBillArrangement.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustBillArrangement.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCustBillArrangement.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustBillArrangement.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testCustBillArrangement.getStatementFormatId()).isEqualTo(UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustBillArrangementWithPatch() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();

        // Update the custBillArrangement using partial update
        CustBillArrangement partialUpdatedCustBillArrangement = new CustBillArrangement();
        partialUpdatedCustBillArrangement.setId(custBillArrangement.getId());

        partialUpdatedCustBillArrangement
            .billArrangementId(UPDATED_BILL_ARRANGEMENT_ID)
            .statementAccNo(UPDATED_STATEMENT_ACC_NO)
            .billAccNo(UPDATED_BILL_ACC_NO)
            .dueDate(UPDATED_DUE_DATE)
            .cycleDate(UPDATED_CYCLE_DATE)
            .promiseToPayDate(UPDATED_PROMISE_TO_PAY_DATE)
            .startDate(UPDATED_START_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .statementFormatId(UPDATED_STATEMENT_FORMAT_ID);

        restCustBillArrangementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillArrangement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillArrangement))
            )
            .andExpect(status().isOk());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
        CustBillArrangement testCustBillArrangement = custBillArrangementList.get(custBillArrangementList.size() - 1);
        assertThat(testCustBillArrangement.getBillArrangementId()).isEqualTo(UPDATED_BILL_ARRANGEMENT_ID);
        assertThat(testCustBillArrangement.getStatementAccNo()).isEqualTo(UPDATED_STATEMENT_ACC_NO);
        assertThat(testCustBillArrangement.getBillAccNo()).isEqualTo(UPDATED_BILL_ACC_NO);
        assertThat(testCustBillArrangement.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCustBillArrangement.getCycleDate()).isEqualTo(UPDATED_CYCLE_DATE);
        assertThat(testCustBillArrangement.getPromiseToPayDate()).isEqualTo(UPDATED_PROMISE_TO_PAY_DATE);
        assertThat(testCustBillArrangement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCustBillArrangement.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustBillArrangement.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCustBillArrangement.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustBillArrangement.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testCustBillArrangement.getStatementFormatId()).isEqualTo(UPDATED_STATEMENT_FORMAT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustBillArrangement() throws Exception {
        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();
        custBillArrangement.setId(count.incrementAndGet());

        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillArrangementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custBillArrangementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustBillArrangement() throws Exception {
        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();
        custBillArrangement.setId(count.incrementAndGet());

        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillArrangementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustBillArrangement() throws Exception {
        int databaseSizeBeforeUpdate = custBillArrangementRepository.findAll().size();
        custBillArrangement.setId(count.incrementAndGet());

        // Create the CustBillArrangement
        CustBillArrangementDTO custBillArrangementDTO = custBillArrangementMapper.toDto(custBillArrangement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillArrangementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillArrangementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillArrangement in the database
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustBillArrangement() throws Exception {
        // Initialize the database
        custBillArrangementRepository.saveAndFlush(custBillArrangement);

        int databaseSizeBeforeDelete = custBillArrangementRepository.findAll().size();

        // Delete the custBillArrangement
        restCustBillArrangementMockMvc
            .perform(delete(ENTITY_API_URL_ID, custBillArrangement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustBillArrangement> custBillArrangementList = custBillArrangementRepository.findAll();
        assertThat(custBillArrangementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
