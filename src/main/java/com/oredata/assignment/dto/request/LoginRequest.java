package com.oredata.assignment.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String email;
    private String password;
}
