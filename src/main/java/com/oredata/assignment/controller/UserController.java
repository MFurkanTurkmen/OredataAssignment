package com.oredata.assignment.controller;

import com.oredata.assignment.dto.request.LoginRequest;
import com.oredata.assignment.dto.request.SignUpRequest;
import com.oredata.assignment.dto.request.UserUpdateRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.LoginResponse;
import com.oredata.assignment.dto.response.UserProfileResponse;
import com.oredata.assignment.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserProfileService userProfileService;
    @PostMapping("/signup")
    @Operation(summary = "Create a new user -> PUBLIC")
    public ResponseEntity<BaseResponseDto> createUser(@RequestBody SignUpRequest dto){
        return ResponseEntity.ok(userProfileService.signup(dto));
    }
    @PostMapping("/login")
    @Operation(summary = "Login User -> PUBLIC")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok(userProfileService.login(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get user with user_id -> PUBLIC")
    public EntityModel<UserProfileResponse> getUserById(@PathVariable Long id) {
        Link profileLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getRolesByUserId(id)).withRel("roles");
        Link orderLink= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrdersByUserId(id,null)).withRel("orders");
        return EntityModel.of(userProfileService.getUserProfileById(id),profileLink,orderLink);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update user with user_id -> ANY ROLE REQUIRED  : ADMIN , DEV")
    @PreAuthorize("hasAnyAuthority('ADMIN','DEV')")
    public ResponseEntity<BaseResponseDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest dto) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, dto));
    }


}
