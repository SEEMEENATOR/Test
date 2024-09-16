package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository

public class UserRepository {
    private List<UserEntity> db = new ArrayList<>(List.of(
            new UserEntity(UUID.randomUUID(), "User1", "user1@gmail.com"),
            new UserEntity(UUID.randomUUID(), "User2", "user2@gmail.com")
    ));

    public List<UserEntity> getUser() {
        return db;
    }
    public Optional<UserEntity> getUserById(UUID userId){
        return Optional.of(db.get(0));
    }
    public UserEntity create(UserEntity userEntity){
        return userEntity;
    }
    public UserEntity update(UUID userId, UserEntity userEntity){
         userEntity.setId(userId);
         return userEntity;
    }
    public void delete(UUID userId){

    }
}
