package br.com.unificaerp.menssagem.model;

import br.com.unificaerp.chamado.model.Chamado;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "menssagens")
@SequenceGenerator(name = "seq_menssagem", sequenceName = "seq_menssagem", allocationSize = 1, initialValue = 1)
public class Menssagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_menssagem")
    private Long id;

    @Column(name = "data_envio", nullable = false)
    private LocalDate dataEnvio;

    private Boolean lida = false;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "arquivo", columnDefinition = "TEXT")
    private String arquivo;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cliente_fk"))
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "atendente_fk"))
    private Usuario atendente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id",
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "chamado_fk"))
    private Chamado chamado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id",
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
    private Empresa empresa;
}
