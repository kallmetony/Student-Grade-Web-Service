package com.aaronr92.schoolwebservice.controller;

import com.aaronr92.schoolwebservice.dto.PasswordChange;
import com.aaronr92.schoolwebservice.entity.User;
import com.aaronr92.schoolwebservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    ResponseEntity<User> register(@Valid @RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/auth/signup")
                .toUriString());
        return ResponseEntity.created(uri).body(userService.registerNewUser(user));
    }

    @PutMapping("/change/password")
    ResponseEntity<PasswordChange> changePassword(@AuthenticationPrincipal User user,
                                                  @RequestBody PasswordChange passwordChange) {
        return ResponseEntity.ok().body(userService.changePassword(user, passwordChange));
    }

    @GetMapping
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @DeleteMapping
    ResponseEntity<Map<String, String>> deleteUser(@RequestParam String username) {
        return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.NO_CONTENT);
    }

    /*@GetMapping()
    ResponseEntity<User> getUser(@RequestParam String username) {
        return ResponseEntity.ok().body((User) userService.loadUserByUsername(username));
    }*/
}
