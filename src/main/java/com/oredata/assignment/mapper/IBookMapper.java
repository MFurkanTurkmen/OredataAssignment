package com.oredata.assignment.mapper;

import com.oredata.assignment.dto.request.BookSaveRequest;
import com.oredata.assignment.dto.request.BookUpdateRequest;
import com.oredata.assignment.dto.response.BookResponse;
import com.oredata.assignment.repository.entity.Book;

public interface IBookMapper {
    Book toBook(BookSaveRequest dto);
    Book toBook(Book book,BookUpdateRequest dto);

    BookResponse toBookResponse(Book book);
}
