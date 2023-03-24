package com.berat.utility;

import com.berat.manager.IUserManager;
import com.berat.model.UserProfile;
import com.berat.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final UserProfileService userProfileService;
    private final IUserManager userManager;

    //@PostConstruct
    public void initData(){
        List<UserProfile> userProfiles = userManager.findAll().getBody();

        userProfileService.saveAll(userProfiles);

    }
}
