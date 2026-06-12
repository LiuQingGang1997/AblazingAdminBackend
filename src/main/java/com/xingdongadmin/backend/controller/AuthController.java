package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.AuthLoginRequest;
import com.xingdongadmin.backend.dto.AuthLoginResponse;
import com.xingdongadmin.backend.dto.UserView;
import com.xingdongadmin.backend.entity.User;
import com.xingdongadmin.backend.repository.UserRepository;
import com.xingdongadmin.backend.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthController(
      UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public AuthLoginResponse login(@Valid @RequestBody AuthLoginRequest request) {
    User user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad creds"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad creds");
    }

    String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());
    return new AuthLoginResponse(token, UserView.from(user));
  }

  @GetMapping("/me")
  public UserView me(Authentication authentication) {
    if (authentication == null || authentication.getName() == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    User user =
        userRepository
            .findByUsername(authentication.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    return UserView.from(user);
  }
}

