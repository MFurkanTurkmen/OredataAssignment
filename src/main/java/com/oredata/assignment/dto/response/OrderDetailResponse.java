package com.oredata.assignment.dto.response;

import com.oredata.assignment.repository.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailResponse extends BaseResponseDto {
    private String email;
    private String name;
    private double totalPrice;
    private String orderDate;
    private List<BookResponseForOrderDetail> books;

}
