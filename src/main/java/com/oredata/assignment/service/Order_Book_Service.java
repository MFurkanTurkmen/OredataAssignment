package com.oredata.assignment.service;

import com.oredata.assignment.repository.Order_Book_Repository;
import com.oredata.assignment.repository.entity.Book;
import com.oredata.assignment.repository.entity.Order_Book;
import com.oredata.assignment.utility.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Order_Book_Service extends ServiceManager<Order_Book,Long> {
    private final Order_Book_Repository repository;

    public Order_Book_Service(Order_Book_Repository repository) {
        super(repository);
        this.repository = repository;
    }
    public void saveOrderBook(Long orderId,Long bookId ){
        save(Order_Book.builder().bookId(bookId).orderId(orderId).build());
    }
    public List<Order_Book> getOrderBookByBookId(Long bookId){
       return repository.findByBookId(bookId);
    }



}
