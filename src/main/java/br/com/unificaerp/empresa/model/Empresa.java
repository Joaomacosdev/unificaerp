package br.com.unificaerp.empresa.model;

import br.com.unificaerp.pessoa.model.Pessoa;
import br.com.unificaerp.plano.model.Plano;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "empresas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_empresas_plano", columnNames = "plano")

        })
@SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa", allocationSize = 1, initialValue = 1)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    private Long id;
    @Column(name = "total_usuario", nullable = false)
    private Integer totalUsuario;
    @Column(name = "total_cliente", nullable = false)
    private Integer totalCliente;

    private Boolean planoAtivo;
    private Boolean bloqueio;

    @Column(name = "logo_marca",columnDefinition = "TEXT")
    private String logoMarca;

    private LocalDate vigenciaPlano;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    public Empresa() {
    }

    public Empresa(Integer totalUsuario, Integer totalCliente, Boolean planoAtivo, Boolean bloqueio, String logoMarca, LocalDate vigenciaPlano, Pessoa pessoa, Plano plano) {
        this.totalUsuario = totalUsuario;
        this.totalCliente = totalCliente;
        this.planoAtivo = planoAtivo;
        this.bloqueio = bloqueio;
        this.logoMarca = logoMarca;
        this.vigenciaPlano = vigenciaPlano;
        this.pessoa = pessoa;
        this.plano = plano;
    }


    public void associarPlano(Plano plano) {
        this.plano = plano;
    }

    public void desassociarPlano() {
        this.plano = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "totalUsuario=" + totalUsuario +
                ", totalCliente=" + totalCliente +
                ", planoAtivo=" + planoAtivo +
                ", bloqueio=" + bloqueio +
                ", logoMarca='" + logoMarca + '\'' +
                ", vigenciaPlano=" + vigenciaPlano +
                '}';
    }
}
