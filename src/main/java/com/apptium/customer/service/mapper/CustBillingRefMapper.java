package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustBillingRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustBillingRef} and its DTO {@link CustBillingRefDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class, CustPaymentMethodMapper.class })
public interface CustBillingRefMapper extends EntityMapper<CustBillingRefDTO, CustBillingRef> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    @Mapping(target = "custPaymentMethod", source = "custPaymentMethod", qualifiedByName = "id")
    CustBillingRefDTO toDto(CustBillingRef s);
}
