package br.com.unificaerp.plano.controller;

import br.com.unificaerp.plano.model.Plano;
import br.com.unificaerp.plano.model.dto.PlanoDTO;
import br.com.unificaerp.plano.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/plano")
public class PlanoController  {


    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }


    @PostMapping("/salvar")
    public ResponseEntity<PlanoDTO> salvar(@RequestBody @Valid Plano plano) {
        return ResponseEntity.ok(planoService.salvar(plano));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<PlanoDTO> atualizar(@RequestBody @Valid Plano plano) {
        return ResponseEntity.ok(planoService.atualizar(plano));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PlanoDTO>> findAll() {
        return ResponseEntity.ok(planoService.findAll());
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<PlanoDTO> buscarPorId(@PathVariable Long id) {
        return planoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<PlanoDTO>> buscaPorNome(@PathVariable String nome) {
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