package com.ecommerceapp.ecommerceserver.security;

public final class SecurityConstants {
    public static final String SECRET = "topsecret";
    public static final long EXPIRATION_TIME = 7_200_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private SecurityConstants() {}
}
