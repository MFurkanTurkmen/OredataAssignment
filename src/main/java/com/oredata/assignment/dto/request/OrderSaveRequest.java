package com.oredata.assignment.dto.request;

import com.oredata.assignment.repository.entity.BaseEntity;
import com.oredata.assignment.repository.entity.Book;
import lombok.*;

import javax.persistence.ManyToMany;
import java.util.HashMap;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderSaveRequest {
    private Long userId;
    private List<String> bookISBN;
}
