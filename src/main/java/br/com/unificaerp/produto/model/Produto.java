package br.com.unificaerp.produto.model;

import br.com.unificaerp.categoria.model.Categoria;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.produto.model.enums.UnidadeMedida;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(name = "uk_produto_sku", columnNames = "sku"),
        @UniqueConstraint(name = "uk_produto_empresa", columnNames = "empresa")
})
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
    private Long id;

    @NotBlank(message = "Nome deve ser informado")
    @Column(nullable = false)
    @ToString.Include
    private String nome;

    private String descricao;

    @NotBlank(message = "Imagem deve ser informada")
    @Column(nullable = false, columnDefinition = "text")
    private String imagem;

    /*Preço, estoque e estoque mínimo: no mínimo 1, como os CHECK do banco (P15)*/
    @NotNull(message = "Preço deve ser informado")
    @Min(value = 1, message = "Preço deve ser de no mínimo R$ 1,00")
    @Column(nullable = false)
    @ToString.Include
    private Double preco;

    @NotNull(message = "Estoque deve ser informado")
    @Min(value = 1, message = "Estoque deve ser de no mínimo 1")
    @Column(nullable = false)
    @ToString.Include
    private Double estoque;

    @NotNull(message = "Estoque mínimo deve ser informado")
    @Min(value = 1, message = "Estoque mínimo deve ser de no mínimo 1")
    @Column(nullable = false)
    @ToString.Include
    private Double estoqueMinimo;

    @NotBlank(message = "Código SKU deve ser informado")
    @Column(nullable = false)
    @ToString.Include
    private String sku;

    private String codigoBarra; /*Varios tipo de parafuso*/

    @NotNull(message = "Unidade de medida de ser informado")
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida", nullable = false)
    @ToString.Include
    private UnidadeMedida unidadeMedida;

    @NotNull(message = "Categoria deve ser informada.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "categoria_fk"))
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "empresa_fk"))
    private Empresa empresa;

}