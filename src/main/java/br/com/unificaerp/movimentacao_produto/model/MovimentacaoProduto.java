package br.com.unificaerp.movimentacao_produto.model;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.movimentacao_produto.model.enums.TipoMovimentacao;
import br.com.unificaerp.pedido.model.Pedido;
import br.com.unificaerp.produto.model.Produto;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movimentacao_produto")
@SequenceGenerator(name = "seq_movimentacao_produto", sequenceName = "seq_movimentacao_produto", allocationSize = 1, initialValue = 1)
public class MovimentacaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimentacao_produto")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private Double quantidade;
    private LocalDate dataMovimentacao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "pedido_fk"))
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "produto_fk"))
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "empresa_fk"))
    private Empresa empresa;
}
