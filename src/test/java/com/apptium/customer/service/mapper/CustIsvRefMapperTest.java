package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustIsvRefMapperTest {

    private CustIsvRefMapper custIsvRefMapper;

    @BeforeEach
    public void setUp() {
        custIsvRefMapper = new CustIsvRefMapperImpl();
    }
}
