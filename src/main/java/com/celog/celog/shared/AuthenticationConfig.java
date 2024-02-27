package com.celog.celog.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration // 스프링 설정 클래스임을 나타냅니다.
@EnableWebSecurity // 웹 보안을 활성화합니다.
public class AuthenticationConfig {

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // HTTP Basic 인증을 비활성화합니다.
                .csrf().disable() // CSRF(Cross-Site Request Forgery) 공격을 비활성화합니다.
                .cors().and()
                .formLogin().disable() // 폼 기반 인증을 비활성화합니다.
                .authorizeHttpRequests() // HTTP 요청에 대한 인가를 설정합니다.
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api/**" // 당장은 모두 허용을 해준다.
                ).permitAll() // 특정 요청 경로에 대해 모든 사용자에게 허용합니다.
                .anyRequest().authenticated() // 다른 모든 요청은 인증된 사용자에게만 허용됩니다.
                .and()
                .sessionManagement() // 세션 관리를 설정합니다.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 상태 없는(Stateless) 방식으로 관리합니다.
                .and().build(); // 보안 필터 체인을 빌드하고 반환합니다.
    }
}
