package br.com.unificaerp.cliente_funcionario.controller;

import br.com.unificaerp.cliente_funcionario.dto.ClienteFuncionarioDTO;
import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.cliente_funcionario.service.ClienteFuncionarioService;
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
@RequestMapping("/api/clienteFuncionario")
public class ClienteFuncionarioController {

    private final ClienteFuncionarioService clienteFuncionarioService;

    private final UsuarioLogadoService usuarioLogadoService;

    public ClienteFuncionarioController(ClienteFuncionarioService clienteFuncionarioService, UsuarioLogadoService usuarioLogadoService) {
        this.clienteFuncionarioService = clienteFuncionarioService;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<ClienteFuncionarioDTO> salvar(
            @RequestBody @Valid ClienteFuncionario clienteFuncionario) {
        return ResponseEntity.ok(clienteFuncionarioService.salvar(clienteFuncionario));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<ClienteFuncionarioDTO> atualizar(
            @RequestBody @Valid ClienteFuncionario clienteFuncionario) {
        return ResponseEntity.ok(clienteFuncionarioService.atualizar(clienteFuncionario));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteFuncionarioDTO>> listar() {
        return ResponseEntity.ok(clienteFuncionarioService.listar(usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<ClienteFuncionarioDTO>> buscaPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(clienteFuncionarioService.buscaPorNome(nome,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorNome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome) {
        return ResponseEntity.ok(clienteFuncionarioService.existePorNome(nome,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorNomeDiferenteId/{id}/{nome}")
    public ResponseEntity<Boolean> existePorNomeDiferenteId(
            @PathVariable Long id, @PathVariable String nome) {
        return ResponseEntity.ok(clienteFuncionarioService.existePorNomeDiferenteId(id, nome,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ClienteFuncionarioDTO> buscarPorId(@PathVariable Long id) {
        return clienteFuncionarioService.buscarPorId(id, usuarioLogadoService.getEmpresaIdLogada())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/existePorId/{id}")
    public ResponseEntity<Boolean> existePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteFuncionarioService.existsById(id,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscarPorPessoa/{idPessoa}")
    public ResponseEntity<ClienteFuncionarioDTO> buscarPorPessoa(@PathVariable Long idPessoa) {
        return ResponseEntity.ok(clienteFuncionarioService.findByPessoaDto(idPessoa,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @PostMapping("/buscarPorIds")
    public ResponseEntity<List<ClienteFuncionarioDTO>> buscarPorIds(@RequestBody Iterable<Long> ids) {
        return ResponseEntity.ok(clienteFuncionarioService.buscarPorIds(ids,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> total() {
        return ResponseEntity.ok(clienteFuncionarioService.total(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ClienteFuncionarioDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return ResponseEntity.ok(clienteFuncionarioService.listarPaginado(
                usuarioLogadoService.getEmpresaIdLogada(), pageable));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        clienteFuncionarioService.deleteById(id, usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.ok("Cliente funcionário deletado com sucesso.");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletarTodos")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(clienteFuncionarioService.deleteAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletarTodosPorIds")
    public ResponseEntity<Void> deletarAllById(@RequestBody Iterable<Long> ids) {
        clienteFuncionarioService.deletarAllById(ids,
                usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/removerUsuario/{id}")
    public ResponseEntity<Void> removeUserClienteFuncionarioId(@PathVariable Long id) {
        clienteFuncionarioService.removeUserClienteFuncionarioId(id,
                usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.noContent().build();
    }
}