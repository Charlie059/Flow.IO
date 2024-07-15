package org.flowio.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BusinessTypeDto {
    private Integer id;
    private String name;
}
