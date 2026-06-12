package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.User;
import java.time.LocalDateTime;

public class UserView {
  private Long id;
  private String username;
  private String email;
  private String role;
  private LocalDateTime createdAt;

  public static UserView from(User user) {
    UserView v = new UserView();
    v.id = user.getId();
    v.username = user.getUsername();
    v.email = user.getEmail();
    v.role = user.getRole();
    v.createdAt = user.getCreatedAt();
    return v;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}

