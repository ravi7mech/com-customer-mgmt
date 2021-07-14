package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustContactCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustContactChar} and its DTO {@link CustContactCharDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustContactCharMapper extends EntityMapper<CustContactCharDTO, CustContactChar> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustContactCharDTO toDtoId(CustContactChar custContactChar);
}
