package com.example.Premind_BE.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private Key key;

    @Value("${spring.jwt.access-expiration}")
    private long accessTokenExpirationMinutes;

    @Value("${spring.jwt.refresh-expiration}")
    private long refreshTokenExpirationMinutes;


    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getEmail(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("email", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createAccessToken(String email) {
        long expiredMs = accessTokenExpirationMinutes * 60 * 1000;
        return createToken(email, expiredMs);
    }

    public String createRefreshToken(String email) {
        long expiredMs = refreshTokenExpirationMinutes * 60 * 1000;
        return createToken(email, expiredMs);
    }

    private String createToken(String email, long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


}
