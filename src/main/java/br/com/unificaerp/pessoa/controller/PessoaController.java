package br.com.unificaerp.pessoa.controller;

import br.com.unificaerp.pessoa.model.Pessoa;
import br.com.unificaerp.pessoa.model.dto.PessoaDTO;
import br.com.unificaerp.pessoa.service.PessoaService;
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
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    private final UsuarioLogadoService usuarioLogadoService;

    public PessoaController(PessoaService pessoaService, UsuarioLogadoService usuarioLogadoService) {
        this.pessoaService = pessoaService;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<PessoaDTO> salvar(@RequestBody @Valid Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.salvar(pessoa));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<PessoaDTO> atualizar(@RequestBody @Valid Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.atualizar(pessoa));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PessoaDTO>> findAll() {
        return ResponseEntity.ok(pessoaService.findAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<PessoaDTO>> buscaPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(pessoaService.buscaPorNome(nome,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorNome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome) {
        return ResponseEntity.ok(pessoaService.existePorNome(nome,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorNomeDiferenteId/{id}/{nome}")
    public ResponseEntity<Boolean> existePorNomeDiferenteId(
            @PathVariable Long id, @PathVariable String nome) {
        return ResponseEntity.ok(pessoaService.existePorNomeDiferenteId(id, nome,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<PessoaDTO>> listar() {
        return ResponseEntity.ok(pessoaService.listar(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorIdDto(id, usuarioLogadoService.getEmpresaIdLogada())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/existePorId/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.existsById(id,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @PostMapping("/buscarPorIds")
    public ResponseEntity<List<PessoaDTO>> buscarPorIds(@RequestBody Iterable<Long> ids) {
        return ResponseEntity.ok(pessoaService.buscarPorIds(ids,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> total() {
        return ResponseEntity.ok(pessoaService.total(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<PessoaDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return ResponseEntity.ok(pessoaService.listarPaginado(
                usuarioLogadoService.getEmpresaIdLogada(), pageable));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        pessoaService.deleteById(id, usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.ok("Pessoa deletada com sucesso.");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletarTodos")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(pessoaService.deleteAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletarTodosPorIds")
    public ResponseEntity<Void> deletarAllById(@RequestBody Iterable<Long> ids) {
        pessoaService.deletarAllById(ids,
                usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.noContent().build();
    }
}