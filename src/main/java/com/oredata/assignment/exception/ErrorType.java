package com.oredata.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

    INTERNAL_SERVER_ERROR(1000,"An Unknown error has occurred on the server", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST_ERROR(1001,"Bad Request",HttpStatus.BAD_REQUEST),
    WRONG_TOKEN(1002,"Wrong Token",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ERROR(1003,"Unauthorized",HttpStatus.UNAUTHORIZED),
    UNKNOWN_ERROR(1004,"We cannot respond to your request at this time. Our developments continue.", HttpStatus.INTERNAL_SERVER_ERROR),
    RATE_LIMIT(1005,"!!! Request limit !!! ",HttpStatus.BAD_REQUEST),

    // ALREADY
    BOOK_ALREADY_EXISTS(1101,"There is already a book for this ISBN",HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1102,"Email is already exists",HttpStatus.BAD_REQUEST),
    ROLE_ALREADY_EXISTS(1103,"Role is Already Exists",HttpStatus.BAD_REQUEST),
    BOOK_ALREADY_IN_USE(1104,"You cannot delete the book because it is used. but you can set the book stock to 0.",HttpStatus.BAD_REQUEST),
    // NOT FOUND
    BOOK_NOT_FOUND_WITH_ID(1201,"No book with this ID has been found.",HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND_WITH_ISBN(1202,"No book with this ISBN has been found.",HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND_WITH_ID_OR_ISBN(1203,"No book with this ID or ISBN has been found.",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1204,"User Not Found",HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1205,"Role Not Found",HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1206,"Order Not Found",HttpStatus.BAD_REQUEST),
    MAIL_NOT_FOUND (1207,"Mail Not Found",HttpStatus.BAD_REQUEST),
    // WRONG
    PASSWORD_NOT_MATCH(1208,"Password Not Match",HttpStatus.BAD_REQUEST),
    // NOT ADDED
    USER_NOT_ADDED(1301,"User Not Added",HttpStatus.BAD_REQUEST),
    BOOK_NOT_ADDED(1302,"Book Not Added",HttpStatus.BAD_REQUEST),
    BOOK_NOT_ADDED_INSUFFICIENT_STOCK(1303,"Insufficient stock For book.",HttpStatus.BAD_REQUEST),

    // OTHER
    MINIMUM_PRICE_FOR_ORDER(1401,"Minimum Price For Order is $25",HttpStatus.BAD_REQUEST);




    int code;
    String message;
    HttpStatus httpStatus;
}