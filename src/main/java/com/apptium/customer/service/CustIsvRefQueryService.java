package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustIsvRef;
import com.apptium.customer.repository.CustIsvRefRepository;
import com.apptium.customer.service.criteria.CustIsvRefCriteria;
import com.apptium.customer.service.dto.CustIsvRefDTO;
import com.apptium.customer.service.mapper.CustIsvRefMapper;
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
 * Service for executing complex queries for {@link CustIsvRef} entities in the database.
 * The main input is a {@link CustIsvRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustIsvRefDTO} or a {@link Page} of {@link CustIsvRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustIsvRefQueryService extends QueryService<CustIsvRef> {

    private final Logger log = LoggerFactory.getLogger(CustIsvRefQueryService.class);

    private final CustIsvRefRepository custIsvRefRepository;

    private final CustIsvRefMapper custIsvRefMapper;

    public CustIsvRefQueryService(CustIsvRefRepository custIsvRefRepository, CustIsvRefMapper custIsvRefMapper) {
        this.custIsvRefRepository = custIsvRefRepository;
        this.custIsvRefMapper = custIsvRefMapper;
    }

    /**
     * Return a {@link List} of {@link CustIsvRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustIsvRefDTO> findByCriteria(CustIsvRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustIsvRef> specification = createSpecification(criteria);
        return custIsvRefMapper.toDto(custIsvRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustIsvRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustIsvRefDTO> findByCriteria(CustIsvRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustIsvRef> specification = createSpecification(criteria);
        return custIsvRefRepository.findAll(specification, page).map(custIsvRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustIsvRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustIsvRef> specification = createSpecification(criteria);
        return custIsvRefRepository.count(specification);
    }

    /**
     * Function to convert {@link CustIsvRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustIsvRef> createSpecification(CustIsvRefCriteria criteria) {
        Specification<CustIsvRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustIsvRef_.id));
            }
            if (criteria.getIsvId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsvId(), CustIsvRef_.isvId));
            }
            if (criteria.getIsvName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsvName(), CustIsvRef_.isvName));
            }
            if (criteria.getIsvCustId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsvCustId(), CustIsvRef_.isvCustId));
            }
            if (criteria.getCustIsvCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustIsvCharId(),
                            root -> root.join(CustIsvRef_.custIsvChars, JoinType.LEFT).get(CustIsvChar_.id)
                        )
                    );
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustIsvRef_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
