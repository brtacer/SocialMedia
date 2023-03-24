package com.berat.service;

import com.berat.model.UserProfile;
import com.berat.rabbitmq.model.RegisterElasticModel;
import com.berat.repository.IUserProfileRepository;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;


@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository userProfileRepository;


    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;

    }


    public UserProfile createUserWithRabbit(RegisterElasticModel model) {
        return save(UserProfile.builder().id(model.getId()).authId(model.getAuthId())
                .username(model.getUsername()).email(model.getEmail()).build());
    }
}
