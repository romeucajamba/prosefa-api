package com.profesa.selos.services;

import com.profesa.selos.dto.LoginRequest;
import com.profesa.selos.dto.LoginResponse;
import com.profesa.selos.dto.RegisterRequest;
import com.profesa.selos.domain.repositories.UserRepository;
import com.profesa.selos.domain.entities.Usuarios;
import com.profesa.selos.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest dto) {

        Usuarios u = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!encoder.matches(dto.senha(), u.getSenha()))
            throw new RuntimeException("Credenciais inválidas");

        UserDetails user = User
                .withUsername(u.getEmail())
                .password(u.getSenha())
                .roles(u.getPapel() == null ? "USER" : u.getPapel().name())
                .build();

        String token = tokenProvider.generateToken(user);

        return new LoginResponse(token);
    }

    public void register(RegisterRequest dto) {

        if (usuarioRepository.findByEmail(dto.email()).isPresent())
            throw new RuntimeException("Email já registrado");

        if (!dto.senha().equals(dto.confirmarSenha()))
            throw new RuntimeException("As senhas não coincidem");

        Usuarios u = Usuarios.builder()
                .email(dto.email())
                .senha(encoder.encode(dto.senha()))
                .papel(Usuarios.Papel.USER)
                .build();

        usuarioRepository.save(u);
    }
}