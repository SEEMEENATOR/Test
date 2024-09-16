package org.example.service;

import org.example.domain.User;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.example.repository.UserRepository;
import org.example.util.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserService userService;
    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getUsers()")
    void shouldReturnUsers() {
        List<UserEntity> mockEntity = List.of(TestData.generateUserEntity());
        List<User> mockDomain = List.of(TestData.generateUser());

        when(userRepository.getUser()).thenReturn(mockEntity);
        when(userMapper.toDomains(mockEntity)).thenReturn(mockDomain);

        List<User> users = userService.getUser();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("Maks");

        verify(userRepository, times(1)).getUser();
    }

    @Test
    @DisplayName("getUserById()")
    void shouldReturnUserById() {
        UUID userId = UUID.randomUUID();
        UserEntity mockEntity = new UserEntity(userId, "Maks", "maks@gmail.com");
        User mockDomain = new User(userId, "Maks", "maks@gmail.com");

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(mockEntity));
        when(userMapper.toDomain(mockEntity)).thenReturn(mockDomain);

        User user = userService.getUserById(userId);

        assertThat(user.getName()).isEqualTo("Maks");
        verify(userRepository).getUserById(uuidCaptor.capture());
        assertThat(uuidCaptor.getValue()).isEqualTo(userId);

    }
    @Test
    void shouldNotGetUserById_whenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
    }
    @Test
    void shouldDeleteUser(){
        UUID userId = UUID.randomUUID();
        userService.delete(userId);
    }
    @Test
    @DisplayName("create()")
    void shouldCreateUser(){
        User user = new User(UUID.randomUUID(),"Maks1","maks1@gmail.com");
        UserEntity userEntity = new UserEntity(UUID.randomUUID(),"Maks1","maks1@gmail.com");

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.create(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        User createdUser = userService.create(user);

        assertThat(createdUser.getName()).isEqualTo("Maks1");
        verify(userRepository,times(1)).create(userEntity);
    }
    @Test
    @DisplayName("update()")
    void shouldUpdateUser(){
        UUID userId = UUID.randomUUID();
        User user = new User(userId,"MaksUpdated","maksupdated@gmail.com");
        UserEntity userEntity = new UserEntity(userId,"MaksUpdated","maksupdated@gmail.com");

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.update(userId, userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        User updatedUser = userService.update(userId, user);

        assertThat(updatedUser.getName()).isEqualTo("MaksUpdated");
        verify(userRepository, times(1)).update(userId, userEntity);

    }

}
