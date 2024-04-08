package com.nhdic_backend.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder amBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    return amBuilder.build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    //
    return http.cors(AbstractHttpConfigurer::disable) // CORS (Cross-origin resource sharing) 적용 여부
        .csrf(
            AbstractHttpConfigurer
                ::disable) // cross site request forgery 사용 X (REST API 서비스 이므로, 인증정보 미사용)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
        .formLogin(AbstractHttpConfigurer::disable) // Security 기본 로그인 Form 사용 X
        .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 암호화 사용 X
        // ==== 권한 설정 ====
        .authorizeHttpRequests(
            (auth) ->
                auth.requestMatchers(antMatcher("/public/**"))
                    .permitAll()
                    // 그 밖의 Request 비허용
                    .anyRequest()
                    .authenticated())
        .build();
  }
}
