package com.univercenter.registry.users.controller;


import com.univercenter.registry.users.controller.dtos.SaveUserRequest;
import com.univercenter.registry.users.controller.dtos.UserRegistrationInfo;
import com.univercenter.registry.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final UserControllerHelper helper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserRegistrationInfo> save(
            @RequestBody final SaveUserRequest request
    ) {
        log.info("Save user request received");
        helper.validateSaveUserRequest(request);
        return ResponseEntity.status(CREATED).body(this.userService.save(request));
    }
}