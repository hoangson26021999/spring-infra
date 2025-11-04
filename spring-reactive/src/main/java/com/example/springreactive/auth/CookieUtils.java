package com.example.springreactive.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;

@Slf4j
public class CookieUtils {

    public static String getCookie(ServerWebExchange exchange, String name) {
        log.info("get-cookie: {}", exchange.getRequest().getCookies());
        HttpCookie httpCookie = exchange.getRequest().getCookies().getFirst(name);
        return httpCookie == null ? null : httpCookie.getValue();
    }

    public static void addCookie(ServerWebExchange exchange, String name, int maxAge) {
        String value = exchange.getRequest().getQueryParams().getFirst(name);
        exchange.getResponse().addCookie(ResponseCookie.fromClientResponse(name, value == null ? "": value)
                                            .maxAge(maxAge)
                                            .httpOnly(true) 
                                            .path("/")
                                            .secure(true) // should be true in production
                                            .build());
    }

    public static void deleteCookie(ServerWebExchange exchange, String name) {
        exchange.getResponse().addCookie(ResponseCookie.fromClientResponse(name, "")
                                            .maxAge(-1)
                                            .httpOnly(false)
                                            .path("/")
                                            .secure(true) // should be true in production
                                            .build());
    }

    public static void shareClientCookie(ServerWebExchange exchange, String name, String value, Duration maxAge) {
        exchange.getResponse().addCookie(ResponseCookie.fromClientResponse(name, value)
                                            .maxAge(maxAge)
                                            .httpOnly(false)
                                            .domain(CookieConstants.SUB_DOMAIN)
                                            .path("/")
                                            .secure(true) // should be true in production
                                            .build());
    }

    public static void deleteClientCookie(ServerWebExchange exchange, String name) {
        exchange.getResponse().addCookie(ResponseCookie.fromClientResponse(name, "")
                                            .maxAge(-1)
                                            .httpOnly(false)
                                            .domain(CookieConstants.SUB_DOMAIN)
                                            .path("/")
                                            .secure(true) // should be true in production
                                            .build());
    }

}
