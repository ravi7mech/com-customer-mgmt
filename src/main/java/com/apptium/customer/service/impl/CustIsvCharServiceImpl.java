package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustIsvChar;
import com.apptium.customer.repository.CustIsvCharRepository;
import com.apptium.customer.service.CustIsvCharService;
import com.apptium.customer.service.dto.CustIsvCharDTO;
import com.apptium.customer.service.mapper.CustIsvCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustIsvChar}.
 */
@Service
@Transactional
public class CustIsvCharServiceImpl implements CustIsvCharService {

    private final Logger log = LoggerFactory.getLogger(CustIsvCharServiceImpl.class);

    private final CustIsvCharRepository custIsvCharRepository;

    private final CustIsvCharMapper custIsvCharMapper;

    public CustIsvCharServiceImpl(CustIsvCharRepository custIsvCharRepository, CustIsvCharMapper custIsvCharMapper) {
        this.custIsvCharRepository = custIsvCharRepository;
        this.custIsvCharMapper = custIsvCharMapper;
    }

    @Override
    public CustIsvCharDTO save(CustIsvCharDTO custIsvCharDTO) {
        log.debug("Request to save CustIsvChar : {}", custIsvCharDTO);
        CustIsvChar custIsvChar = custIsvCharMapper.toEntity(custIsvCharDTO);
        custIsvChar = custIsvCharRepository.save(custIsvChar);
        return custIsvCharMapper.toDto(custIsvChar);
    }

    @Override
    public Optional<CustIsvCharDTO> partialUpdate(CustIsvCharDTO custIsvCharDTO) {
        log.debug("Request to partially update CustIsvChar : {}", custIsvCharDTO);

        return custIsvCharRepository
            .findById(custIsvCharDTO.getId())
            .map(
                existingCustIsvChar -> {
                    custIsvCharMapper.partialUpdate(existingCustIsvChar, custIsvCharDTO);

                    return existingCustIsvChar;
                }
            )
            .map(custIsvCharRepository::save)
            .map(custIsvCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustIsvCharDTO> findAll() {
        log.debug("Request to get all CustIsvChars");
        return custIsvCharRepository.findAll().stream().map(custIsvCharMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustIsvCharDTO> findOne(Long id) {
        log.debug("Request to get CustIsvChar : {}", id);
        return custIsvCharRepository.findById(id).map(custIsvCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustIsvChar : {}", id);
        custIsvCharRepository.deleteById(id);
    }
}
