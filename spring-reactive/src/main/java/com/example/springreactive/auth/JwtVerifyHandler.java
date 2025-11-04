package com.example.springreactive.auth;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtVerifyHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.secret}")
    private String jwtSecret;

    private JWTSigner jwtSigner;

    @PostConstruct
    public void init() {
        this.jwtSigner = JWTSignerUtil.hs256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.objectMapper.findAndRegisterModules();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Mono<VerificationResult> check(String accessToken) {
        return Mono.just(verify(accessToken))
                .onErrorResume(e -> Mono.error(new UnauthorizedException(e.getMessage())));
    }

    @SneakyThrows
    public VerificationResult verify(String token) {
        JWT jwt = JWT.of(token).setSigner(this.jwtSigner);
        boolean result = jwt.validate(0);
        VerificationResult verificationResult = new VerificationResult();
        verificationResult.setValidated(result);
        if (!result) {
            return verificationResult;
        }
        JWTPayload payload = jwt.getPayload();
        verificationResult.setJti((String) payload.getClaim("jti"));
        verificationResult.setSub((String) payload.getClaim("sub"));
        verificationResult.setName((String) payload.getClaim("name"));
        verificationResult.setRoles((List<String>) payload.getClaim("roles"));
        return verificationResult;
    }
}
