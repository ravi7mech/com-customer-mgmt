package com.apptium.customer.repository;

import com.apptium.customer.domain.CustIsvRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustIsvRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustIsvRefRepository extends JpaRepository<CustIsvRef, Long>, JpaSpecificationExecutor<CustIsvRef> {}
