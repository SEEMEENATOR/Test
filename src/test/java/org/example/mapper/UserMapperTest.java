package org.example.mapper;

import org.example.domain.User;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @ParameterizedTest
    @CsvSource({
            "3b2a1e6d-9e4c-4214-b42e-303b916000d7, Test User 1, test1@example.com",
            "48a83d7a-0b9e-4e9d-bacd-96f7c282f11d, Test User 2, test2@example.com"
    })
    void shouldMapUserEntityToDomain(String id, String name, String email) {
        UUID uuid = UUID.fromString(id);
        UserEntity userEntity = new UserEntity(uuid, name, email);

        User user = userMapper.toDomain(userEntity);

        assertThat(user.getId()).isEqualTo(uuid);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @ParameterizedTest
    @CsvSource({
            "3b2a1e6d-9e4c-4214-b42e-303b916000d7, Test User 1, test1@example.com",
            "48a83d7a-0b9e-4e9d-bacd-96f7c282f11d, Test User 2, test2@example.com"
    })
    void shouldMapUserToEntity(String id, String name, String email) {
        UUID uuid = UUID.fromString(id);
        User user = new User(uuid, name, email);

        UserEntity userEntity = userMapper.toEntity(user);

        assertThat(userEntity.getId()).isEqualTo(uuid);
        assertThat(userEntity.getName()).isEqualTo(name);
        assertThat(userEntity.getEmail()).isEqualTo(email);
    }
    @Test
    void shouldMapUserEntitiesToDomains() {
        UserEntity userEntity1 = new UserEntity(UUID.randomUUID(), "Maks1", "maks1@gmail.com");
        UserEntity userEntity2 = new UserEntity(UUID.randomUUID(), "Maks2", "maks2@gmail.com");

        List<UserEntity> userEntities = Arrays.asList(userEntity1, userEntity2);

        List<User> users = userMapper.toDomains(userEntities);

        assertThat(users).hasSize(2);

        assertThat(users.get(0).getId()).isEqualTo(userEntity1.getId());
        assertThat(users.get(0).getName()).isEqualTo(userEntity1.getName());
        assertThat(users.get(0).getEmail()).isEqualTo(userEntity1.getEmail());

        assertThat(users.get(1).getId()).isEqualTo(userEntity2.getId());
        assertThat(users.get(1).getName()).isEqualTo(userEntity2.getName());
        assertThat(users.get(1).getEmail()).isEqualTo(userEntity2.getEmail());
    }
}
