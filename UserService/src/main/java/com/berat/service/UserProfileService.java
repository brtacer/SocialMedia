package com.berat.service;

import com.berat.dto.request.CreateUserRequest;
import com.berat.dto.request.UpdateAuthRequest;
import com.berat.dto.request.UpdateUserRequest;
import com.berat.exception.UserManagerException;
import com.berat.manager.IAuthManager;
import com.berat.mapper.IUserMapper;
import com.berat.model.UserProfile;
import com.berat.model.enums.EStatus;
import com.berat.repository.IUserProfileRepository;
import com.berat.utility.JwtTokenManager;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.berat.exception.EErrorType.*;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenManager tokenManager;
    private final IAuthManager authManager;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenManager tokenManager, IAuthManager authManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
    }

    public Boolean createUser(CreateUserRequest dto) {
        if (Objects.isNull(save(IUserMapper.INSTANCE.toUser(dto))))
            throw new UserManagerException(USER_NOT_CREATED);
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
}
