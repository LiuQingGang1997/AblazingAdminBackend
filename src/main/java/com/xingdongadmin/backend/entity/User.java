package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "users")
@Comment("系统用户表")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("主键ID")
  private Long id;

  @Column(nullable = false, unique = true)
  @Comment("用户名")
  private String username;

  @Column(nullable = false)
  @Comment("密码哈希值")
  private String passwordHash;

  @Column(nullable = false)
  @Comment("角色权限 (如 ADMIN, EDITOR, VIEWER)")
  private String role;

  @Comment("电子邮箱")
  private String email;

  @Comment("创建时间")
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
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

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
