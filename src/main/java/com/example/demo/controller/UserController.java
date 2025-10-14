package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Fetch all users.
     * Example: GET /api/v1/users/getAllUsers
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Fetch a single user by ID.
     * Example: GET /api/v1/users/getUserById/{id}
     */
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserByName(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new user.
     * Example: POST /api/v1/users/createUser
     */
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("/api/v1/users/getUserById/" + createdUser.getId()))
                .body(createdUser);
    }



    /**
     * Delete a user by ID.
     * Example: DELETE /api/v1/users/deleteUser/{id}
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(user -> {
                    userService.deleteUser(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Authentication endpoint used by Keycloak User Storage SPI (isValid()).
     *
     * Request:
     *   POST /api/authenticate
     *   Content-Type: application/json
     *   Body: { "username": "alice", "password": "plainTextPassword" }
     *
     * Response:
     *   Default JSON: { "valid": true|false }
     *   If ?plain=true is present: "true" or "false" (text/plain)
     */
    @PostMapping(
            path = "/authenticate",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> authenticate(
            @RequestParam(name = "plain", defaultValue = "false") boolean plain,
            @RequestBody AuthRequest req
    ) {

        boolean valid = false;
        try {
                valid = userService.verifyPassword(req.username, req.password);

        } catch (Exception e) {
            valid = false;
        }


        if (plain) {
            // Text/plain "true"/"false" for maximal compatibility
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(Boolean.toString(valid));
        } else {
            // JSON default: { "valid": true|false }
            return ResponseEntity.ok(Map.of("valid", valid));
        }
    }



    public static final class AuthRequest {
     public String username;
      public String password;
    }

    // Example record returned by your service/repo layer (adapt to your model)
    public static final class UserRecord {
        private final String username;
        private final String passwordHash; // or plaintext if you must
        private final boolean enabled;

        public UserRecord(String username, String passwordHash, boolean enabled) {
            this.username = username;
            this.passwordHash = passwordHash;
            this.enabled = enabled;
        }
        public String username() { return username; }
        public String passwordHash() { return passwordHash; }
        public boolean enabled() { return enabled; }
    }


}
