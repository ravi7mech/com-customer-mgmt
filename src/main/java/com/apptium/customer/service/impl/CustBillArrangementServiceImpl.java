package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustBillArrangement;
import com.apptium.customer.repository.CustBillArrangementRepository;
import com.apptium.customer.service.CustBillArrangementService;
import com.apptium.customer.service.dto.CustBillArrangementDTO;
import com.apptium.customer.service.mapper.CustBillArrangementMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustBillArrangement}.
 */
@Service
@Transactional
public class CustBillArrangementServiceImpl implements CustBillArrangementService {

    private final Logger log = LoggerFactory.getLogger(CustBillArrangementServiceImpl.class);

    private final CustBillArrangementRepository custBillArrangementRepository;

    private final CustBillArrangementMapper custBillArrangementMapper;

    public CustBillArrangementServiceImpl(
        CustBillArrangementRepository custBillArrangementRepository,
        CustBillArrangementMapper custBillArrangementMapper
    ) {
        this.custBillArrangementRepository = custBillArrangementRepository;
        this.custBillArrangementMapper = custBillArrangementMapper;
    }

    @Override
    public CustBillArrangementDTO save(CustBillArrangementDTO custBillArrangementDTO) {
        log.debug("Request to save CustBillArrangement : {}", custBillArrangementDTO);
        CustBillArrangement custBillArrangement = custBillArrangementMapper.toEntity(custBillArrangementDTO);
        custBillArrangement = custBillArrangementRepository.save(custBillArrangement);
        return custBillArrangementMapper.toDto(custBillArrangement);
    }

    @Override
    public Optional<CustBillArrangementDTO> partialUpdate(CustBillArrangementDTO custBillArrangementDTO) {
        log.debug("Request to partially update CustBillArrangement : {}", custBillArrangementDTO);

        return custBillArrangementRepository
            .findById(custBillArrangementDTO.getId())
            .map(
                existingCustBillArrangement -> {
                    custBillArrangementMapper.partialUpdate(existingCustBillArrangement, custBillArrangementDTO);

                    return existingCustBillArrangement;
                }
            )
            .map(custBillArrangementRepository::save)
            .map(custBillArrangementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustBillArrangementDTO> findAll() {
        log.debug("Request to get all CustBillArrangements");
        return custBillArrangementRepository
            .findAll()
            .stream()
            .map(custBillArrangementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the custBillArrangements where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillArrangementDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custBillArrangements where Customer is null");
        return StreamSupport
            .stream(custBillArrangementRepository.findAll().spliterator(), false)
            .filter(custBillArrangement -> custBillArrangement.getCustomer() == null)
            .map(custBillArrangementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustBillArrangementDTO> findOne(Long id) {
        log.debug("Request to get CustBillArrangement : {}", id);
        return custBillArrangementRepository.findById(id).map(custBillArrangementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustBillArrangement : {}", id);
        custBillArrangementRepository.deleteById(id);
    }
}
