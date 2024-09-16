package org.example.controller;

import org.example.domain.User;
import lombok.RequiredArgsConstructor;
import org.example.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("api/v1/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getUser();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users);
    }
    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @GetMapping("/api/v1/create")
    public ResponseEntity<User> createUser(@RequestParam String name, @RequestParam String email) {
        User user = new User(null, name, email);
        User createdUser = userService.create(user);
        return ResponseEntity.status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdUser);
    }


    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedUser);
    }


    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
