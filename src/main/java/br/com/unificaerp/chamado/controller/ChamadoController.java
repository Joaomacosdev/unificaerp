package br.com.unificaerp.chamado.controller;

import br.com.unificaerp.chamado.model.Chamado;
import br.com.unificaerp.chamado.service.ChamadoService;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamado")
public class ChamadoController {

    private final ChamadoService chamadoService;

    private final UsuarioLogadoService usuarioLogadoService;

    public ChamadoController(ChamadoService chamadoService, UsuarioLogadoService usuarioLogadoService) {
        this.chamadoService = chamadoService;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Chamado> salvar(@RequestBody @Valid Chamado chamado) {
        return ResponseEntity.ok(chamadoService.salvar(chamado));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Chamado> atualizar(@RequestBody @Valid Chamado chamado) {
        return ResponseEntity.ok(chamadoService.atualizar(chamado));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Chamado>> listar() {
        return ResponseEntity.ok(chamadoService.findAll(
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Chamado> buscarPorId(@PathVariable Long id) {
        return chamadoService.buscarPorId(id, usuarioLogadoService.getEmpresaIdLogada())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscaPorTitulo/{titulo}")
    public ResponseEntity<List<Chamado>> buscaPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(chamadoService.buscaPorTitulo(titulo,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorTitulo/{titulo}")
    public ResponseEntity<Boolean> existePorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(chamadoService.existePorTitulo(titulo,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorTituloDiferenteId/{id}/{titulo}")
    public ResponseEntity<Boolean> existePorTituloDiferenteId(
            @PathVariable Long id, @PathVariable String titulo) {
        return ResponseEntity.ok(chamadoService.existePorTituloDiferenteId(id, titulo,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletePorId(@PathVariable Long id) {
        chamadoService.deleteById(id, usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.ok("Chamado deletado com sucesso.");
    }

}
