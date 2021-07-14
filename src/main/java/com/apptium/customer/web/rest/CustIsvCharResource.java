package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustIsvCharRepository;
import com.apptium.customer.service.CustIsvCharQueryService;
import com.apptium.customer.service.CustIsvCharService;
import com.apptium.customer.service.criteria.CustIsvCharCriteria;
import com.apptium.customer.service.dto.CustIsvCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustIsvChar}.
 */
@RestController
@RequestMapping("/api")
public class CustIsvCharResource {

    private final Logger log = LoggerFactory.getLogger(CustIsvCharResource.class);

    private static final String ENTITY_NAME = "customerManagementCustIsvChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustIsvCharService custIsvCharService;

    private final CustIsvCharRepository custIsvCharRepository;

    private final CustIsvCharQueryService custIsvCharQueryService;

    public CustIsvCharResource(
        CustIsvCharService custIsvCharService,
        CustIsvCharRepository custIsvCharRepository,
        CustIsvCharQueryService custIsvCharQueryService
    ) {
        this.custIsvCharService = custIsvCharService;
        this.custIsvCharRepository = custIsvCharRepository;
        this.custIsvCharQueryService = custIsvCharQueryService;
    }

    /**
     * {@code POST  /cust-isv-chars} : Create a new custIsvChar.
     *
     * @param custIsvCharDTO the custIsvCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custIsvCharDTO, or with status {@code 400 (Bad Request)} if the custIsvChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-isv-chars")
    public ResponseEntity<CustIsvCharDTO> createCustIsvChar(@Valid @RequestBody CustIsvCharDTO custIsvCharDTO) throws URISyntaxException {
        log.debug("REST request to save CustIsvChar : {}", custIsvCharDTO);
        if (custIsvCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new custIsvChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustIsvCharDTO result = custIsvCharService.save(custIsvCharDTO);
        return ResponseEntity
            .created(new URI("/api/cust-isv-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-isv-chars/:id} : Updates an existing custIsvChar.
     *
     * @param id the id of the custIsvCharDTO to save.
     * @param custIsvCharDTO the custIsvCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custIsvCharDTO,
     * or with status {@code 400 (Bad Request)} if the custIsvCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custIsvCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-isv-chars/{id}")
    public ResponseEntity<CustIsvCharDTO> updateCustIsvChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustIsvCharDTO custIsvCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustIsvChar : {}, {}", id, custIsvCharDTO);
        if (custIsvCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custIsvCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custIsvCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustIsvCharDTO result = custIsvCharService.save(custIsvCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custIsvCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-isv-chars/:id} : Partial updates given fields of an existing custIsvChar, field will ignore if it is null
     *
     * @param id the id of the custIsvCharDTO to save.
     * @param custIsvCharDTO the custIsvCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custIsvCharDTO,
     * or with status {@code 400 (Bad Request)} if the custIsvCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custIsvCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custIsvCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-isv-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustIsvCharDTO> partialUpdateCustIsvChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustIsvCharDTO custIsvCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustIsvChar partially : {}, {}", id, custIsvCharDTO);
        if (custIsvCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custIsvCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custIsvCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustIsvCharDTO> result = custIsvCharService.partialUpdate(custIsvCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custIsvCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-isv-chars} : get all the custIsvChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custIsvChars in body.
     */
    @GetMapping("/cust-isv-chars")
    public ResponseEntity<List<CustIsvCharDTO>> getAllCustIsvChars(CustIsvCharCriteria criteria) {
        log.debug("REST request to get CustIsvChars by criteria: {}", criteria);
        List<CustIsvCharDTO> entityList = custIsvCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-isv-chars/count} : count all the custIsvChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-isv-chars/count")
    public ResponseEntity<Long> countCustIsvChars(CustIsvCharCriteria criteria) {
        log.debug("REST request to count CustIsvChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(custIsvCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-isv-chars/:id} : get the "id" custIsvChar.
     *
     * @param id the id of the custIsvCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custIsvCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-isv-chars/{id}")
    public ResponseEntity<CustIsvCharDTO> getCustIsvChar(@PathVariable Long id) {
        log.debug("REST request to get CustIsvChar : {}", id);
        Optional<CustIsvCharDTO> custIsvCharDTO = custIsvCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custIsvCharDTO);
    }

    /**
     * {@code DELETE  /cust-isv-chars/:id} : delete the "id" custIsvChar.
     *
     * @param id the id of the custIsvCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-isv-chars/{id}")
    public ResponseEntity<Void> deleteCustIsvChar(@PathVariable Long id) {
        log.debug("REST request to delete CustIsvChar : {}", id);
        custIsvCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
