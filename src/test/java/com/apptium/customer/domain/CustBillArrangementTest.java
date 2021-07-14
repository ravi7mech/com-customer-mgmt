package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustBillArrangementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustBillArrangement.class);
        CustBillArrangement custBillArrangement1 = new CustBillArrangement();
        custBillArrangement1.setId(1L);
        CustBillArrangement custBillArrangement2 = new CustBillArrangement();
        custBillArrangement2.setId(custBillArrangement1.getId());
        assertThat(custBillArrangement1).isEqualTo(custBillArrangement2);
        custBillArrangement2.setId(2L);
        assertThat(custBillArrangement1).isNotEqualTo(custBillArrangement2);
        custBillArrangement1.setId(null);
        assertThat(custBillArrangement1).isNotEqualTo(custBillArrangement2);
    }
}
