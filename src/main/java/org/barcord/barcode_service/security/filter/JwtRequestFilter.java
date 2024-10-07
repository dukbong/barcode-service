package org.barcord.barcode_service.security.filter;

import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.barcord.barcode_service.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${token.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            JWT jwt = vaildateToken(token);
            Map<String, Object> claims = jwt.getAllClaims();
            if(SecurityContextHolder.getContext().getAuthentication() == null) {
//                SecurityContextHolder.getContext().setAuthentication();
            }
        }

        filterChain.doFilter(request, response);
    }

    private JWT vaildateToken(String token) {
        try {
            Verifier verifier = HMACVerifier.newVerifier(secretKey);

            JWT jwt = JWT.getDecoder().decode(token, verifier);
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
            if(jwt.expiration.isBefore(now)) {
                throw new InvalidTokenException("올바르지 않은 토큰 정보입니다.");
            }
            return jwt;
        } catch (Exception e) {
            throw new InvalidTokenException("올바르지 않은 토큰 정보입니다.");
        }
    }

}
