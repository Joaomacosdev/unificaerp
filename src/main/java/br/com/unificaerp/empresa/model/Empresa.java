package br.com.unificaerp.empresa.model;

import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.pessoa.model.Pessoa;
import br.com.unificaerp.plano.model.Plano;
import br.com.unificaerp.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<ClienteFuncionario> clienteFuncionarios = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    public Empresa() {
    }

    public Empresa(Integer totalUsuario, Integer totalCliente, Boolean planoAtivo, Boolean bloqueio, String logoMarca, LocalDate vigenciaPlano, Pessoa pessoa, Plano plano, List<ClienteFuncionario> clienteFuncionarios, List<Usuario> usuarios) {
        this.totalUsuario = totalUsuario;
        this.totalCliente = totalCliente;
        this.planoAtivo = planoAtivo;
        this.bloqueio = bloqueio;
        this.logoMarca = logoMarca;
        this.vigenciaPlano = vigenciaPlano;
        this.pessoa = pessoa;
        this.plano = plano;
        this.clienteFuncionarios = clienteFuncionarios;
        this.usuarios = usuarios;
    }

    public void adicionarClienteFuncionario(ClienteFuncionario clienteFuncionario) {
        clienteFuncionarios.add(clienteFuncionario);
        clienteFuncionario.associarEmpresa(this);
    }

    public void removerClienteFuncionario(ClienteFuncionario clienteFuncionario) {
        clienteFuncionarios.remove(clienteFuncionario);
        clienteFuncionario.desassociarEmpresa();
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        usuario.associarEmpresa(this);
    }

    public void removerUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        usuario.desassociarUsuario();
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
