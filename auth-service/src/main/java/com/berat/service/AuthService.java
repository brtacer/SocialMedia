package com.berat.service;

import com.berat.dto.request.*;
import com.berat.dto.response.AuthResponse;
import com.berat.exception.AuthManagerException;
import com.berat.exception.EErrorType;
import com.berat.manager.IUserManager;
import com.berat.mapper.IAuthMapper;
import com.berat.model.Auth;
import com.berat.model.enums.ERole;
import com.berat.model.enums.EStatus;
import com.berat.rabbitmq.producer.RegisterMailProducer;
import com.berat.rabbitmq.producer.RegisterProducer;
import com.berat.repository.IAuthRepository;
import com.berat.utility.CodeGenerator;
import com.berat.utility.JwtTokenManager;
import com.berat.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final JwtTokenManager tokenManager;
    private final CacheManager cacheManager;
    private final RegisterProducer registerProducer;
    private final RegisterMailProducer registerMailProducer;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IAuthRepository authRepository, IUserManager userManager,
                       JwtTokenManager tokenManager, CacheManager cacheManager,
                       RegisterProducer registerProducer, RegisterMailProducer registerMailProducer, PasswordEncoder passwordEncoder) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
        this.cacheManager = cacheManager;
        this.registerProducer = registerProducer;
        this.registerMailProducer = registerMailProducer;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional// işlemi geri alıyor gerçekleşmezse hata görürse
    public AuthResponse register(RegisterRequest dto){
       Auth auth=IAuthMapper.INSTANCE.toAuth(dto);
       auth.setActivationCode(CodeGenerator.generateCode());

       try {
           save(auth);
           userManager.createUser(IAuthMapper.INSTANCE.toCreateUserRequest(auth));
           cacheManager.getCache("findbyrole").evict(auth.getRole().toString().toUpperCase());
       }catch (Exception exception){
           throw new AuthManagerException(EErrorType.USER_NOT_CREATED);
       }
       return IAuthMapper.INSTANCE.toAuthResponse(auth);
    }
    @Transactional
    public AuthResponse registerWithRabbit(RegisterRequest dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Auth auth=IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());

        try {
            save(auth);
            registerProducer.sendNewUser(IAuthMapper.INSTANCE.toRegisterModel(auth));
            registerMailProducer.sendMail(IAuthMapper.INSTANCE.toRegisterMailModel(auth));
            cacheManager.getCache("findbyrole").evict(auth.getRole().toString().toUpperCase());
        }catch (Exception exception){
            throw new AuthManagerException(EErrorType.USER_NOT_CREATED);
        }
        return IAuthMapper.INSTANCE.toAuthResponse(auth);
    }
    public String login(LoginRequest dto){
        Optional<Auth> auth=authRepository.findByUsername(dto.getUsername());


        if (auth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), auth.get().getPassword()))
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
            String token = tokenManager.createToken(auth.get().getId(),auth.get().getRole()).get();
            userManager.activateStatus("Bearer "+token);
            return true;
        }else {
            throw new AuthManagerException(EErrorType.ACTIVATE_CODE_ERROR);
        }
    }
    public Boolean updateAuth(UpdateAuthRequest dto){
        Optional<Auth> auth=findById(dto.getAuthId());
        if (auth.isEmpty())
            throw new AuthManagerException(EErrorType.USER_NOT_FOUND);
        auth.get().setEmail(dto.getEmail());
        auth.get().setUsername(dto.getUsername());
        update(auth.get());
        return true;
    }
    public Boolean deActivate(Long id){
        Optional<Auth> auth=findById(id);
        if (auth.isEmpty())
            throw new AuthManagerException(EErrorType.USER_NOT_FOUND);
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        userManager.deActivate(id);
        return true;
    }
    public List<Long> findByRole(String role){
        ERole myrole;
        try {
            myrole = ERole.valueOf(role.toUpperCase(Locale.ENGLISH));
        }catch (Exception exception){
            throw new AuthManagerException(EErrorType.ROLE_NOT_FOUND);
        }
        return authRepository.findAllByRole(myrole).stream()
                .map(a-> a.getId()).toList();
    }
}
