package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.UserView;
import com.xingdongadmin.backend.entity.User;
import com.xingdongadmin.backend.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public static class CreateUserRequest {
    @NotBlank public String username;
    @NotBlank public String password;
    @NotBlank public String role;
    public String email;
  }

  public static class UpdateUserRequest {
    public String password;
    public String role;
    public String email;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserView> getAllUsers() {
    return userRepository.findAll().stream().map(UserView::from).toList();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public UserView createUser(@RequestBody CreateUserRequest req) {
    if (userRepository.findByUsername(req.username).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username exists");
    }

    User user = new User();
    user.setUsername(req.username);
    user.setPasswordHash(passwordEncoder.encode(req.password));
    user.setRole(req.role);
    user.setEmail(req.email);
    return UserView.from(userRepository.save(user));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public UserView updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest req) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

    if (req.email != null) {
      user.setEmail(req.email);
    }
    if (req.role != null) {
      user.setRole(req.role);
    }
    if (req.password != null && !req.password.isBlank()) {
      user.setPasswordHash(passwordEncoder.encode(req.password));
    }
    return UserView.from(userRepository.save(user));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
