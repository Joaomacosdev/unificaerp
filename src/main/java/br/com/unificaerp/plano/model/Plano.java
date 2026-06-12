package br.com.unificaerp.plano.model;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.plano.model.enums.TipoPlano;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "planos")
@SequenceGenerator(name = "seq_plano", sequenceName = "seq_plano", allocationSize = 1, initialValue = 1)
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_plano")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "descricao", nullable = false)
    private String descricao;

    private Boolean ativo;

    @Column(name = "valor_mensal", nullable = false)
    private Double valorMensal;
    @Column(name = "limite_usuario", nullable = false)
    private Integer limiteUsuario;
    @Column(name = "limite_cliente", nullable = false)
    private Integer limiteCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plano", nullable = false)
    private TipoPlano tipoPlano;

    @OneToMany(mappedBy = "plano", fetch = FetchType.LAZY)
    private List<Empresa> empresas = new ArrayList<>();

    public Plano() {
    }

    public Plano(String nome, String descricao, Boolean ativo, Double valorMensal, Integer limiteUsuario, Integer limiteCliente, TipoPlano tipoPlano, List<Empresa> empresas) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.valorMensal = valorMensal;
        this.limiteUsuario = limiteUsuario;
        this.limiteCliente = limiteCliente;
        this.tipoPlano = tipoPlano;
        this.empresas = empresas;
    }

    public void adicionarEmpresa(Empresa empresa){
        empresas.add(empresa);
        empresa.associarPlano(this);
    }

    public void removerEmpresa(Empresa empresa){
        empresas.remove(empresa);
        empresa.desassociarPlano();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Plano plano = (Plano) o;
        return Objects.equals(id, plano.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plano{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                ", valorMensal=" + valorMensal +
                ", limiteUsuario=" + limiteUsuario +
                ", limiteCliente=" + limiteCliente +
                ", tipoPlano=" + tipoPlano +
                '}';
    }
}
