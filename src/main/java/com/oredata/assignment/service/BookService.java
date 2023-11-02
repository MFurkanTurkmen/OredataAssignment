package com.oredata.assignment.service;

import com.oredata.assignment.dto.request.BaseGetMethodRequest;
import com.oredata.assignment.dto.request.BookSaveRequest;
import com.oredata.assignment.dto.request.BookUpdateRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.BookResponse;
import com.oredata.assignment.exception.ErrorType;
import com.oredata.assignment.exception.OredataException;
import com.oredata.assignment.mapper.IBookMapper;
import com.oredata.assignment.repository.BookRepository;
import com.oredata.assignment.repository.entity.Book;
import com.oredata.assignment.utility.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService extends ServiceManager<Book,Long> {
    private final BookRepository repository;
    private final IBookMapper bookMapper;
    private final Order_Book_Service orderBookService;
    Logger logger = LoggerFactory.getLogger(BookService.class);
    public BookService(BookRepository repository, IBookMapper bookMapper, Order_Book_Service orderBookService) {
        super(repository);
        this.repository = repository;
        this.bookMapper = bookMapper;
        this.orderBookService = orderBookService;
    }
    public BaseResponseDto saveBook(BookSaveRequest dto){
        if (repository.existsByIsbn(dto.getIsbn())) throw new OredataException(ErrorType.BOOK_ALREADY_EXISTS);
        Book book=save(bookMapper.toBook(dto));
        logger.info("Book saved. {}",book);
        return BaseResponseDto.builder().message("ok").statusCode(200).build();
    }

    public BookResponse getBookByIsbn(String isbn){
        Book book=repository.findOptionalByIsbn(isbn).orElseThrow(()->new OredataException(ErrorType.BOOK_NOT_FOUND_WITH_ISBN));
        return bookMapper.toBookResponse(book);
    }
    public List<BookResponse> getAllBooks(BaseGetMethodRequest dto){

        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        int pageSize = dto.getPageSize() == 0 ? 10 : dto.getPageSize();
        Pageable pageable = PageRequest.of(dto.getPageNumber(), pageSize, sort);
        Page<Book> booksPage = repository.findAll(pageable);
        if (booksPage.isEmpty()) {
            pageable = PageRequest.of(0, pageSize, sort);
            booksPage = repository.findAll(pageable);
        }
        List<BookResponse> bookResponses = booksPage.stream().map(x->bookMapper.toBookResponse(x)).collect(Collectors.toList());
        return bookResponses;
    }

    public BaseResponseDto deleteBookByIsbn(String isbn) {

        Book book= repository.findOptionalByIsbn(isbn).orElseThrow(()-> new OredataException(ErrorType.BOOK_NOT_FOUND_WITH_ISBN));
        if (!orderBookService.getOrderBookByBookId(book.getId()).isEmpty()){
            throw new OredataException(ErrorType.BOOK_ALREADY_IN_USE);
        }
        delete(book);
        logger.info("Book Deleted. {}",book);
        return BaseResponseDto.builder().message("ok").statusCode(200).build();
    }
    public BaseResponseDto updateBook(BookUpdateRequest dto,String isbn){
        Book book= repository.findOptionalByIsbn(isbn).orElseThrow(()-> new OredataException(ErrorType.BOOK_NOT_FOUND_WITH_ISBN));
        book=bookMapper.toBook(book,dto);
        update(book);
        logger.info("Book Updated. {}",book);

        return BaseResponseDto.builder().statusCode(200).message("ok").build();

    }

    public Optional<Book> findOptionalByIsbn(String isbn) {
        return repository.findOptionalByIsbn(isbn);
    }

    public List<Book> findBookForOrderId(Long orderId){
        return repository.findBookForOrderId2(orderId);
    }
}
