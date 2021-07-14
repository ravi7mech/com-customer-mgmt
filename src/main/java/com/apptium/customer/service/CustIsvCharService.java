package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustIsvCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustIsvChar}.
 */
public interface CustIsvCharService {
    /**
     * Save a custIsvChar.
     *
     * @param custIsvCharDTO the entity to save.
     * @return the persisted entity.
     */
    CustIsvCharDTO save(CustIsvCharDTO custIsvCharDTO);

    /**
     * Partially updates a custIsvChar.
     *
     * @param custIsvCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustIsvCharDTO> partialUpdate(CustIsvCharDTO custIsvCharDTO);

    /**
     * Get all the custIsvChars.
     *
     * @return the list of entities.
     */
    List<CustIsvCharDTO> findAll();

    /**
     * Get the "id" custIsvChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustIsvCharDTO> findOne(Long id);

    /**
     * Delete the "id" custIsvChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
