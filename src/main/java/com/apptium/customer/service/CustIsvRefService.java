package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustIsvRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustIsvRef}.
 */
public interface CustIsvRefService {
    /**
     * Save a custIsvRef.
     *
     * @param custIsvRefDTO the entity to save.
     * @return the persisted entity.
     */
    CustIsvRefDTO save(CustIsvRefDTO custIsvRefDTO);

    /**
     * Partially updates a custIsvRef.
     *
     * @param custIsvRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustIsvRefDTO> partialUpdate(CustIsvRefDTO custIsvRefDTO);

    /**
     * Get all the custIsvRefs.
     *
     * @return the list of entities.
     */
    List<CustIsvRefDTO> findAll();

    /**
     * Get the "id" custIsvRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustIsvRefDTO> findOne(Long id);

    /**
     * Delete the "id" custIsvRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
