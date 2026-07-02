package br.com.unificaerp.usuario.model;

import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.role.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_login", columnNames = "login"),
                @UniqueConstraint(name = "uk_usuario_empresa", columnNames = "empresa_id")
        })
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long id;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "bloqueio", nullable = false)
    private Boolean bloqueio;
    @Column(name = "token_sessao", nullable = false)
    private String tokenSessao;
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false,
    foreignKey = @ForeignKey(name = "empresa_fk"))
    private Empresa empresa;

    @OneToOne
    private ClienteFuncionario clienteFuncionario;

    @ManyToMany
    @JoinTable(name = "role_usuario",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public void associarEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void desassociarUsuario() {
        this.empresa = null;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
