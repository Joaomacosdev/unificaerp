package br.com.unificaerp.role.model;

import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;
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

public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
    private Long id;
    @Column(nullable = false, length = 50)
    private String acesso;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();

    public Role() {
    }

    public Role(String acesso) {
        this.acesso = acesso;
    }

    @Override
    public @Nullable String getAuthority() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
