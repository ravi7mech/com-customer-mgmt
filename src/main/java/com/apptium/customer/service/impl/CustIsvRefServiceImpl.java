package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustIsvRef;
import com.apptium.customer.repository.CustIsvRefRepository;
import com.apptium.customer.service.CustIsvRefService;
import com.apptium.customer.service.dto.CustIsvRefDTO;
import com.apptium.customer.service.mapper.CustIsvRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustIsvRef}.
 */
@Service
@Transactional
public class CustIsvRefServiceImpl implements CustIsvRefService {

    private final Logger log = LoggerFactory.getLogger(CustIsvRefServiceImpl.class);

    private final CustIsvRefRepository custIsvRefRepository;

    private final CustIsvRefMapper custIsvRefMapper;

    public CustIsvRefServiceImpl(CustIsvRefRepository custIsvRefRepository, CustIsvRefMapper custIsvRefMapper) {
        this.custIsvRefRepository = custIsvRefRepository;
        this.custIsvRefMapper = custIsvRefMapper;
    }

    @Override
    public CustIsvRefDTO save(CustIsvRefDTO custIsvRefDTO) {
        log.debug("Request to save CustIsvRef : {}", custIsvRefDTO);
        CustIsvRef custIsvRef = custIsvRefMapper.toEntity(custIsvRefDTO);
        custIsvRef = custIsvRefRepository.save(custIsvRef);
        return custIsvRefMapper.toDto(custIsvRef);
    }

    @Override
    public Optional<CustIsvRefDTO> partialUpdate(CustIsvRefDTO custIsvRefDTO) {
        log.debug("Request to partially update CustIsvRef : {}", custIsvRefDTO);

        return custIsvRefRepository
            .findById(custIsvRefDTO.getId())
            .map(
                existingCustIsvRef -> {
                    custIsvRefMapper.partialUpdate(existingCustIsvRef, custIsvRefDTO);

                    return existingCustIsvRef;
                }
            )
            .map(custIsvRefRepository::save)
            .map(custIsvRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustIsvRefDTO> findAll() {
        log.debug("Request to get all CustIsvRefs");
        return custIsvRefRepository.findAll().stream().map(custIsvRefMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustIsvRefDTO> findOne(Long id) {
        log.debug("Request to get CustIsvRef : {}", id);
        return custIsvRefRepository.findById(id).map(custIsvRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustIsvRef : {}", id);
        custIsvRefRepository.deleteById(id);
    }
}
