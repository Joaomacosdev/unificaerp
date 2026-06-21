package br.com.unificaerp.pessoa.model;

import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.pessoa.model.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pessoas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_pessoa_razao_social", columnNames = "razaoSocial"),
                @UniqueConstraint(name = "uk_pessoa_insc_estadual", columnNames = "inscEstadual"),
                @UniqueConstraint(name = "uk_pessoa_cnpj", columnNames = "cnpj"),
                @UniqueConstraint(name = "uk_pessoa_cpf", columnNames = "cpf"),
                @UniqueConstraint(name = "uk_pessoa_email", columnNames = "email")
        })
@SequenceGenerator(name = "seq_pessoa", sequenceName = "seq_pessoa", allocationSize = 1, initialValue = 1)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "razao_social", nullable = false, length = 60)
    private String razaoSocial;
    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;
    @Column(name = "insc_estadual", nullable = false, length = 14)
    private String inscEstadual;
    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    private Boolean ativo;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "observacao")
    private String observacao;
    @Column(name = "cep", nullable = false, length = 8)
    private String cep;
    @Column(name = "logradouro", length = 100)
    private String logradouro;
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;
    @Column(name = "pais", nullable = false, length = 100)
    private String pais;
    @Column(name = "complemento", nullable = false, length = 150)
    private String complemento;

    /*Refere-se ao cadastro da empresa em multitanci*/
    @ManyToOne(fetch = FetchType.LAZY) /* LAZY -> Carrega a empresa quando tiver necessidade */
    @JoinColumn(name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "empresa_fk"))
    private Empresa empresa;

    @JsonBackReference
    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClienteFuncionario clienteFuncionario;

    public Pessoa() {
    }

    public Pessoa(String nome, String razaoSocial, String nomeFantasia, String inscEstadual, String cnpj, String cpf, String email, String telefone, TipoPessoa tipoPessoa, Boolean ativo, LocalDateTime dataCadastro, String observacao, String cep, String logradouro, String bairro, String estado, String cidade, String pais, String complemento, Empresa empresa, ClienteFuncionario clienteFuncionario) {
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscEstadual = inscEstadual;
        this.cnpj = cnpj;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.observacao = observacao;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.estado = estado;
        this.cidade = cidade;
        this.pais = pais;
        this.complemento = complemento;
        this.empresa = empresa;
        this.clienteFuncionario = clienteFuncionario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", inscEstadual='" + inscEstadual + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipoPessoa=" + tipoPessoa +
                ", ativo=" + ativo +
                ", dataCadastro=" + dataCadastro +
                ", observacao='" + observacao + '\'' +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", pais='" + pais + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }
}
