package com.apptium.customer.repository;

import com.apptium.customer.domain.CustIsvChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustIsvChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustIsvCharRepository extends JpaRepository<CustIsvChar, Long>, JpaSpecificationExecutor<CustIsvChar> {}
