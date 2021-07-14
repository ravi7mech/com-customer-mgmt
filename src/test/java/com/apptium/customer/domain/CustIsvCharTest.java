package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustIsvCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustIsvChar.class);
        CustIsvChar custIsvChar1 = new CustIsvChar();
        custIsvChar1.setId(1L);
        CustIsvChar custIsvChar2 = new CustIsvChar();
        custIsvChar2.setId(custIsvChar1.getId());
        assertThat(custIsvChar1).isEqualTo(custIsvChar2);
        custIsvChar2.setId(2L);
        assertThat(custIsvChar1).isNotEqualTo(custIsvChar2);
        custIsvChar1.setId(null);
        assertThat(custIsvChar1).isNotEqualTo(custIsvChar2);
    }
}
