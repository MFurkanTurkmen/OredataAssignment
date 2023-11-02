package com.oredata.assignment.repository;

import com.oredata.assignment.dto.response.OrderResponse;
import com.oredata.assignment.repository.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
}
