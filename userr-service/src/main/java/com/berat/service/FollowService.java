package com.berat.service;

import com.berat.dto.request.CreateFollowRequest;
import com.berat.exception.EErrorType;
import com.berat.exception.UserManagerException;
import com.berat.model.Follow;
import com.berat.model.UserProfile;
import com.berat.repository.IFollowRepository;
import com.berat.utility.JwtTokenManager;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FollowService extends ServiceManager<Follow,String> {
    private final IFollowRepository followRepository;
    private final JwtTokenManager jwtTokenManager;
    private final UserProfileService userProfileService;

    public FollowService(IFollowRepository followRepository, JwtTokenManager jwtTokenManager, UserProfileService userProfileService) {
        super(followRepository);
        this.followRepository = followRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userProfileService = userProfileService;
    }

    @Transactional
    public Object createFollow(CreateFollowRequest dto) {
        Optional<Long> authId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserManagerException(EErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = userProfileService.findByAuthId(authId.get());
        Optional<UserProfile> followUser = userProfileService.findById(dto.getFollowId());

        Optional<Follow> follow = followRepository.findByUserIdAndFollowerId(userProfile.get().getId(),followUser.get().getId());
        if (follow.isPresent())
            throw new UserManagerException(EErrorType.FOLLOW_ALREADY_EXIST);
        if (userProfile.isEmpty() && followUser.isEmpty())
            throw new UserManagerException(EErrorType.USER_NOT_FOUND);
        save(Follow.builder()
                .userId(userProfile.get().getId())
                .followerId(followUser.get().getId())
                .build());
        userProfile.get().getFollows().add(followUser.get().getId());
        followUser.get().getFollowers().add(userProfile.get().getId());
        userProfileService.update(userProfile.get());
        userProfileService.update(followUser.get());
        return true;
    }
}
