package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustIsvRefRepository;
import com.apptium.customer.service.CustIsvRefQueryService;
import com.apptium.customer.service.CustIsvRefService;
import com.apptium.customer.service.criteria.CustIsvRefCriteria;
import com.apptium.customer.service.dto.CustIsvRefDTO;
import com.apptium.customer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.customer.domain.CustIsvRef}.
 */
@RestController
@RequestMapping("/api")
public class CustIsvRefResource {

    private final Logger log = LoggerFactory.getLogger(CustIsvRefResource.class);

    private static final String ENTITY_NAME = "customerManagementCustIsvRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustIsvRefService custIsvRefService;

    private final CustIsvRefRepository custIsvRefRepository;

    private final CustIsvRefQueryService custIsvRefQueryService;

    public CustIsvRefResource(
        CustIsvRefService custIsvRefService,
        CustIsvRefRepository custIsvRefRepository,
        CustIsvRefQueryService custIsvRefQueryService
    ) {
        this.custIsvRefService = custIsvRefService;
        this.custIsvRefRepository = custIsvRefRepository;
        this.custIsvRefQueryService = custIsvRefQueryService;
    }

    /**
     * {@code POST  /cust-isv-refs} : Create a new custIsvRef.
     *
     * @param custIsvRefDTO the custIsvRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custIsvRefDTO, or with status {@code 400 (Bad Request)} if the custIsvRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-isv-refs")
    public ResponseEntity<CustIsvRefDTO> createCustIsvRef(@Valid @RequestBody CustIsvRefDTO custIsvRefDTO) throws URISyntaxException {
        log.debug("REST request to save CustIsvRef : {}", custIsvRefDTO);
        if (custIsvRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new custIsvRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustIsvRefDTO result = custIsvRefService.save(custIsvRefDTO);
        return ResponseEntity
            .created(new URI("/api/cust-isv-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-isv-refs/:id} : Updates an existing custIsvRef.
     *
     * @param id the id of the custIsvRefDTO to save.
     * @param custIsvRefDTO the custIsvRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custIsvRefDTO,
     * or with status {@code 400 (Bad Request)} if the custIsvRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custIsvRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-isv-refs/{id}")
    public ResponseEntity<CustIsvRefDTO> updateCustIsvRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustIsvRefDTO custIsvRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustIsvRef : {}, {}", id, custIsvRefDTO);
        if (custIsvRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custIsvRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custIsvRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustIsvRefDTO result = custIsvRefService.save(custIsvRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custIsvRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-isv-refs/:id} : Partial updates given fields of an existing custIsvRef, field will ignore if it is null
     *
     * @param id the id of the custIsvRefDTO to save.
     * @param custIsvRefDTO the custIsvRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custIsvRefDTO,
     * or with status {@code 400 (Bad Request)} if the custIsvRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custIsvRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custIsvRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-isv-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustIsvRefDTO> partialUpdateCustIsvRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustIsvRefDTO custIsvRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustIsvRef partially : {}, {}", id, custIsvRefDTO);
        if (custIsvRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custIsvRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custIsvRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustIsvRefDTO> result = custIsvRefService.partialUpdate(custIsvRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custIsvRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-isv-refs} : get all the custIsvRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custIsvRefs in body.
     */
    @GetMapping("/cust-isv-refs")
    public ResponseEntity<List<CustIsvRefDTO>> getAllCustIsvRefs(CustIsvRefCriteria criteria) {
        log.debug("REST request to get CustIsvRefs by criteria: {}", criteria);
        List<CustIsvRefDTO> entityList = custIsvRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-isv-refs/count} : count all the custIsvRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-isv-refs/count")
    public ResponseEntity<Long> countCustIsvRefs(CustIsvRefCriteria criteria) {
        log.debug("REST request to count CustIsvRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(custIsvRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-isv-refs/:id} : get the "id" custIsvRef.
     *
     * @param id the id of the custIsvRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custIsvRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-isv-refs/{id}")
    public ResponseEntity<CustIsvRefDTO> getCustIsvRef(@PathVariable Long id) {
        log.debug("REST request to get CustIsvRef : {}", id);
        Optional<CustIsvRefDTO> custIsvRefDTO = custIsvRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custIsvRefDTO);
    }

    /**
     * {@code DELETE  /cust-isv-refs/:id} : delete the "id" custIsvRef.
     *
     * @param id the id of the custIsvRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-isv-refs/{id}")
    public ResponseEntity<Void> deleteCustIsvRef(@PathVariable Long id) {
        log.debug("REST request to delete CustIsvRef : {}", id);
        custIsvRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
