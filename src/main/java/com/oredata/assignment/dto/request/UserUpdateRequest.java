package com.oredata.assignment.dto.request;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {
    private String name;
    private String email;
}
