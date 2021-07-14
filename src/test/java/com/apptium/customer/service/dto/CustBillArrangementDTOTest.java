package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustBillArrangementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustBillArrangementDTO.class);
        CustBillArrangementDTO custBillArrangementDTO1 = new CustBillArrangementDTO();
        custBillArrangementDTO1.setId(1L);
        CustBillArrangementDTO custBillArrangementDTO2 = new CustBillArrangementDTO();
        assertThat(custBillArrangementDTO1).isNotEqualTo(custBillArrangementDTO2);
        custBillArrangementDTO2.setId(custBillArrangementDTO1.getId());
        assertThat(custBillArrangementDTO1).isEqualTo(custBillArrangementDTO2);
        custBillArrangementDTO2.setId(2L);
        assertThat(custBillArrangementDTO1).isNotEqualTo(custBillArrangementDTO2);
        custBillArrangementDTO1.setId(null);
        assertThat(custBillArrangementDTO1).isNotEqualTo(custBillArrangementDTO2);
    }
}
