package com.oredata.assignment.controller;
import com.oredata.assignment.dto.request.BaseGetMethodRequest;
import com.oredata.assignment.dto.request.BookSaveRequest;
import com.oredata.assignment.dto.request.BookUpdateRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.BookResponse;
import com.oredata.assignment.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    @Operation(summary = "create a new book -> REQUIRED ROLE : ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponseDto> saveBook(@Valid @RequestBody BookSaveRequest dto){
        return ResponseEntity.ok(bookService.saveBook(dto));
    }


    @GetMapping("/{isbn}")
    @Operation(summary = "Get the book with ISBN -> (PUBLIC) ")
    public ResponseEntity<BaseResponseDto> getBookByIsbn(@PathVariable("isbn") String isbn){
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }
    @GetMapping("/")
    @Operation(summary = "get all books -> (PUBLIC) ",
            description = "If page number is exceeded, it does not return empty. It returns to the first page.")
    public ResponseEntity<List<BookResponse>> getAllBook(BaseGetMethodRequest dto){
        return ResponseEntity.ok(bookService.getAllBooks(dto));
    }


    @DeleteMapping("/isbn")
    @Operation(summary = "delete the book with given ISBN -> REQUIRED ROLE : ADMIN")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<BaseResponseDto> deleteBookByIsbn(@RequestParam String isbn){
        return ResponseEntity.ok(bookService.deleteBookByIsbn(isbn));
    }

    @PutMapping("/{isbn}")
    @Operation(summary = "Update the book with given ISBN -> REQUIRED ROLE : ADMIN")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public EntityModel<BaseResponseDto> updateBook(@PathVariable(value = "isbn") String isbn, @RequestBody BookUpdateRequest dto){
        Link bookDetailLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getBookByIsbn(isbn)).withRel("book detail");
        return EntityModel.of(bookService.updateBook(dto,isbn),bookDetailLink);
    }


}
