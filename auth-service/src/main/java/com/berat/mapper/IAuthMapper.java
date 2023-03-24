package com.berat.mapper;

import com.berat.dto.request.ActivateRequest;
import com.berat.dto.request.ActivateUserRequest;
import com.berat.dto.request.CreateUserRequest;
import com.berat.dto.request.RegisterRequest;
import com.berat.dto.response.AuthResponse;
import com.berat.model.Auth;
import com.berat.rabbitmq.model.RegisterMailModel;
import com.berat.rabbitmq.model.RegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(final RegisterRequest dto);
    AuthResponse toAuthResponse(final Auth auth);

    @Mapping(source = "id",target = "authId")
    CreateUserRequest toCreateUserRequest(final Auth auth);
    @Mapping(source = "id",target = "authId")
    RegisterModel toRegisterModel(final Auth auth);
    RegisterMailModel toRegisterMailModel(final Auth auth);


}
