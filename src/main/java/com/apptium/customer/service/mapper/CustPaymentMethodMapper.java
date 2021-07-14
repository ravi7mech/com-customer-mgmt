package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustPaymentMethodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustPaymentMethod} and its DTO {@link CustPaymentMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustPaymentMethodMapper extends EntityMapper<CustPaymentMethodDTO, CustPaymentMethod> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustPaymentMethodDTO toDtoId(CustPaymentMethod custPaymentMethod);
}
