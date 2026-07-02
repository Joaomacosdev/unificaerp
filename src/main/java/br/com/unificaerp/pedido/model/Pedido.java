package br.com.unificaerp.pedido.model;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.pedido.model.enums.FormaPagamento;
import br.com.unificaerp.pedido.model.enums.StatusPedido;
import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@SequenceGenerator(name = "seq_pedido", sequenceName = "seq_pedido", allocationSize = 1, initialValue = 1)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true, length = 30)
    private String numeroPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusPedido status;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false, length = 30)
    private FormaPagamento formaPagamento;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;
    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;
    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;
    @Column(name = "sub_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal subTotal = BigDecimal.ZERO;
    @Column(name = "desconto", nullable = false, precision = 15, scale = 2)
    private BigDecimal desconto = BigDecimal.ZERO;
    @Column(name = "frete", nullable = false, precision = 15, scale = 2)
    private BigDecimal frete = BigDecimal.ZERO;
    @Column(name = "taxa", nullable = false, precision = 15, scale = 2)
    private BigDecimal taxa = BigDecimal.ZERO;
    @Column(name = "total", nullable = false, precision = 15, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "observacao", length = 500)
    private String observacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cliente_fk"))
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "vendedor_fk"))
    private Usuario vendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
    private Empresa empresa;


}
