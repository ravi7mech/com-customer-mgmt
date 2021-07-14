package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustIsvCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustIsvChar} and its DTO {@link CustIsvCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustIsvRefMapper.class })
public interface CustIsvCharMapper extends EntityMapper<CustIsvCharDTO, CustIsvChar> {
    @Mapping(target = "custIsvRef", source = "custIsvRef", qualifiedByName = "id")
    CustIsvCharDTO toDto(CustIsvChar s);
}
