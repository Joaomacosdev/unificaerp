package br.com.unificaerp.chamado.model;

import br.com.unificaerp.chamado.model.enums.Prioridade;
import br.com.unificaerp.chamado.model.enums.PrioridadeChamado;
import br.com.unificaerp.chamado.model.enums.StatusChamado;
import br.com.unificaerp.chamado.model.enums.TipoChamado;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.menssagem.model.Menssagem;
import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chamados")
@SequenceGenerator(name = "seq_chamado", sequenceName = "seq_chamado", allocationSize = 1, initialValue = 1)
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chamado")
    private Long id;

    private String titulo;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TipoChamado tipoChamado;
    @Enumerated(EnumType.STRING)
    private StatusChamado statusChamado;
    @Enumerated(EnumType.STRING)
    private PrioridadeChamado prioridadeChamado;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private Prioridade prioridade;

    @OneToMany(mappedBy = "chamado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Menssagem> menssagens = new ArrayList<Menssagem>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aberto_user_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "aberto_user_fk"))
    private Usuario abertoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fechado_user_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fechado_user_fk"))
    private Usuario fechadoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "atendente_fk"))
    private Usuario atendente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cliente_fk"))
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false,
     foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_dk"))
    private Empresa empresa;



}
