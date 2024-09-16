package org.example.mapper;

import org.example.domain.User;
import org.example.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    User toDomain(UserEntity userEntity);

    List<User> toDomains(List<UserEntity> users);

    @Mapping(source = "id", target = "id")
    UserEntity toEntity(User user);
}

