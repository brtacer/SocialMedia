package com.berat.controller;

import com.berat.dto.request.ActivateRequest;
import com.berat.dto.request.LoginRequest;
import com.berat.dto.request.RegisterRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest dto){
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("/activatestatus")
    public ResponseEntity<?> activateStatus(@RequestBody ActivateRequest dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }


}
