package org.example.service;

import org.example.domain.User;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.mapper.UserMapper;
import org.example.mapper.UserMapperImpl;
import org.springframework.stereotype.Service;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {
    UserRepository userRepository = new UserRepository();
    UserMapper userMapper = new UserMapperImpl();
    public List<User> getUser() {
        List<UserEntity> users = userRepository.getUser();
        return userMapper.toDomains(users);
    }
    public User getUserById(UUID userId) {
        UserEntity userEntity = userRepository.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return userMapper.toDomain(userEntity);
    }

    public User create(User user){
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity createdEntity = userRepository.create(userEntity);
        return userMapper.toDomain(createdEntity);

    }
    public User update(UUID userId, User user){
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity updateEntity = userRepository.update(userId, userEntity);
        return userMapper.toDomain(updateEntity);
    }
    public void delete(UUID userId){

    }

}
