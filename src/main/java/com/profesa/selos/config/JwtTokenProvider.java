package com.profesa.selos.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jdk.internal.org.jline.reader.LineReaderBuilder;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String secret = "MINHA_CHAVE";
    private final long expiration = 1000 * 60 * 60 * 3000;

    public String generateToken(UserDetails userDetails){
        return jwt.builder()
                .subject(userDetails.getUsername())
                .issudAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
    }

    public String extractUsername(String token){
        ;
        return jwt.parser()
                .verifyWith(key.hmacShakeyFor(secret.getBytes()))
                .build()
                .parseSeSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTpokenValid(String token, UserDetails user){
        return extractUsername(token).equals(user.getUsername());
    }
}
