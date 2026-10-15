package br.com.unificaerp.empresa.controller;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Empresa> salvar(@RequestBody @Valid Empresa empresa) {
        return ResponseEntity.ok(empresaService.salvar(empresa));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Empresa> atualizar(@RequestBody @Valid Empresa empresa) {
        return ResponseEntity.ok(empresaService.atualizar(empresa));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscaPorId(id));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Empresa>> listar() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<Empresa>> buscaPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(empresaService.buscaPorNome(nome));
    }

    @GetMapping("/existePorNome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome) {
        return ResponseEntity.ok(empresaService.existePorNome(nome));
    }

    @GetMapping("/existePorNomeDiferenteId/{id}/{nome}")
    public ResponseEntity<Boolean> existePorNomeDiferenteId(
            @PathVariable Long id, @PathVariable String nome) {
        return ResponseEntity.ok(empresaService.existePorNomeDiferenteId(id, nome));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        empresaService.deleteById(id);
        return ResponseEntity.ok("Empresa deletada com sucesso.");
    }

}
