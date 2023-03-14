package com.berat.service;

import com.berat.dto.request.ActivateRequest;
import com.berat.dto.request.LoginRequest;
import com.berat.dto.request.RegisterRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.exception.AuthManagerException;
import com.berat.exception.EErrorType;
import com.berat.mapper.IAuthMapper;
import com.berat.model.Auth;
import com.berat.model.enums.EStatus;
import com.berat.repository.IAuthRepository;
import com.berat.utility.CodeGenerator;
import com.berat.utility.ServiceManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public AuthResponse register(RegisterRequest dto){
       Auth auth=IAuthMapper.INSTANCE.toAuth(dto);
       auth.setActivationCode(CodeGenerator.generateCode());
       return IAuthMapper.INSTANCE.toAuthResponse(save(auth));
    }
    public AuthResponse login(LoginRequest dto){
        Optional<Auth> auth=authRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty())
            throw new AuthManagerException(EErrorType.LOGIN_ERROR);
        return IAuthMapper.INSTANCE.toAuthResponse(auth.get());
    }

    public Boolean activateStatus(ActivateRequest dto) {
        Optional<Auth> auth=findById(dto.getId());
        if (auth.isEmpty())
            throw new AuthManagerException(EErrorType.USER_NOT_FOUND);
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            return true;
        }else {
            throw new AuthManagerException(EErrorType.ACTIVATE_CODE_ERROR);
        }

    }
}
