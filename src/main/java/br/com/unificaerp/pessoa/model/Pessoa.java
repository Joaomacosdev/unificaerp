package br.com.unificaerp.pessoa.model;

import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.pessoa.model.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
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

    @NotBlank(message = "Nome deve ser informado")
    @Column(nullable = false)
    @ToString.Include
    private String nome;

    @Column
    @ToString.Include
    private String razaoSocial;

    @Column
    @ToString.Include
    private String nomeFantasia;

    @Column(unique = true, name = "inscricao_estadual")
    private String inscricaoEstadual;

    /*
     * Documentos (P13, script V2): e-mail obrigatório para todos, CPF só para
     * pessoa física e CNPJ só para pessoa jurídica. Os três são únicos no banco
     * inteiro (P12); a unique do PostgreSQL aceita vários NULL. Em branco vira
     * NULL nos setters, senão dois cadastros sem o documento bateriam na unique.
     */
    @CNPJ(message = "Informe o CNPJ corretamente",format = CNPJ.Format.ALPHANUMERIC)
    @Column(unique = true)
    private String cnpj;

    @NotBlank(message = "Informe o telefone corretamente")
    @Column(nullable = false)
    private String telefone;

    @CPF(message = "Informe o CPF corretamente")
    @Column(unique = true)
    private String cpf;

    @NotBlank(message = "Informe o e-mail corretamente")
    @Email(message = "Informe o e-mail corretamente")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Informe o tipo da pessoa PF ou PJ")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ToString.Include
    private TipoPessoa tipoPessoa;

    @NotNull(message = "Informe se a pessoa está ativa")
    @Column(nullable = false)
    @ToString.Include
    private Boolean ativo = true;

    /*Preenchida pelo PessoaService no cadastro e mantida na edição (P20). A coluna é NOT NULL.*/
    @Column(nullable = false)
    @ToString.Include
    private LocalDate dataCadastro;

    @Column(length = 1000)
    private String observacao;

    @NotBlank(message = "Informe o CEP corretamente")
    @Column(nullable = false)
    private String cep;

    @NotBlank(message = "Informe a rua/logradouro da forma correta")
    @Column(nullable = false)
    private String logradouro;

    @NotBlank(message = "Informe o Bairro corretamente")
    @Column(nullable = false)
    private String bairro;

    @NotBlank(message = "Informe o Estado corretamente")
    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "Informe o Cidade corretamente")
    @Column(nullable = false)
    private String cidade;

    @NotBlank(message = "Informe o País corretamente")
    @Column(nullable = false)
    private String pais;

    @Column
    private String complemento;

    /*Refere-se ao cadastro da empresa em multitanci*/
    @ManyToOne(fetch = FetchType.LAZY) /* LAZY -> Carrega a empresa quando tiver necessidade */
    @JoinColumn(name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "empresa_fk"))
    private Empresa empresa;

    public void setCpf(String cpf) {
        this.cpf = vazioParaNulo(cpf);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = vazioParaNulo(cnpj);
    }

    /*Mesma regra das CHECKs pessoa_cpf_pessoa_fisica_check e pessoa_cnpj_pessoa_juridica_check do banco (V2)*/
    @JsonIgnore
    @AssertTrue(message = "Informe o CPF para pessoa física")
    public boolean isCpfInformadoParaPessoaFisica() {
        return tipoPessoa != TipoPessoa.FISICA || cpf != null;
    }

    @JsonIgnore
    @AssertTrue(message = "Informe o CNPJ para pessoa jurídica")
    public boolean isCnpjInformadoParaPessoaJuridica() {
        return tipoPessoa != TipoPessoa.JURIDICA || cnpj != null;
    }

    private static String vazioParaNulo(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

}