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
    @Column(name = "cnpj", nullable = false, length = 18)
    private String cnpj;
    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @Column(name = "telefone", nullable = false, length = 15)
    private String telefone;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    private Boolean ativo;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "observacao")
    private String observacao;
    @Column(name = "cep", nullable = false, length = 9)
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
            nullable = true,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "empresa_fk"))
    private Empresa empresa;



    public Pessoa() {
    }

    public Pessoa(String nome, String razaoSocial, String nomeFantasia, String inscEstadual, String cnpj, String cpf, String email, String telefone, TipoPessoa tipoPessoa, Boolean ativo, LocalDateTime dataCadastro, String observacao, String cep, String logradouro, String bairro, String estado, String cidade, String pais, String complemento, Empresa empresa) {
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
