package com.oredata.assignment.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateRequest {
    private String title;
    private String author;
    private int stock;
    private long price;
}
