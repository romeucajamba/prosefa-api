package com.profesa.selos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Este filtro intercepta TODAS as requisições antes de chegarem nos controladores.
 * Ele verifica se existe um token JWT no cabeçalho "Authorization",
 * valida o token e, caso esteja tudo correto,
 * autentica o usuário no contexto do Spring Security.
 *
 * OncePerRequestFilter garante que este filtro será executado APENAS UMA VEZ por requisição.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Classe responsável por gerar e validar tokens JWT
    private final JwtTokenProvider jwtTokenProvider;
    // Carrega informações do usuário a partir do username (banco de dados)
    private final UserDetailsService userDetailsService;

    /**
     * Método principal do filtro.
     * Ele é executado automaticamente em TODAS as requisições HTTP.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        //Obtém o valor do cabeçalho "Authorization"
        String authHeader = request.getHeader("Authorization");

        //Se não houver token OU não começar com "Bearer ", ignore e siga o fluxo normal
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //Remove o prefixo "Bearer " e fica apenas com o token JWT puro
        String token = authHeader.substring(7);

        //Extrai o username que foi colocado dentro do token JWT
        String username = jwtTokenProvider.extractUsername(token);

        /**
         * Verifica:
         * - Se conseguiu extrair o username
         * - Se o usuário AINDA NÃO está autenticado no contexto do Spring
         */

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Carrega o usuário a partir do banco de dados
            UserDetails user = userDetailsService.loadUserByUsername(username);

            //Verifica se o token é válido para esse usuário
            if (jwtTokenProvider.isTokenValid(token, user)) {
                /**
                 *Cria uma autenticação do Spring Security
                 *    contendo:
                 *    - usuário autenticado
                 *    - credenciais (null pois já temos o token)
                 *    - permissões (roles)
                 */
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                //Adiciona detalhes da requisição (como IP e navegador)
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Salva a autenticação no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // Continua o fluxo da requisição normalmente
        chain.doFilter(request, response);
    }
}