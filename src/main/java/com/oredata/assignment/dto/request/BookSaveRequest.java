package com.oredata.assignment.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookSaveRequest {
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private int stock;
    private long price;
}
