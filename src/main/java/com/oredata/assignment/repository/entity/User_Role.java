package com.oredata.assignment.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role")
@Data
public class User_Role extends BaseEntity{
    private Long userId;
    private Long roleId;
}
