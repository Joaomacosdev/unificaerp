package br.com.unificaerp.usuario.model;

import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.role.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_login", columnNames = "login"),
        })
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long id;

    @JsonIgnore
    @Column(name = "login", nullable = false)
    private String login;

    @JsonIgnore
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "liberado", nullable = false)
    @JsonIgnore
    private Boolean liberado  = true;

    @Column(name = "token_sessao", columnDefinition = "text")
    @JsonIgnore
    private String tokenSessao;


    @Column(name = "refresh_token", columnDefinition = "text")
    @JsonIgnore
    private String refreshToken;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false,
    foreignKey = @ForeignKey(name = "empresa_fk"))
    private Empresa empresa;

    @OneToOne
    @JoinColumn(name = "cliente_funcionario_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "cliente_funcionario_fk"))
    private ClienteFuncionario clienteFuncionario;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_usuario",
            uniqueConstraints = @UniqueConstraint(name="unique_role_user",
                    columnNames = { "acesso_id", "usuario_id" }), /*Contraint de unicidade entre usuario e acesso*/

            joinColumns = @JoinColumn(name = "usuario_id",
                    foreignKey = @ForeignKey(name="usuario_fk")),   /*Representa a tabela de usuário e acesso*/

            inverseJoinColumns = @JoinColumn(name="acesso_id",
                    foreignKey = @ForeignKey(name="acesso_fk"))

    )
    private List<Role> acessos = new ArrayList<Role>();


    public void associarEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void desassociarUsuario() {
        this.empresa = null;
    }

    public void addRole(Role role) {
        this.acessos.add(role);
    }

    public void removeRole(Role role) {
        this.acessos.remove(role);
    }


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.acessos;
    }


    @JsonIgnore
    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }


    @Override
    public String getUsername() {
        return this.login;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return liberado;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }




    public boolean idAdmin() {

        if(this.acessos == null || this.acessos.isEmpty()) {
            return false;
        }

        return this.acessos.stream().anyMatch(a -> a.getAcesso().equals("ROLE_ADMIN"));
    }
}
