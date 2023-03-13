package com.berat.service;

import com.berat.dto.request.RegisterRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.mapper.IAuthMapper;
import com.berat.model.Auth;
import com.berat.repository.IAuthRepository;
import com.berat.utility.CodeGenerator;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public AuthResponse register(RegisterRequest dto){
       Auth auth=save(IAuthMapper.INSTANCE.toAuth(dto));
       auth.setActivationCode(CodeGenerator.generateCode());
       return IAuthMapper.INSTANCE.toAuthResponse(auth);
    }
}
