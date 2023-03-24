package com.berat.mapper;

import com.berat.dto.request.CreateUserRequest;
import com.berat.dto.request.UpdateAuthRequest;
import com.berat.dto.request.UpdateUserRequest;
import com.berat.model.UserProfile;
import com.berat.rabbitmq.model.RegisterElasticModel;
import com.berat.rabbitmq.model.RegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);

    UserProfile toUser(final CreateUserRequest dto);
    UserProfile toUser(final RegisterModel dto);
    RegisterElasticModel toRegisterElasticModel(final UserProfile profile);


}
