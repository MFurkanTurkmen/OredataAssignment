package com.oredata.assignment.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Data
public class Order_Book extends BaseEntity{
    private Long orderId;
    private Long bookId;
}
