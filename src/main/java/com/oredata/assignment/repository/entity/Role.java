package com.oredata.assignment.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_roles")
@Data
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;
}
