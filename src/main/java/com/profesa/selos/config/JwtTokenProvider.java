package com.profesa.selos.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

/**
 * Classe responsável por criar, validar e extrair informações do JWT.
 * Usa a biblioteca JJWT (io.jsonwebtoken).
 */
@Component
public class JwtTokenProvider {

    /**
     * Chave secreta usada para assinar o token.
     * IMPORTANTE:
     *  - Deve ter pelo menos 32 caracteres para HS256
     *  - Em produção deve vir do ENV (variável de ambiente)
     */
    private final String secret = "CHAVE_SUPER_SECRETA_DE_32_CHARS_AQUI_123456";

    /**
     * Tempo de expiração do token (24 horas)
     */
    private final long expirationMillis = 1000L * 60 * 60 * 24;

    /**
     * Converte a string secret em uma chave criptográfica válida.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Gera um token JWT para o usuário autenticado.
     */
    public String generateToken(UserDetails userDetails) {

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // usuário dono do token
                .setIssuedAt(agora)                     // quando o token foi criado
                .setExpiration(expiracao)              // quando expira
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // assinatura
                .compact(); // finaliza o token
    }

    /**
     * Extrai o nome do usuário a partir do token.
     * Se o token estiver inválido, retorna NULL.
     */
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()               // cria parser do JWT
                    .setSigningKey(getSigningKey())   // define chave usada para validar assinatura
                    .build()
                    .parseClaimsJws(token)            // valida o token
                    .getBody()
                    .getSubject();                    // pega o subject (username)
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * Verifica se o token é válido:
     * - assinatura correta
     * - não expirado
     * - pertence ao mesmo usuário
     */
    public boolean isTokenValid(String token, UserDetails user) {
        String username = extractUsername(token);
        if (username == null) return false; // token inválido
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Verifica se o token já expirou.
     */
    private boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return expiration.before(new Date()); // se expirou → true
        } catch (JwtException e) {
            return true;
        }
    }
}

