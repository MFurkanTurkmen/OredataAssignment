package com.oredata.assignment.controller;

import com.oredata.assignment.dto.request.BaseGetMethodRequest;
import com.oredata.assignment.dto.request.OrderSaveRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.OrderDetailResponse;
import com.oredata.assignment.dto.response.OrderResponse;
import com.oredata.assignment.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/")
    @Operation(summary = "save order with user_id and books ISBN -> REQUIRED ROLE : USER")
    @PreAuthorize("hasAuthority('USER')")
    public EntityModel<BaseResponseDto> saveOrder(@RequestBody OrderSaveRequest dto) {
        Link userDetail= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(dto.getUserId())).withRel("user detail");
        Set<Link> allDetails= dto.getBookISBN().stream()
                .map(isbn ->WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .getBookByIsbn(isbn)).withRel("book detail") ).collect(Collectors.toSet());
        allDetails.add(userDetail);
        return EntityModel.of(orderService.saveOrder(dto),allDetails);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get orders with user id -> ANY ROLE REQUIRED : USER , ADMIN , DEV")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','DEV')")
    public EntityModel<CollectionModel<OrderResponse>> getOrdersByUserId(@PathVariable("userId") Long userId, BaseGetMethodRequest request) {
        List<OrderResponse> orderResponses= orderService.getOrdersByUserId(userId,request);
        CollectionModel<OrderResponse> collectionModel=CollectionModel.of(orderResponses);
        List<Link> links=orderResponses.stream()
                .map(orderResponse -> WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                                .getOrderDetailById(orderResponse.getId()))
                        .withRel("order detail")).collect(Collectors.toList());
        return EntityModel.of(collectionModel,links);
    }

    @GetMapping("/details/{orderId}")
    @Operation(summary = "get order detail with order_id -> ANY ROLE REQUIRED : USER , ADMIN , DEV")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','DEV')")
    public ResponseEntity<OrderDetailResponse> getOrderDetailById(@PathVariable("orderId")   Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetailById(orderId));
    }





    }
