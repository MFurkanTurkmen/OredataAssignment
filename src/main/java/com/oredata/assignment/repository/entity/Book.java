package com.oredata.assignment.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_book")
@Data
public class Book extends BaseEntity{
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private int stock;
    private long price;
}
