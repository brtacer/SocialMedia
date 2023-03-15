package com.berat.service;

import com.berat.dto.request.CreateUserRequest;
import com.berat.exception.UserManagerException;
import com.berat.mapper.IUserMapper;
import com.berat.model.UserProfile;
import com.berat.model.enums.EStatus;
import com.berat.repository.IUserProfileRepository;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.berat.exception.EErrorType.*;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final IUserProfileRepository userProfileRepository;

    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
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
}
