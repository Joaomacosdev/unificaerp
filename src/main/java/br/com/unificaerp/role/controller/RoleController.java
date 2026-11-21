package br.com.unificaerp.role.controller;

import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.role.model.dto.RoleDTO;
import br.com.unificaerp.role.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<RoleDTO> salvar(@RequestBody @Valid Role role){
        return ResponseEntity.ok(roleService.salvar(role));
    }


    @PostMapping("/atualizar")
    public ResponseEntity<RoleDTO> atualizar(@RequestBody @Valid Role role){
        return ResponseEntity.ok(roleService.atualizar(role));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RoleDTO>> findAll() {
        return ResponseEntity.ok(roleService.listar());
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<RoleDTO> buscarPorId(@PathVariable Long id) {
        return roleService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscaPorAcesso/{acesso}")
    public ResponseEntity<List<RoleDTO>> buscaPorAcesso(@PathVariable String acesso) {
        return ResponseEntity.ok(roleService.buscaPorAcessoDto(acesso));
    }

    @GetMapping("/existePorAcesso/{acesso}")
    public ResponseEntity<Boolean> existePorAcesso(@PathVariable String acesso) {
        return ResponseEntity.ok(roleService.existePorAcesso(acesso));
    }

    @GetMapping("/existePorAcessoDiferenteId/{id}/{acesso}")
    public ResponseEntity<Boolean> existePorAcessoDiferenteId(
            @PathVariable Long id, @PathVariable String acesso) {
        return ResponseEntity.ok(roleService.existePorAcessoDiferenteId(id, acesso));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deletado com sucesso.");
    }
}