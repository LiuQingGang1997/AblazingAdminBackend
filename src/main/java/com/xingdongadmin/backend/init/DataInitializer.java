package com.xingdongadmin.backend.init;

import com.xingdongadmin.backend.entity.User;
import com.xingdongadmin.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) {
    if (userRepository.findByUsername("admin").isEmpty()) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setPasswordHash(passwordEncoder.encode("admin123"));
      admin.setRole("ADMIN");
      admin.setEmail("admin@example.com");
      userRepository.save(admin);
    }
    
    if (userRepository.findByUsername("manage").isEmpty()) {
      User manageUser = new User();
      manageUser.setUsername("manage");
      manageUser.setPasswordHash(passwordEncoder.encode("Ablazing"));
      manageUser.setRole("ADMIN");
      manageUser.setEmail("manage@example.com");
      userRepository.save(manageUser);
    }
  }
}

