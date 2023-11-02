package com.oredata.assignment.dto.request;

import com.oredata.assignment.repository.entity.Role;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
}
