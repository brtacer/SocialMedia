package com.berat.service;

import com.berat.dto.request.ActivateRequest;
import com.berat.dto.request.LoginRequest;
import com.berat.dto.request.RegisterRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.exception.AuthManagerException;
import com.berat.exception.EErrorType;
import com.berat.manager.IUserManager;
import com.berat.mapper.IAuthMapper;
import com.berat.model.Auth;
import com.berat.model.enums.EStatus;
import com.berat.repository.IAuthRepository;
import com.berat.utility.CodeGenerator;
import com.berat.utility.JwtTokenManager;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final JwtTokenManager tokenManager;

    public AuthService(IAuthRepository authRepository, IUserManager userManager, JwtTokenManager tokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
    }

    public AuthResponse register(RegisterRequest dto){
       Auth auth=IAuthMapper.INSTANCE.toAuth(dto);
       auth.setActivationCode(CodeGenerator.generateCode());
       save(auth);
       userManager.createUser(IAuthMapper.INSTANCE.toCreateUserRequest(auth));
       return IAuthMapper.INSTANCE.toAuthResponse(auth);
    }
    public String login(LoginRequest dto){
        Optional<Auth> auth=authRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());

        if (auth.isEmpty() || !auth.get().getStatus().equals(EStatus.ACTIVE))
            throw new AuthManagerException(EErrorType.LOGIN_ERROR);
        if (!auth.get().getStatus().equals(EStatus.ACTIVE))
            throw new AuthManagerException(EErrorType.NOT_ACTIVE_ACCOUNT);
        return tokenManager.createToken(auth.get().getId(),auth.get().getRole())
                .orElseThrow(()-> {throw new AuthManagerException(EErrorType.TOKEN_NOT_CREATED);});
    }

    public Boolean activateStatus(ActivateRequest dto) {
        Optional<Auth> auth=findById(dto.getId());
        if (auth.isEmpty())
            throw new AuthManagerException(EErrorType.USER_NOT_FOUND);
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activateStatus(dto.getId());
            return true;
        }else {
            throw new AuthManagerException(EErrorType.ACTIVATE_CODE_ERROR);
        }

    }
}
