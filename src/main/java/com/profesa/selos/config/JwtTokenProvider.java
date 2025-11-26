package com.profesa.selos.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;

/**
 * Classe responsável por criar, validar e extrair informações do JWT.
 * Usa a biblioteca JJWT (io.jsonwebtoken).
 */
@Component
public class JwtTokenProvider {
    /**
     * SECRET carregado do .env
     * NUNCA deixe isso hardcoded no código.
     */
    @Value("${JWT_SECRET}")
    private String secretKey;

    /**
     * Tempo de expiração do token (em ms)
     * Também carregado do .env
     */
    @Value("${JWT_EXPIRATION}")
    private long expiration;

    /**
     * ISSUER (quem emitiu o token)
     */
    @Value("${JWT_ISSUER}")
    private String issuer;

    /**
     * Gera um token JWT válido para o usuário informado.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // email
                .setIssuedAt(new Date()) // data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // expiração
                .setIssuer(issuer) // emissor
                .signWith(SignatureAlgorithm.HS256, secretKey) // assina com chave secreta
                .compact();
    }

    /**
     * Extrai o username do token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Verifica se o token é válido.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Verifica se o token expirou.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrai a data de expiração do token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Método auxiliar para extrair qualquer campo do token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrai todas as claims do token JWT.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)     // correto para JJWT 0.9.x / 0.10.x
                .parseClaimsJws(token)
                .getBody();
    }

}

