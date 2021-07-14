package com.apptium.customer.repository;

import com.apptium.customer.domain.CustBillArrangement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustBillArrangement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustBillArrangementRepository
    extends JpaRepository<CustBillArrangement, Long>, JpaSpecificationExecutor<CustBillArrangement> {}
