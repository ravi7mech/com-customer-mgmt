package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustBillArrangement;
import com.apptium.customer.repository.CustBillArrangementRepository;
import com.apptium.customer.service.criteria.CustBillArrangementCriteria;
import com.apptium.customer.service.dto.CustBillArrangementDTO;
import com.apptium.customer.service.mapper.CustBillArrangementMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CustBillArrangement} entities in the database.
 * The main input is a {@link CustBillArrangementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustBillArrangementDTO} or a {@link Page} of {@link CustBillArrangementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustBillArrangementQueryService extends QueryService<CustBillArrangement> {

    private final Logger log = LoggerFactory.getLogger(CustBillArrangementQueryService.class);

    private final CustBillArrangementRepository custBillArrangementRepository;

    private final CustBillArrangementMapper custBillArrangementMapper;

    public CustBillArrangementQueryService(
        CustBillArrangementRepository custBillArrangementRepository,
        CustBillArrangementMapper custBillArrangementMapper
    ) {
        this.custBillArrangementRepository = custBillArrangementRepository;
        this.custBillArrangementMapper = custBillArrangementMapper;
    }

    /**
     * Return a {@link List} of {@link CustBillArrangementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillArrangementDTO> findByCriteria(CustBillArrangementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustBillArrangement> specification = createSpecification(criteria);
        return custBillArrangementMapper.toDto(custBillArrangementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustBillArrangementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustBillArrangementDTO> findByCriteria(CustBillArrangementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustBillArrangement> specification = createSpecification(criteria);
        return custBillArrangementRepository.findAll(specification, page).map(custBillArrangementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustBillArrangementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustBillArrangement> specification = createSpecification(criteria);
        return custBillArrangementRepository.count(specification);
    }

    /**
     * Function to convert {@link CustBillArrangementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustBillArrangement> createSpecification(CustBillArrangementCriteria criteria) {
        Specification<CustBillArrangement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustBillArrangement_.id));
            }
            if (criteria.getBillArrangementId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBillArrangementId(), CustBillArrangement_.billArrangementId));
            }
            if (criteria.getStatementAccNo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getStatementAccNo(), CustBillArrangement_.statementAccNo));
            }
            if (criteria.getBillAccNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillAccNo(), CustBillArrangement_.billAccNo));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), CustBillArrangement_.dueDate));
            }
            if (criteria.getCycleDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCycleDate(), CustBillArrangement_.cycleDate));
            }
            if (criteria.getPromiseToPayDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPromiseToPayDate(), CustBillArrangement_.promiseToPayDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), CustBillArrangement_.startDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CustBillArrangement_.status));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustBillArrangement_.createdDate));
            }
            if (criteria.getStatusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusDate(), CustBillArrangement_.statusDate));
            }
            if (criteria.getStatusReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusReason(), CustBillArrangement_.statusReason));
            }
            if (criteria.getStatementFormatId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getStatementFormatId(), CustBillArrangement_.statementFormatId));
            }
            if (criteria.getAutoPayId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAutoPayId(),
                            root -> root.join(CustBillArrangement_.autoPay, JoinType.LEFT).get(AutoPay_.id)
                        )
                    );
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustBillArrangement_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
