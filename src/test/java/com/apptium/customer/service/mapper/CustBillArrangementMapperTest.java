package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustBillArrangementMapperTest {

    private CustBillArrangementMapper custBillArrangementMapper;

    @BeforeEach
    public void setUp() {
        custBillArrangementMapper = new CustBillArrangementMapperImpl();
    }
}
