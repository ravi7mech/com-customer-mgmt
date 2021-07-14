package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustIsvRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustIsvRef.class);
        CustIsvRef custIsvRef1 = new CustIsvRef();
        custIsvRef1.setId(1L);
        CustIsvRef custIsvRef2 = new CustIsvRef();
        custIsvRef2.setId(custIsvRef1.getId());
        assertThat(custIsvRef1).isEqualTo(custIsvRef2);
        custIsvRef2.setId(2L);
        assertThat(custIsvRef1).isNotEqualTo(custIsvRef2);
        custIsvRef1.setId(null);
        assertThat(custIsvRef1).isNotEqualTo(custIsvRef2);
    }
}
