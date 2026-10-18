package br.com.unificaerp.plano.controller;

import br.com.unificaerp.plano.model.Plano;
import br.com.unificaerp.plano.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plano")
public class PlanoController  {


    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }


    @PostMapping("/salvar")
    public ResponseEntity<Plano> salvar(@RequestBody @Valid Plano plano) {
        return ResponseEntity.ok(planoService.salvar(plano));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Plano> atualizar(@RequestBody @Valid Plano plano) {
        return ResponseEntity.ok(planoService.atualizar(plano));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Plano>> findAll() {
        return ResponseEntity.ok(planoService.findAll());
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Long id) {
        return planoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<Plano>> buscaPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(planoService.buscaPorNome(nome));
    }

    @GetMapping("/existePorNome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome) {
        return ResponseEntity.ok(planoService.existePorNome(nome));
    }

    @GetMapping("/existePorNomeDiferenteId/{id}/{nome}")
    public ResponseEntity<Boolean> existePorNomeDiferenteId(
            @PathVariable Long id, @PathVariable String nome) {
        return ResponseEntity.ok(planoService.existePorNomeDiferenteId(id, nome));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        planoService.deleteById(id);
        return ResponseEntity.ok("Plano deletado com sucesso.");
    }

}
