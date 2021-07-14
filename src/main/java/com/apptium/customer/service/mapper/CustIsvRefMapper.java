package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustIsvRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustIsvRef} and its DTO {@link CustIsvRefDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustIsvRefMapper extends EntityMapper<CustIsvRefDTO, CustIsvRef> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustIsvRefDTO toDto(CustIsvRef s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustIsvRefDTO toDtoId(CustIsvRef custIsvRef);
}
