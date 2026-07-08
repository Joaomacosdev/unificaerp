package br.com.unificaerp.movimentacao_produto.repository;

import br.com.unificaerp.movimentacao_produto.model.MovimentacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoProduto, Long> {
}
