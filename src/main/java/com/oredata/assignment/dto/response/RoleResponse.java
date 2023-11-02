package com.oredata.assignment.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleResponse extends BaseResponseDto {
    private String name;
}
