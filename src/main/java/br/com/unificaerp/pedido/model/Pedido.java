package br.com.unificaerp.pedido.model;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.pedido.enums.TipoPedido;
import br.com.unificaerp.pedido.model.enums.FormaPagamento;
import br.com.unificaerp.pedido.model.enums.StatusPedido;
import br.com.unificaerp.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@SequenceGenerator(name = "seq_pedido", sequenceName = "seq_pedido", allocationSize = 1, initialValue = 1)
public class Pedido {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
    private Long id;

    @NotBlank(message = "Inform o número do pedido")
    @Column(nullable = false)
    private String numeroPedido;


    @NotNull(message = "Informa o status do pedido")
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @NotNull(message = "Informe a forma de pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @NotNull(message = "Informe o tipo do pedido")
    @Enumerated(EnumType.STRING)
    private TipoPedido tipoPedido;

    @NotNull(message = "Informe a forma de pagamento")
    @Column(nullable = false)
    private LocalDate dataPedido;

    private LocalDate dataPagamento;
    private LocalDate dataCancelamento;

    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private BigDecimal frete = BigDecimal.ZERO;
    private BigDecimal taxas = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "vendedor_fk"))
    private Usuario vendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "cliente_fk"))
    private Usuario cliente;


    @NotNull(message = "Empresa deve ser informada corretamente")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "empresa_fk"))
    private Empresa empresa;


}
