package com.berat.mapper;

import com.berat.dto.request.CreateUserRequest;
import com.berat.model.UserProfile;
import com.berat.rabbitmq.model.RegisterElasticModel;
import com.berat.rabbitmq.model.RegisterModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-31T12:10:18+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class IUserMapperImpl implements IUserMapper {

    @Override
    public UserProfile toUser(CreateUserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder<?, ?> userProfile = UserProfile.builder();

        userProfile.authId( dto.getAuthId() );
        userProfile.username( dto.getUsername() );
        userProfile.email( dto.getEmail() );

        return userProfile.build();
    }

    @Override
    public UserProfile toUser(RegisterModel dto) {
        if ( dto == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder<?, ?> userProfile = UserProfile.builder();

        userProfile.authId( dto.getAuthId() );
        userProfile.username( dto.getUsername() );
        userProfile.email( dto.getEmail() );

        return userProfile.build();
    }

    @Override
    public RegisterElasticModel toRegisterElasticModel(UserProfile profile) {
        if ( profile == null ) {
            return null;
        }

        RegisterElasticModel.RegisterElasticModelBuilder registerElasticModel = RegisterElasticModel.builder();

        registerElasticModel.id( profile.getId() );
        registerElasticModel.authId( profile.getAuthId() );
        registerElasticModel.username( profile.getUsername() );
        registerElasticModel.email( profile.getEmail() );

        return registerElasticModel.build();
    }
}
