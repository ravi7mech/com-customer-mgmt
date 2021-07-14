package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustBillArrangementRepository;
import com.apptium.customer.service.CustBillArrangementQueryService;
import com.apptium.customer.service.CustBillArrangementService;
import com.apptium.customer.service.criteria.CustBillArrangementCriteria;
import com.apptium.customer.service.dto.CustBillArrangementDTO;
import com.apptium.customer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.customer.domain.CustBillArrangement}.
 */
@RestController
@RequestMapping("/api")
public class CustBillArrangementResource {

    private final Logger log = LoggerFactory.getLogger(CustBillArrangementResource.class);

    private static final String ENTITY_NAME = "customerManagementCustBillArrangement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustBillArrangementService custBillArrangementService;

    private final CustBillArrangementRepository custBillArrangementRepository;

    private final CustBillArrangementQueryService custBillArrangementQueryService;

    public CustBillArrangementResource(
        CustBillArrangementService custBillArrangementService,
        CustBillArrangementRepository custBillArrangementRepository,
        CustBillArrangementQueryService custBillArrangementQueryService
    ) {
        this.custBillArrangementService = custBillArrangementService;
        this.custBillArrangementRepository = custBillArrangementRepository;
        this.custBillArrangementQueryService = custBillArrangementQueryService;
    }

    /**
     * {@code POST  /cust-bill-arrangements} : Create a new custBillArrangement.
     *
     * @param custBillArrangementDTO the custBillArrangementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custBillArrangementDTO, or with status {@code 400 (Bad Request)} if the custBillArrangement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-bill-arrangements")
    public ResponseEntity<CustBillArrangementDTO> createCustBillArrangement(@RequestBody CustBillArrangementDTO custBillArrangementDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustBillArrangement : {}", custBillArrangementDTO);
        if (custBillArrangementDTO.getId() != null) {
            throw new BadRequestAlertException("A new custBillArrangement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustBillArrangementDTO result = custBillArrangementService.save(custBillArrangementDTO);
        return ResponseEntity
            .created(new URI("/api/cust-bill-arrangements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-bill-arrangements/:id} : Updates an existing custBillArrangement.
     *
     * @param id the id of the custBillArrangementDTO to save.
     * @param custBillArrangementDTO the custBillArrangementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillArrangementDTO,
     * or with status {@code 400 (Bad Request)} if the custBillArrangementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custBillArrangementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-bill-arrangements/{id}")
    public ResponseEntity<CustBillArrangementDTO> updateCustBillArrangement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustBillArrangementDTO custBillArrangementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustBillArrangement : {}, {}", id, custBillArrangementDTO);
        if (custBillArrangementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillArrangementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillArrangementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustBillArrangementDTO result = custBillArrangementService.save(custBillArrangementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custBillArrangementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-bill-arrangements/:id} : Partial updates given fields of an existing custBillArrangement, field will ignore if it is null
     *
     * @param id the id of the custBillArrangementDTO to save.
     * @param custBillArrangementDTO the custBillArrangementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillArrangementDTO,
     * or with status {@code 400 (Bad Request)} if the custBillArrangementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custBillArrangementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custBillArrangementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-bill-arrangements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustBillArrangementDTO> partialUpdateCustBillArrangement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustBillArrangementDTO custBillArrangementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustBillArrangement partially : {}, {}", id, custBillArrangementDTO);
        if (custBillArrangementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillArrangementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillArrangementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustBillArrangementDTO> result = custBillArrangementService.partialUpdate(custBillArrangementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custBillArrangementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-bill-arrangements} : get all the custBillArrangements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custBillArrangements in body.
     */
    @GetMapping("/cust-bill-arrangements")
    public ResponseEntity<List<CustBillArrangementDTO>> getAllCustBillArrangements(CustBillArrangementCriteria criteria) {
        log.debug("REST request to get CustBillArrangements by criteria: {}", criteria);
        List<CustBillArrangementDTO> entityList = custBillArrangementQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-bill-arrangements/count} : count all the custBillArrangements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-bill-arrangements/count")
    public ResponseEntity<Long> countCustBillArrangements(CustBillArrangementCriteria criteria) {
        log.debug("REST request to count CustBillArrangements by criteria: {}", criteria);
        return ResponseEntity.ok().body(custBillArrangementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-bill-arrangements/:id} : get the "id" custBillArrangement.
     *
     * @param id the id of the custBillArrangementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custBillArrangementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-bill-arrangements/{id}")
    public ResponseEntity<CustBillArrangementDTO> getCustBillArrangement(@PathVariable Long id) {
        log.debug("REST request to get CustBillArrangement : {}", id);
        Optional<CustBillArrangementDTO> custBillArrangementDTO = custBillArrangementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custBillArrangementDTO);
    }

    /**
     * {@code DELETE  /cust-bill-arrangements/:id} : delete the "id" custBillArrangement.
     *
     * @param id the id of the custBillArrangementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-bill-arrangements/{id}")
    public ResponseEntity<Void> deleteCustBillArrangement(@PathVariable Long id) {
        log.debug("REST request to delete CustBillArrangement : {}", id);
        custBillArrangementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
