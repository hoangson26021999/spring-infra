package com.example.springreactive.auth;


import org.springframework.security.oauth2.client.web.server.ServerAuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements ServerAuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Override
    public Mono<OAuth2AuthorizationRequest> loadAuthorizationRequest(ServerWebExchange exchange) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<OAuth2AuthorizationRequest> removeAuthorizationRequest(ServerWebExchange exchange) {
        CookieUtils.deleteCookie(exchange, CookieConstants.REDIRECT_URI_PARAM_COOKIE_NAME);
        return null;
    }

    @Override
    public Mono<Void> saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, ServerWebExchange exchange) {
        CookieUtils.addCookie(exchange, CookieConstants.REDIRECT_URI_PARAM_COOKIE_NAME, CookieConstants.COOKIE_EXPIRE_SECONDS);
        return Mono.empty();
    }
}
