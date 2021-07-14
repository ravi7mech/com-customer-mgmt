package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustIsvChar;
import com.apptium.customer.repository.CustIsvCharRepository;
import com.apptium.customer.service.criteria.CustIsvCharCriteria;
import com.apptium.customer.service.dto.CustIsvCharDTO;
import com.apptium.customer.service.mapper.CustIsvCharMapper;
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
 * Service for executing complex queries for {@link CustIsvChar} entities in the database.
 * The main input is a {@link CustIsvCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustIsvCharDTO} or a {@link Page} of {@link CustIsvCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustIsvCharQueryService extends QueryService<CustIsvChar> {

    private final Logger log = LoggerFactory.getLogger(CustIsvCharQueryService.class);

    private final CustIsvCharRepository custIsvCharRepository;

    private final CustIsvCharMapper custIsvCharMapper;

    public CustIsvCharQueryService(CustIsvCharRepository custIsvCharRepository, CustIsvCharMapper custIsvCharMapper) {
        this.custIsvCharRepository = custIsvCharRepository;
        this.custIsvCharMapper = custIsvCharMapper;
    }

    /**
     * Return a {@link List} of {@link CustIsvCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustIsvCharDTO> findByCriteria(CustIsvCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustIsvChar> specification = createSpecification(criteria);
        return custIsvCharMapper.toDto(custIsvCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustIsvCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustIsvCharDTO> findByCriteria(CustIsvCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustIsvChar> specification = createSpecification(criteria);
        return custIsvCharRepository.findAll(specification, page).map(custIsvCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustIsvCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustIsvChar> specification = createSpecification(criteria);
        return custIsvCharRepository.count(specification);
    }

    /**
     * Function to convert {@link CustIsvCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustIsvChar> createSpecification(CustIsvCharCriteria criteria) {
        Specification<CustIsvChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustIsvChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustIsvChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), CustIsvChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), CustIsvChar_.valueType));
            }
            if (criteria.getCustIsvRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustIsvRefId(),
                            root -> root.join(CustIsvChar_.custIsvRef, JoinType.LEFT).get(CustIsvRef_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
