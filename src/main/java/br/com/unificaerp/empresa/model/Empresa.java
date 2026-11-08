package br.com.unificaerp.empresa.model;

import br.com.unificaerp.pessoa.model.Pessoa;
import br.com.unificaerp.plano.model.Plano;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@JsonIdentityReference(alwaysAsId = true)
@Data
@Entity
@Table(name = "empresas", uniqueConstraints =
@UniqueConstraint(name = "uk_empresa_pessoa_id", columnNames = "pessoa_id")
)
@SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa", allocationSize = 1, initialValue = 1)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    private Long id;
    @Column(name = "total_usuario", nullable = true)
    private Integer totalUsuario = 0;
    @Column(name = "total_cliente", nullable = true)
    private Integer totalCliente = 0;

    private Boolean planoAtivo = false;
    private Boolean bloqueio = false;

    @Column(name = "logo_marca", columnDefinition = "TEXT")
    private String logoMarca;

    private LocalDate vigenciaPlano;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
    private Pessoa pessoa;


    @ManyToOne(fetch = FetchType.LAZY) /* LAZY -> Carrega o plano quando tiver necessidade */
    @JoinColumn(name = "plano_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "plano_fk"))
    private Plano plano;





}
