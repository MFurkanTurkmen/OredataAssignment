package com.oredata.assignment.dto.request;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseGetMethodRequest {
    private int pageSize;
    private int pageNumber;
}
