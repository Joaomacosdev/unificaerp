package br.com.unificaerp.pedido.controller;

import br.com.unificaerp.pedido.model.Pedido;
import br.com.unificaerp.pedido.service.PedidoService;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    private final UsuarioLogadoService usuarioLogadoService;

    public PedidoController(PedidoService pedidoService, UsuarioLogadoService usuarioLogadoService) {
        this.pedidoService = pedidoService;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Pedido> salvar(@RequestBody @Valid Pedido pedido) {
        return ResponseEntity.ok(pedidoService.salvar(pedido));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Pedido> atualizar(@RequestBody @Valid Pedido pedido) {
        return ResponseEntity.ok(pedidoService.atualizar(pedido));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscaPorNumeroPedido/{numeroPedido}")
    public ResponseEntity<List<Pedido>> buscaPorNumeroPedido(@PathVariable String numeroPedido) {
        return ResponseEntity.ok(pedidoService.buscaPorNumeroPedido(numeroPedido,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorNumeroPedido/{numeroPedido}")
    public ResponseEntity<Boolean> existePorNumeroPedido(@PathVariable String numeroPedido) {
        return ResponseEntity.ok(pedidoService.existePorNumeroPedido(numeroPedido,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorNumeroPedidoDiferenteId/{id}/{numeroPedido}")
    public ResponseEntity<Boolean> existePorNumeroPedidoDiferenteId(
            @PathVariable Long id, @PathVariable String numeroPedido) {
        return ResponseEntity.ok(pedidoService.existePorNumeroPedidoDiferenteId(id, numeroPedido,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listar(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id, usuarioLogadoService.getEmpresaIdLogada())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/existePorId/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.existsById(id,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @PostMapping("/buscarPorIds")
    public ResponseEntity<List<Pedido>> buscarPorIds(@RequestBody Iterable<Long> ids) {
        return ResponseEntity.ok(pedidoService.buscarPorIds(ids,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> total() {
        return ResponseEntity.ok(pedidoService.total(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<Pedido>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return ResponseEntity.ok(pedidoService.listarPaginado(
                usuarioLogadoService.getEmpresaIdLogada(), pageable));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        pedidoService.deleteById(id, usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.ok("Pedido deletado com sucesso.");
    }

    @DeleteMapping("/deletarTodos")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(pedidoService.deleteAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @DeleteMapping("/deletarTodosPorIds")
    public ResponseEntity<Void> deletarAllById(@RequestBody Iterable<Long> ids) {
        pedidoService.deletarAllById(ids,
                usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.noContent().build();
    }
}
