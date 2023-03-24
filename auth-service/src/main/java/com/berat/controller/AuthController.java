package com.berat.controller;

import static com.berat.constant.EndPoints.*;
import com.berat.dto.request.ActivateRequest;
import com.berat.dto.request.LoginRequest;
import com.berat.dto.request.RegisterRequest;
import com.berat.dto.request.UpdateAuthRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.exception.AuthManagerException;
import com.berat.exception.EErrorType;
import com.berat.model.Auth;
import com.berat.model.enums.ERole;
import com.berat.service.AuthService;
import com.berat.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenManager tokenManager;
    private final CacheManager cacheManager;

    @PostMapping(REGISTER)
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(REGISTER+"2")
    public ResponseEntity<AuthResponse> registerWithRabbit(@RequestBody @Valid RegisterRequest dto){
        return ResponseEntity.ok(authService.registerWithRabbit(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequest dto){

        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping(ACTIVESTATUS)
    public ResponseEntity<?> activateStatus(@RequestBody ActivateRequest dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Void> updateUserProfile(@RequestBody UpdateAuthRequest dto){
        authService.updateAuth(dto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deActivate(@RequestParam Long id){
        return ResponseEntity.ok(authService.deActivate(id));
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getrolefromtoken")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenManager.getRoleFromToken(token).get());
    }
    @GetMapping("/redis")
    @Cacheable(value = "redisexample")
    public String redisExample(String value){
        try {
            Thread.sleep(2000);
            return value;
        }catch (Exception exception){
            throw new AuthManagerException(EErrorType.USER_NOT_FOUND);
        }
    }
    @GetMapping("/redisdelete")
    @CacheEvict(cacheNames = "redisexample",allEntries = true)
    public void redisDelete(){
    }
    @GetMapping("/redisdelete2")
    public Boolean redisDelete2(){
        try {
            //cacheManager.getCache("redisexample").clear();// hepsini silecek yukardakiyle aynı
            cacheManager.getCache("redisexample").evict("Mustafa");// Mustafa cache ini sil.
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return false;
    }

    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<Long>> findByRole(@RequestParam String role){
        return ResponseEntity.ok(authService.findByRole(role));
    }

}
