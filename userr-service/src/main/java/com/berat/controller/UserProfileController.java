package com.berat.controller;

import static com.berat.constant.EndPoints.*;

import com.berat.dto.request.CreateUserRequest;
import com.berat.dto.request.UpdateUserRequest;
import com.berat.model.UserProfile;
import com.berat.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(ACTIVESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
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
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
    @GetMapping(FINDBYUSERNAME)
    public ResponseEntity<UserProfile> findByUsername(@RequestParam String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }
    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<UserProfile>> findByRole(@RequestParam String role){
        return ResponseEntity.ok(userProfileService.findByRole(role));
    }
}
