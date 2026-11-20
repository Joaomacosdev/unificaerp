package br.com.unificaerp.item_pedido.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {

    private Long id;
    private Double quantidade;
    private BigDecimal subTotal;
    private BigDecimal desconto;
    private BigDecimal total;
    private Long produtoId;
    private String produtoNome;
    private Long pedidoId;
    private String numeroPedido;
    private Long empresaId;

}
