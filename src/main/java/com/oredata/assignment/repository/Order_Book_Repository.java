package com.oredata.assignment.repository;

import com.oredata.assignment.repository.entity.Book;
import com.oredata.assignment.repository.entity.Order_Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

@Repository
public interface Order_Book_Repository extends JpaRepository<Order_Book,Long> {
    List<Order_Book> findByBookId(Long bookId);


}
