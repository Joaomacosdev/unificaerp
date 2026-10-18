package br.com.unificaerp.role_usuario.model;

import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role_usuario", uniqueConstraints = {
        @UniqueConstraint(name = "unique_role_user", columnNames = { "acesso_id", "usuario_id" }) })
@SequenceGenerator(name = "seq_role_usuario", sequenceName = "seq_role_usuario", allocationSize = 1, initialValue = 1)
public class RoleUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_usuario")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acesso_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "acesso_fk"))
    private Role acesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "usuario_fk"))
    private Usuario usuario;

}
