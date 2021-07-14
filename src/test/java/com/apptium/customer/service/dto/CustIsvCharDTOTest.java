package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustIsvCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustIsvCharDTO.class);
        CustIsvCharDTO custIsvCharDTO1 = new CustIsvCharDTO();
        custIsvCharDTO1.setId(1L);
        CustIsvCharDTO custIsvCharDTO2 = new CustIsvCharDTO();
        assertThat(custIsvCharDTO1).isNotEqualTo(custIsvCharDTO2);
        custIsvCharDTO2.setId(custIsvCharDTO1.getId());
        assertThat(custIsvCharDTO1).isEqualTo(custIsvCharDTO2);
        custIsvCharDTO2.setId(2L);
        assertThat(custIsvCharDTO1).isNotEqualTo(custIsvCharDTO2);
        custIsvCharDTO1.setId(null);
        assertThat(custIsvCharDTO1).isNotEqualTo(custIsvCharDTO2);
    }
}
