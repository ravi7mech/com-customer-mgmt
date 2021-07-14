package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustBillArrangementDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustBillArrangement}.
 */
public interface CustBillArrangementService {
    /**
     * Save a custBillArrangement.
     *
     * @param custBillArrangementDTO the entity to save.
     * @return the persisted entity.
     */
    CustBillArrangementDTO save(CustBillArrangementDTO custBillArrangementDTO);

    /**
     * Partially updates a custBillArrangement.
     *
     * @param custBillArrangementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustBillArrangementDTO> partialUpdate(CustBillArrangementDTO custBillArrangementDTO);

    /**
     * Get all the custBillArrangements.
     *
     * @return the list of entities.
     */
    List<CustBillArrangementDTO> findAll();
    /**
     * Get all the CustBillArrangementDTO where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustBillArrangementDTO> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custBillArrangement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustBillArrangementDTO> findOne(Long id);

    /**
     * Delete the "id" custBillArrangement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
