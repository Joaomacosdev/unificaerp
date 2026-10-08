package br.com.unificaerp.movimentacao_produto.service;

import br.com.unificaerp.movimentacao_produto.model.MovimentacaoProduto;
import br.com.unificaerp.movimentacao_produto.repository.MovimentacaoProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoProdutoService {

    private final MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    /*Posso escrever query customizadas e dinâmicas, mais complexas do que no Repository*/
    @PersistenceContext
    private EntityManager entityManager;

    public MovimentacaoProdutoService(MovimentacaoProdutoRepository movimentacaoProdutoRepository) {
        this.movimentacaoProdutoRepository = movimentacaoProdutoRepository;
    }

    public List<MovimentacaoProduto> findAll(Long idEmpresa) {

        return movimentacaoProdutoRepository.findAll(idEmpresa);
    }

    public List<MovimentacaoProduto> buscaPorNome(String nome, Long idEmpresa) {
        return movimentacaoProdutoRepository.buscaPorNome(nome, idEmpresa);
    }

    public boolean existePorNome(String nome, Long idEmpresa) {
        return movimentacaoProdutoRepository.existePorNome(nome, idEmpresa);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
        return movimentacaoProdutoRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
    }

    public void deleteById(Long id, Long idEmpresa) {
        movimentacaoProdutoRepository.deleteById(id, idEmpresa);
    }

    public long deleteAll(Long empresaID) {
        return movimentacaoProdutoRepository.deleteAll(empresaID);
    }

    void deletarAllById(Iterable<Long> ids, Long empresaId) {
        movimentacaoProdutoRepository.deletarAllById(ids, empresaId);
    }

    public List<MovimentacaoProduto> buscarPorIds(Iterable<Long> ids, Long empresaId) {
        return movimentacaoProdutoRepository.buscarPorIds(ids, empresaId);
    }

    boolean existsById(Long id, Long empresaId) {
        return movimentacaoProdutoRepository.existsById(id, empresaId);
    }

    public List<MovimentacaoProduto> listar(Long empresaId) {
        return movimentacaoProdutoRepository.listar(empresaId);
    }

    public Optional<MovimentacaoProduto> buscarPorId(Long id, Long empresaId) {
        return movimentacaoProdutoRepository.buscarPorId(id, empresaId);
    }

    public long total(Long empresaId) {
        return movimentacaoProdutoRepository.total(empresaId);
    }

    public Page<MovimentacaoProduto> listarPaginado(Long empresaId, Pageable pageable) {
        return movimentacaoProdutoRepository.listarPaginado(empresaId, pageable);
    }

    // ====================Métodos específicos para Pedido====================

    public List<MovimentacaoProduto> findAllByPedido(Long idPedido, Long idEmpresa) {
        return movimentacaoProdutoRepository.findAllByPedido(idPedido, idEmpresa);
    }

    public List<MovimentacaoProduto> buscaPorNomeByPedido(String nome, Long idPedido, Long idEmpresa) {
        return movimentacaoProdutoRepository.buscaPorNomeByPedido(nome, idPedido, idEmpresa);
    }

    public boolean existePorNomeByPedido(String nome, Long idPedido, Long idEmpresa) {
        return movimentacaoProdutoRepository.existePorNomeByPedido(nome, idPedido, idEmpresa);
    }

    public boolean existePorNomeDiferenteIdByPedido(Long id, String nome, Long idPedido, Long idEmpresa) {
        return movimentacaoProdutoRepository.existePorNomeDiferenteIdByPedido(id, nome, idPedido, idEmpresa);
    }

    public void deleteByIdAndPedido(Long id, Long idPedido, Long idEmpresa) {
        movimentacaoProdutoRepository.deleteByIdAndPedido(id, idPedido, idEmpresa);
    }

    // ====================dentro dos métodos do
    // service===============================

    // Verificar se está em uso
    // Realizar um consulta com integração para saber se pode deletar
    // Fazer copia e backup
    // Fazer inumeras validações de regra de negocio
    // Fazer validações
    // Lançar exeções
    // Escrever regras de negócio

}
