package com.profesa.selos.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Email será usado como "username" do Spring Security
    @Column(nullable = false, unique = true)
    private String email;

    // Senha criptografada (BCrypt)
    @Column(nullable = false)
    private String senha;

    // Enum indicando o papel do usuário (ADMIN / USER)
    @Enumerated(EnumType.STRING)
    private Papel papel;

    public enum Papel {
        ADMIN, USER
    }

    /**
     * Retorna a lista de permissões (roles) do usuário.
     * Spring exige objetos GrantedAuthority.
     * Aqui criamos uma GrantedAuthority simples com lambda.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.papel.name());
    }

    /**
     * Retorna a senha para autenticação.
     * ATENÇÃO: deve sempre ser a senha criptografada!
     */
    @Override
    public String getPassword() {
        return this.senha;
    }

    /**
     * O email será usado como username para login.
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    // Os métodos abaixo definem se a conta está ativa.
    // Aqui retornamos tudo como ativo, mas pode personalizar depois.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
