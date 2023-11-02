package com.oredata.assignment.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
@Data
public class UserProfile extends BaseEntity {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
}
