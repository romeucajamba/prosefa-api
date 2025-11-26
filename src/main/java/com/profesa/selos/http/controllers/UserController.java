package com.profesa.selos.http.controllers;

import com.profesa.selos.dto.EmpresaResponse;
import com.profesa.selos.dto.LoginRequest;
import com.profesa.selos.dto.LoginResponse;
import com.profesa.selos.dto.RegisterRequest;
import com.profesa.selos.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest data){
        return ResponseEntity.ok(authService.login(data));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest data){
        authService.register(data);
        return ResponseEntity.ok("Usuario Criado com sucesso");
    }
}
