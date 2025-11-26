package com.profesa.selos.http.controllers;

import com.profesa.selos.dto.LoginRequest;
import com.profesa.selos.dto.LoginResponse;
import com.profesa.selos.dto.RegisterRequest;
import com.profesa.selos.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest data){
        return ResponseEntity.ok(authService.login(data));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest data){
        authService.register(data);
        return ResponseEntity.ok("Usuário criado com sucesso");
    }
}
