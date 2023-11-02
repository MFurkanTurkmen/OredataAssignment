package com.oredata.assignment.service;

import com.oredata.assignment.dto.request.BaseGetMethodRequest;
import com.oredata.assignment.dto.request.OrderSaveRequest;
import com.oredata.assignment.dto.response.*;
import com.oredata.assignment.exception.ErrorType;
import com.oredata.assignment.exception.OredataException;
import com.oredata.assignment.repository.OrderRepository;
import com.oredata.assignment.repository.entity.Book;
import com.oredata.assignment.repository.entity.Order;
import com.oredata.assignment.repository.entity.UserProfile;
import com.oredata.assignment.utility.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService extends ServiceManager<Order,Long> {
    private final OrderRepository repository;
    private final BookService bookService;
    private final Order_Book_Service orderBookService;
    private final UserProfileService userProfileService;
    Logger logger = LoggerFactory.getLogger(OrderService.class);


    public OrderService(OrderRepository repository, BookService bookService, Order_Book_Service orderBookService, UserProfileService userProfileService) {
        super(repository);
        this.repository = repository;
        this.bookService = bookService;
        this.orderBookService = orderBookService;
        this.userProfileService = userProfileService;
    }
    // todo order ve order_book ile ilgili loglar tutulmadı
    public BaseResponseDto saveOrder(OrderSaveRequest dto){
        if (dto.getUserId()==null || dto.getUserId()==0 ) throw new OredataException(ErrorType.USER_NOT_FOUND);
        double totalPrice=0d;
        Book book=null;
        List<Book> books=new ArrayList<>();
        for (String isbn: dto.getBookISBN()){
            book=bookService.findOptionalByIsbn(isbn).orElseThrow(()-> new OredataException(ErrorType.BOOK_NOT_FOUND_WITH_ISBN));
            if (book.getStock()<1) throw new OredataException(ErrorType.BOOK_NOT_ADDED_INSUFFICIENT_STOCK);
            book.setStock(book.getStock()-1);
            totalPrice+=book.getPrice();
            books.add(book);
        }
        List<Book> updatedBooks=bookService.updateAll(books);
        if (totalPrice<25) throw new OredataException(ErrorType.MINIMUM_PRICE_FOR_ORDER);
        Order order = save(Order.builder()
                .userId(dto.getUserId())
                .orderDate(LocalDateTime.now().toString())
                .totalPrice(totalPrice)
                .build());
        logger.info("save order. {}",order);
        updatedBooks.stream().forEach(updatedBook -> orderBookService.saveOrderBook(order.getId(), updatedBook.getId()));
        return BaseResponseDto.builder().statusCode(200).message("ok").build();
    }

    public List<OrderResponse> getOrdersByUserId(Long userId, BaseGetMethodRequest dto) {
        Sort sort = Sort.by(Sort.Direction.ASC, "updateDate");

        int pageSize = dto.getPageSize() == 0 ? 5 : dto.getPageSize();
        Pageable pageable = PageRequest.of(dto.getPageNumber(), pageSize, sort);
        Page<Order> orderPage = repository.findByUserId(userId,pageable);
        if (orderPage.isEmpty()) {
            pageable = PageRequest.of(0, pageSize, sort);
            orderPage = repository.findByUserId(userId,pageable);
        }

        List<OrderResponse> orderResponses= orderPage.stream()
                .map(order -> OrderResponse.builder()
                        .orderDate(order.getOrderDate())
                        .totalPrice(order.getTotalPrice())
                        .id(order.getId())
                        .statusCode(200)
                        .message("ok")
                        .build()).collect(Collectors.toList());
        return orderResponses;
    }

    public OrderDetailResponse getOrderDetailById(Long orderId) {
        Order order=findById(orderId).orElseThrow(()-> new OredataException(ErrorType.ORDER_NOT_FOUND));
        UserProfile userProfile=userProfileService.findById(order.getUserId())
                .orElseThrow(()-> new OredataException(ErrorType.USER_NOT_FOUND));

        List<BookResponseForOrderDetail> bookResponseForOrderDetails= bookService.findBookForOrderId(orderId).stream()
                .map(book -> BookResponseForOrderDetail.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .price(book.getPrice())
                        .build()).collect(Collectors.toList());

        return OrderDetailResponse.builder()
                .message("ok")
                .statusCode(200)
                .name(userProfile.getName())
                .email(userProfile.getEmail())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .books(bookResponseForOrderDetails)
                .build();
    }
}
