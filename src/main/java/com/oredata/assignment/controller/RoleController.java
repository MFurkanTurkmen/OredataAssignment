package com.oredata.assignment.controller;

import com.oredata.assignment.dto.request.RoleSaveRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.RolesByUserIdResponse;
import com.oredata.assignment.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/role")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/")
    @Operation(summary = "create a new role -> ANY ROLE REQUIRED : ADMIN , DEV")
    @PreAuthorize("hasAnyAuthority('ADMIN','DEV')")
    public ResponseEntity<BaseResponseDto> saveRole(@RequestBody RoleSaveRequest dto) {
        return ResponseEntity.ok(roleService.saveRole(dto));
    }

    @GetMapping("/get-roles-by-userId/{userId}")
    @Operation(summary = "get roles with User_id -> ANY ROLE REQUIRED : ADMIN , DEV"
            ,description = "Returns roles of the user with id.")
    @PreAuthorize("hasAnyAuthority('ADMIN','DEV')")
    public ResponseEntity<RolesByUserIdResponse> getRolesByUserId(@PathVariable("userId") long userId){
        return ResponseEntity.ok(roleService.getRolesByUserId(userId));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete role with role_id -> REQUIRED ROLE : DEV"
            ,description = "delete role given id")
    @PreAuthorize("hasAnyAuthority('DEV')")
    public ResponseEntity<BaseResponseDto> deleteRole(@PathVariable("id") long id){
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

}
