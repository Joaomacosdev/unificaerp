package br.com.unificaerp.pedido.controller;

import br.com.unificaerp.pedido.model.Pedido;
import br.com.unificaerp.pedido.model.dto.PedidoDTO;
import br.com.unificaerp.pedido.service.PedidoService;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    private final UsuarioLogadoService usuarioLogadoService;

    public PedidoController(PedidoService pedidoService, UsuarioLogadoService usuarioLogadoService) {
        this.pedidoService = pedidoService;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @PostMapping("/atualizar")
    public ResponseEntity<PedidoDTO> atualizar(@RequestBody @Valid Pedido pedido) {
        return ResponseEntity.ok(pedidoService.atualizar(pedido));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoDTO>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscaPorNumeroPedido/{numeroPedido}")
    public ResponseEntity<List<PedidoDTO>> buscaPorNumeroPedido(@PathVariable String numeroPedido) {
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
    public ResponseEntity<List<PedidoDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listar(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
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
    public ResponseEntity<List<PedidoDTO>> buscarPorIds(@RequestBody Iterable<Long> ids) {
        return ResponseEntity.ok(pedidoService.buscarPorIds(ids,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> total() {
        return ResponseEntity.ok(pedidoService.total(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<PedidoDTO>> listarPaginado(
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletarTodos")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(pedidoService.deleteAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletarTodosPorIds")
    public ResponseEntity<Void> deletarAllById(@RequestBody Iterable<Long> ids) {
        pedidoService.deletarAllById(ids,
                usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.noContent().build();
    }
}