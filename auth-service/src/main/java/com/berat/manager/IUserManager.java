package com.berat.manager;

import com.berat.dto.request.CreateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.berat.constant.EndPoints.ACTIVESTATUS;
import static com.berat.constant.EndPoints.DELETEBYID;

@FeignClient(url = "http://localhost:7072/api/v1/user",decode404 = true,name = "auth-userprofile")
public interface IUserManager {
    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequest dto);

    @GetMapping(ACTIVESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deActivate(@RequestParam Long authId);
}
