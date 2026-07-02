package br.com.unificaerp.produto.model;

import br.com.unificaerp.categoria.model.Categoria;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.produto.model.enums.UnidadeMedida;
import jakarta.persistence.*;

@Entity
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(name = "uk_produto_sku", columnNames = "sku"),
        @UniqueConstraint(name = "uk_produto_empresa", columnNames = "empresa")
})
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;
    @Column(name = "imagem", nullable = false, columnDefinition = "TEXT")
    private String imagem;
    @Column(name = "preco", nullable = false)
    private Double preco;
    @Column(name = "estoque", nullable = false)
    private Double estoque;
    @Column(name = "sku", nullable = false)
    private String SKU;
    @Column(name = "codigo_barra")
    private String codigoBarra;

    @Column(name = "unidade_medida", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_fk"))
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
    private Empresa empresa;

}
