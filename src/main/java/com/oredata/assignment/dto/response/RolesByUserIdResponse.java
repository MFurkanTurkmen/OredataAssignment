package com.oredata.assignment.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolesByUserIdResponse extends BaseResponseDto {
    Set<RoleResponse> roleResponses;
}
