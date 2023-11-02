package com.oredata.assignment.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileResponse extends BaseResponseDto {
    private String name;
    private String email;
}
