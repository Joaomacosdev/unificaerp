package br.com.unificaerp.pedido.repository;

import br.com.unificaerp.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
