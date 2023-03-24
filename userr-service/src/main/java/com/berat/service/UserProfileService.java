package com.berat.service;

import com.berat.dto.request.CreateUserRequest;
import com.berat.dto.request.UpdateAuthRequest;
import com.berat.dto.request.UpdateUserRequest;
import com.berat.exception.UserManagerException;
import com.berat.manager.IAuthManager;
import com.berat.mapper.IUserMapper;
import com.berat.model.UserProfile;
import com.berat.model.enums.EStatus;
import com.berat.rabbitmq.model.RegisterModel;
import com.berat.rabbitmq.producer.RegisterProducer;
import com.berat.repository.IUserProfileRepository;
import com.berat.utility.JwtTokenManager;
import com.berat.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.berat.exception.EErrorType.*;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenManager tokenManager;
    private final IAuthManager authManager;
    private final CacheManager cacheManager;
    private final RegisterProducer registerProducer;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenManager tokenManager, IAuthManager authManager, CacheManager cacheManager, RegisterProducer registerProducer) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
        this.registerProducer = registerProducer;
    }

    public Boolean createUser(CreateUserRequest dto) {
        if (Objects.isNull(save(IUserMapper.INSTANCE.toUser(dto))))
            throw new UserManagerException(USER_NOT_CREATED);
        return true;
    }
    public Boolean createUserWithRabbit(RegisterModel model) {
        UserProfile profile=save(IUserMapper.INSTANCE.toUser(model));
        if (Objects.isNull(profile))
            throw new UserManagerException(USER_NOT_CREATED);
        registerProducer.sendNewUser(IUserMapper.INSTANCE.toRegisterElasticModel(profile));
        return true;
    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile =userProfileRepository.findByAuthId(authId);
        if (userProfile.isEmpty())
            throw new UserManagerException(USER_NOT_FOUND);
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }
    public Boolean updateUserProfile(UpdateUserRequest dto){
        Optional<Long> authId=tokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserManagerException(INVALID_TOKEN);

        Optional<UserProfile> userProfile=userProfileRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty())
            throw new UserManagerException(USER_NOT_FOUND);

        UserProfile toUpdate=userProfile.get();

        cacheManager.getCache("findbyusername").evict(toUpdate.getUsername().toLowerCase());

        if (!dto.getUsername().equals(toUpdate.getUsername()) ||
                !dto.getEmail().equals(toUpdate.getEmail())){

            toUpdate.setEmail(dto.getEmail());
            toUpdate.setUsername(dto.getUsername());

            authManager.updateUserProfile(UpdateAuthRequest.builder()
                    .authId(authId.get())
                    .email(dto.getEmail())
                    .username(dto.getUsername())
                    .build());
        }

        toUpdate.setPhone(dto.getPhone());
        toUpdate.setAvatar(dto.getAvatar());
        toUpdate.setAddress(dto.getAddress());
        toUpdate.setAbout(dto.getAbout());
        update(toUpdate);

        return true;

    }

    public Boolean deActive(Long authId) {
        Optional<UserProfile> userProfile=userProfileRepository.findByAuthId(authId);
        if (userProfile.isEmpty())
            throw new UserManagerException(USER_NOT_FOUND);
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }
    @Cacheable(value = "findbyusername",key = "#username.toLowerCase()") // küçük büyük duyarlı olmasın
    public UserProfile findByUsername(String username){
        return userProfileRepository.findByUsernameIgnoreCase(username).orElseThrow(()-> new UserManagerException(USER_NOT_FOUND));
    }
    @Cacheable(value = "findbyrole",key = "#role.toUpperCase()") // küçük büyük duyarlı olmasın
    public List<UserProfile> findByRole(String role){
        List<Long> authIds = authManager.findByRole(role).getBody();// response entity dönüyor get body dedik.
        return authIds.stream().map(a-> userProfileRepository.findByAuthId(a)
                .orElseThrow(()-> new UserManagerException(USER_NOT_FOUND))).toList();
    }
}
