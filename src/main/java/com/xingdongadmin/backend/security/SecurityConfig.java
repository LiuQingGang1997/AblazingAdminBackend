package com.xingdongadmin.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter)
      throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.cors(Customizer.withDefaults());
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(
        auth ->
            auth.requestMatchers("/api/health", "/api/auth/login", "/h2-console/**").permitAll()
                // 放行文件上传接口
                .requestMatchers(HttpMethod.POST, "/api/upload").permitAll()
                // 放行官网前端需要的 GET 接口（允许匿名访问查询列表），注意需要精确匹配以及路径下的所有
                .requestMatchers(HttpMethod.GET, "/api/banners", "/api/banners/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cases", "/api/cases/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/customer-reviews", "/api/customer-reviews/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/brands", "/api/brands/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/mall-brands", "/api/mall-brands/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product-types", "/api/product-types/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product-scenes", "/api/product-scenes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/team-members", "/api/team-members/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product-series", "/api/product-series/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/dynamic-news", "/api/dynamic-news/**").permitAll()
                .anyRequest()
                .authenticated());

    http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

