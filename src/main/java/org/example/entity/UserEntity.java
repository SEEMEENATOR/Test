package org.example.entity;

import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    private UUID id;
    private String name;
    private String email;

}