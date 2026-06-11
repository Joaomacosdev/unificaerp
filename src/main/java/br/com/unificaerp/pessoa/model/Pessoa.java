package br.com.unificaerp.pessoa.model;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.pessoa.model.enums.TipoPessoa;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pessoas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_pessoa_razao_social", columnNames = "razaoSocial"),
                @UniqueConstraint(name = "uk_pessoa_insc_estadual", columnNames = "inscEstadual"),
                @UniqueConstraint(name = "uk_pessoa_cnpj", columnNames = "cnpj")
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
    @Column(name = "cep", nullable = false,length = 8)
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

    //private Empresa empresa;

}
