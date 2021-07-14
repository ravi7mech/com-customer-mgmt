package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustIsvRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustIsvRefDTO.class);
        CustIsvRefDTO custIsvRefDTO1 = new CustIsvRefDTO();
        custIsvRefDTO1.setId(1L);
        CustIsvRefDTO custIsvRefDTO2 = new CustIsvRefDTO();
        assertThat(custIsvRefDTO1).isNotEqualTo(custIsvRefDTO2);
        custIsvRefDTO2.setId(custIsvRefDTO1.getId());
        assertThat(custIsvRefDTO1).isEqualTo(custIsvRefDTO2);
        custIsvRefDTO2.setId(2L);
        assertThat(custIsvRefDTO1).isNotEqualTo(custIsvRefDTO2);
        custIsvRefDTO1.setId(null);
        assertThat(custIsvRefDTO1).isNotEqualTo(custIsvRefDTO2);
    }
}
