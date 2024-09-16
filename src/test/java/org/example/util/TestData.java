package org.example.util;

import org.example.domain.User;
import org.example.entity.UserEntity;

import java.util.UUID;

public class TestData {
    public static UserEntity generateUserEntity(){
        return new UserEntity().builder()
                .id(UUID.randomUUID())
                .name("Maks")
                .email("maks@gmail.com")
                .build();
    }
    public static User generateUser(){
        UUID userId = UUID.randomUUID();
        return new User().builder()
                .id(userId)
                .name("Maks")
                .email("maks@gmail.com")
                .build();
    }
}
