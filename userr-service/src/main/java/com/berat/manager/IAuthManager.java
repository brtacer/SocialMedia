package com.berat.manager;

import com.berat.dto.request.UpdateAuthRequest;
import com.berat.dto.request.UpdateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berat.constant.EndPoints.FINDBYROLE;
import static com.berat.constant.EndPoints.UPDATE;

@FeignClient(url = "http://localhost:7071/api/v1/auth",decode404 = true,name = "auth-service")
public interface IAuthManager {
    @PutMapping(UPDATE)
    public ResponseEntity<Void> updateUserProfile(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateAuthRequest dto);
    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<Long>> findByRole(@RequestHeader(value = "Authorization") String token, @RequestParam String role);
}
