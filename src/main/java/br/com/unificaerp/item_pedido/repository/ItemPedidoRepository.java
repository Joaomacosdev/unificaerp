package br.com.unificaerp.item_pedido.repository;

import br.com.unificaerp.item_pedido.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
