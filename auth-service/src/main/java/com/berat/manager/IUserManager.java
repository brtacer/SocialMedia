package com.berat.manager;

import com.berat.dto.request.ActivateStatusDto;
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

    @PostMapping(ACTIVESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestHeader(value = "Authorization") String token);
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deActivate(@RequestParam Long authId);
}
