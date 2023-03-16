package com.berat.controller;

import static com.berat.constant.EndPoints.*;
import com.berat.dto.request.ActivateRequest;
import com.berat.dto.request.LoginRequest;
import com.berat.dto.request.RegisterRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.model.Auth;
import com.berat.model.enums.ERole;
import com.berat.service.AuthService;
import com.berat.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenManager tokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequest dto){

        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping(ACTIVESTATUS)
    public ResponseEntity<?> activateStatus(@RequestBody ActivateRequest dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }

    @GetMapping("/createtoken")
    public ResponseEntity<String> createToken(Long id, ERole role){
        return ResponseEntity.ok(tokenManager.createToken(id,role).get());
    }
    @GetMapping("/createtoken2")
    public ResponseEntity<String> createToken2(Long id){
        return ResponseEntity.ok(tokenManager.createToken(id).get());
    }
    @GetMapping("/getidfromtoken")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(tokenManager.getIdFromToken(token).get());
    }
    @GetMapping("/getrolefromtoken")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenManager.getRoleFromToken(token).get());
    }

}
