package org.barcord.barcode_service.config.security;

import org.barcord.barcode_service.api.service.kakako.service.KakaoOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final KakaoTokenFilter kakaoTokenFilter;
	private final KakaoOAuth2UserService kakaoOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/login/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(kakaoTokenFilter, UsernamePasswordAuthenticationFilter.class);

		http.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login/oauth2/code/kakao/**", "/oauth2/authorization/kakao").permitAll()
						.anyRequest().authenticated() // 그 외 요청은 인증 필요
				).oauth2Login(oauth -> oauth
						.defaultSuccessUrl("/login/oauth2/code/kakao/success") // 로그인 성공 시 리디렉션 경로
						.failureUrl("/login/oauth2/code/kakao/failure") // 로그인 실패 시 리디렉션 경로
						.userInfoEndpoint(userInfo -> userInfo.userService(kakaoOAuth2UserService) // 카카오 사용자 정보 가져오기
						));

		return http.build();
	}
	

}
