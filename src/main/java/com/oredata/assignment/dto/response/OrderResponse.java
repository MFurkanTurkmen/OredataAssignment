package com.oredata.assignment.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse extends BaseResponseDto {
    private long id;
    private double totalPrice;
    private String orderDate;
}
