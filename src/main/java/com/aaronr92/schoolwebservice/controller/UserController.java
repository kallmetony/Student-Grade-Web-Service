package com.aaronr92.schoolwebservice.controller;

import com.aaronr92.schoolwebservice.dto.PasswordChange;
import com.aaronr92.schoolwebservice.dto.RoleOperation;
import com.aaronr92.schoolwebservice.dto.UserDTO;
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

    @PostMapping("/signup")
    ResponseEntity<User> signup(@Valid @RequestBody UserDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/user/register")
                .toUriString());
        return ResponseEntity.created(uri).body(userService.signup(user));
    }

    @PutMapping("/change/role")
    ResponseEntity<Void> changeRole(@RequestBody RoleOperation roleOperation) {
        userService.changeRole(roleOperation);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change/username")
    ResponseEntity<Void> changeUsername(@RequestParam("old_username") String oldUsername,
                                        @RequestParam("new_username") String newUsername) {
        userService.changeUsername(oldUsername, newUsername);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change/password")
    ResponseEntity<PasswordChange> changePassword(@AuthenticationPrincipal User user,
                                                  @RequestBody PasswordChange passwordChange) {
        return ResponseEntity.ok().body(userService.changePassword(user, passwordChange));
    }

    @GetMapping("/find/all")
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/find")
    ResponseEntity<User> getUser(@RequestParam String username) {
        return ResponseEntity.ok(((User) userService.loadUserByUsername(username)));
    }

    @DeleteMapping
    ResponseEntity<Map<String, String>> deleteUser(@RequestParam String username) {
        return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.NO_CONTENT);
    }
}
