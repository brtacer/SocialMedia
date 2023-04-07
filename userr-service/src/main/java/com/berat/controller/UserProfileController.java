package com.berat.controller;

import static com.berat.constant.EndPoints.*;

import com.berat.dto.request.CreateUserRequest;
import com.berat.dto.request.UpdateUserRequest;
import com.berat.model.UserProfile;
import com.berat.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequest dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }
    @PostMapping(ACTIVESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(userProfileService.activateStatus(token));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateUserProfile(@RequestBody UpdateUserRequest dto){
        return ResponseEntity.ok(userProfileService.updateUserProfile(dto));
    }
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deActive(@RequestParam Long authId){
        return ResponseEntity.ok(userProfileService.deActive(authId));
    }
    @GetMapping(FINDALL)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
    @GetMapping(FINDBYUSERNAME)
    public ResponseEntity<UserProfile> findByUsername(@RequestParam String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }
    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<UserProfile>> findByRole(@RequestHeader(value = "Authorization") String token, @RequestParam String role){
        return ResponseEntity.ok(userProfileService.findByRole(role, token));
    }
}
