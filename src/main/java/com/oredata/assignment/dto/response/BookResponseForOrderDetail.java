package com.oredata.assignment.dto.response;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseForOrderDetail {
    private String title;
    private String author;
    private long price;
}
