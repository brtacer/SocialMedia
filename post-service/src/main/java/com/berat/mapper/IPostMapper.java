package com.berat.mapper;

import com.berat.dto.request.CreatePostRequest;
import com.berat.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPostMapper {

    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);

    Post toPost(final CreatePostRequest dto);
}
