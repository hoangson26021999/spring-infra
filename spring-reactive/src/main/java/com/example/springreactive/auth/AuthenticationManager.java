package com.example.springreactive.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);
    private final JwtVerifyHandler jwtVerifyHandler;

    public AuthenticationManager(@Autowired JwtVerifyHandler jwtVerifyHandler) {
        this.jwtVerifyHandler = jwtVerifyHandler;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        VerificationResult tokenResult = jwtVerifyHandler.verify(token);
        if (!tokenResult.isValidated()) {
//            return Mono.error(new UnauthorizedException("Invalid token"));
            return Mono.empty();
        }
        var authorities = tokenResult.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        String userId = "";
        try {
            userId = tokenResult.getSub();
        } catch (NumberFormatException ex) {
            logger.error("sub claim is not a number: {}", tokenResult.getSub());
            return Mono.empty();
        }
        UserPrincipal principal = new UserPrincipal(userId, tokenResult.getName());
        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(principal, tokenResult, authorities));
    }
}
