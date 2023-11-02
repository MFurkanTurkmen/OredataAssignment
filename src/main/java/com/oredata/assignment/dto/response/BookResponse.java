package com.oredata.assignment.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponse extends BaseResponseDto{
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private long price;
}
