package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustIsvCharMapperTest {

    private CustIsvCharMapper custIsvCharMapper;

    @BeforeEach
    public void setUp() {
        custIsvCharMapper = new CustIsvCharMapperImpl();
    }
}
