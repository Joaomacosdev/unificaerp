package br.com.unificaerp.plano.model;

import br.com.unificaerp.plano.model.enums.TipoPlano;
import jakarta.persistence.*;

import java.math.BigDecimal;

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

}
