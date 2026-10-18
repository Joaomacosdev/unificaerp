package br.com.unificaerp.role.model;

import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles",
 uniqueConstraints = {
        @UniqueConstraint(name = "uk_role_acesso", columnNames = "acesso")
 })
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1)
@Getter
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
    private Long id;

    @NotBlank(message = "Acesso deve ser informado")
    @Column(nullable = false)
    private String acesso;

    /*Nome do acesso*/
    @Override
    public @Nullable String getAuthority() {
        return this.acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso != null ? acesso.toUpperCase(): "";
    }


}
