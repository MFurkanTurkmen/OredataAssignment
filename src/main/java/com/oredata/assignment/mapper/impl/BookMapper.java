package com.oredata.assignment.mapper.impl;

import com.oredata.assignment.dto.request.BookSaveRequest;
import com.oredata.assignment.dto.request.BookUpdateRequest;
import com.oredata.assignment.dto.response.BookResponse;
import com.oredata.assignment.mapper.IBookMapper;
import com.oredata.assignment.repository.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements IBookMapper {
    @Override
    public Book toBook(BookSaveRequest dto) {
        if (dto == null) return null;
        return Book.builder()
                .title(dto.getTitle())
                .stock(dto.getStock())
                .price(dto.getPrice())
                .isbn(dto.getIsbn())
                .author(dto.getAuthor())
                .build();
    }

    @Override
    public Book toBook(Book book,BookUpdateRequest dto) {
        if (dto == null) return null;
        if (dto.getAuthor()!=null  && !dto.getAuthor().isBlank()  ) {book.setAuthor(dto.getAuthor());}
        if (dto.getPrice()!=0d) book.setPrice(dto.getPrice());
        if (dto.getStock()!=0) book.setStock(dto.getStock());
        if (dto.getTitle()!=null && !dto.getTitle().isBlank() ) book.setTitle(dto.getTitle());
        return book;
    }

    @Override
    public BookResponse toBookResponse(Book book) {
        if (book==null) return null;
        return BookResponse.builder()
                .title(book.getTitle())
                .stock(book.getStock())
                .price(book.getPrice())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .message("ok")
                .statusCode(200)
                .build();
     }
}
