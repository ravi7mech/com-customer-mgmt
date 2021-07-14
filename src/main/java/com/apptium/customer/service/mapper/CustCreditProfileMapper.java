package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustCreditProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustCreditProfile} and its DTO {@link CustCreditProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustCreditProfileMapper extends EntityMapper<CustCreditProfileDTO, CustCreditProfile> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustCreditProfileDTO toDto(CustCreditProfile s);
}
