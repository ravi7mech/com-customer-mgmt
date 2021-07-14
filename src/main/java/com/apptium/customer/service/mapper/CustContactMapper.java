package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustContactDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustContact} and its DTO {@link CustContactDTO}.
 */
@Mapper(componentModel = "spring", uses = { GeographicSiteRefMapper.class, CustContactCharMapper.class, CustomerMapper.class })
public interface CustContactMapper extends EntityMapper<CustContactDTO, CustContact> {
    @Mapping(target = "geographicSiteRef", source = "geographicSiteRef", qualifiedByName = "id")
    @Mapping(target = "custContactChar", source = "custContactChar", qualifiedByName = "id")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustContactDTO toDto(CustContact s);
}
