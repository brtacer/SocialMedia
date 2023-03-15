package com.berat.controller;

import static com.berat.constant.EndPoints.*;

import com.berat.dto.request.CreateUserRequest;
import com.berat.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequest dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }
    @GetMapping(ACTIVESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }
}
