package br.ufrn.dimap.gestaovendas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credenciais implements UserDetails {
    @Id
    @Column(name = "pessoa_id")
    private Long id;

    private String username;
    private String password;
    private String role; // Ex: "ROLE_USER,ROLE_ADMIN"

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte a string de roles (separadas por vírgula) em uma lista de GrantedAuthority
        return Arrays.stream(role.split(",")).map(SimpleGrantedAuthority::new).toList();
    }

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
