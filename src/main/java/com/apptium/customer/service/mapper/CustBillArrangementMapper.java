package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustBillArrangementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustBillArrangement} and its DTO {@link CustBillArrangementDTO}.
 */
@Mapper(componentModel = "spring", uses = { AutoPayMapper.class })
public interface CustBillArrangementMapper extends EntityMapper<CustBillArrangementDTO, CustBillArrangement> {
    @Mapping(target = "autoPay", source = "autoPay", qualifiedByName = "id")
    CustBillArrangementDTO toDto(CustBillArrangement s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustBillArrangementDTO toDtoId(CustBillArrangement custBillArrangement);
}
