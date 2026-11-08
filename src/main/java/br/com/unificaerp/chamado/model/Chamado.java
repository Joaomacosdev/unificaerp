package br.com.unificaerp.chamado.model;

import br.com.unificaerp.chamado.model.enums.PrioridadeChamado;
import br.com.unificaerp.chamado.model.enums.StatusChamado;
import br.com.unificaerp.chamado.model.enums.TipoChamado;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "chamados")
@Getter
@Setter
@SequenceGenerator(name = "seq_chamado", sequenceName = "seq_chamado", allocationSize = 1, initialValue = 1)
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chamado")
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoChamado tipoChamado;;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeChamado prioridadeChamado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado statusChamado;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    private LocalDate dataFechamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aberto_user_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "aberto_user_fk"))
    private Usuario abertoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fechado_user_id",
            nullable = true,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "fechado_user_fk"))
    private Usuario fechadoUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "atendente_fk"))
    private Usuario atendente;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "cliente_fk"))
    private Usuario cliente;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "empresa_fk"))
    private Empresa empresa;



}
