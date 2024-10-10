package org.barcord.barcode_service.config.security;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.service.kakako.KakaoOAuth2UserService;
import org.barcord.barcode_service.config.security.entrypoint.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoOAuth2UserService kakaoOAuth2UserService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/login/oauth2/code/kakao/**", "/oauth2/authorization/kakao").permitAll()
                                .requestMatchers("/h2-console/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/login/oauth2/code/kakao/success")
                        .failureUrl("/login/oauth2/code/kakao/failure")
                        .userInfoEndpoint(userInfo -> userInfo.userService(kakaoOAuth2UserService)
                        ));

        http.headers(header -> {
            header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        });

        return http.build();
    }


}
