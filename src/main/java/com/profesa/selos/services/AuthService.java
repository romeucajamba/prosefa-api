package com.profesa.selos.services;

import com.profesa.selos.dto.LoginRequest;
import com.profesa.selos.dto.LoginResponse;
import com.profesa.selos.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.profesa.selos.domain.repositories.UserRepository;
import com.profesa.selos.config.JwtTokenProvider;
import com.profesa.selos.domain.entities.Usuarios;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository usuarioRepository;
    private PasswordEncoder encoder;
    private JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest dto) {

        Usuarios u = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!encoder.matches(dto.senha(), u.getSenha()))
            throw new RuntimeException("Credenciais inválidas");

        UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getSenha())
                .roles(u.getPapel().name())
                .build();

        String token = tokenProvider.generateToken(user);

        return new LoginResponse(token);
    }

    public void register(RegisterRequest dto) {

        if (usuarioRepository.findByEmail(dto.email()).isPresent())
            throw new RuntimeException("Email já registrado");

        Usuarios u = Usuario.builder()
                .email(dto.email())
                .senha(encoder.encode(dto.senha()))
                .papel(Usuario.Papel.USER)
                .build();

        usuarioRepository.save(u);
    }
}