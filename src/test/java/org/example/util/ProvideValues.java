package org.example.util;

import org.example.entity.UserEntity;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;
public class ProvideValues {
    private static Stream<Arguments> provideValuesForMapperTest() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(List.of(new UserEntity())),
                Arguments.of(List.of(new UserEntity(), new UserEntity()))
        );
    }
}
