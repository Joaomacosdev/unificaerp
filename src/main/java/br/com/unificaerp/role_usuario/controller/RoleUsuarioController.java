package br.com.unificaerp.role_usuario.controller;

import br.com.unificaerp.role_usuario.model.RoleUsuario;
import br.com.unificaerp.role_usuario.model.dto.RoleUsuarioDTO;
import br.com.unificaerp.role_usuario.service.RoleUsuarioService;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/roleUsuario")
public class RoleUsuarioController {

    private final RoleUsuarioService roleUsuarioService;

    private final UsuarioLogadoService usuarioLogadoService;

    public RoleUsuarioController(RoleUsuarioService roleUsuarioService, UsuarioLogadoService usuarioLogadoService) {
        this.roleUsuarioService = roleUsuarioService;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<RoleUsuarioDTO> salvar(@RequestBody @Valid RoleUsuario roleUsuario) {
        return ResponseEntity.ok(roleUsuarioService.salvarDto(roleUsuario));
    }


    @PostMapping("/atualizar")
    public ResponseEntity<RoleUsuarioDTO> atualizar(@RequestBody @Valid RoleUsuario roleUsuario) {
        return ResponseEntity.ok(roleUsuarioService.atualizar(roleUsuario));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RoleUsuarioDTO>> listar() {
        return ResponseEntity.ok(roleUsuarioService.listar());
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<RoleUsuarioDTO> buscarPorId(@PathVariable Long id) {
        return roleUsuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/listarPorUsuario/{idUsuario}")
    public ResponseEntity<List<RoleUsuarioDTO>> findAllByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(roleUsuarioService.findAllByUsuario(idUsuario,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/listarPorRole/{idRole}")
    public ResponseEntity<List<RoleUsuarioDTO>> findAllByRoleAndEmpresa(@PathVariable Long idRole) {
        return ResponseEntity.ok(roleUsuarioService.findAllByRoleAndEmpresa(idRole,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @GetMapping("/existePorUsuarioERole/{idUsuario}/{idRole}")
    public ResponseEntity<Boolean> existePorUsuarioERole(
            @PathVariable Long idUsuario, @PathVariable Long idRole) {
        return ResponseEntity.ok(roleUsuarioService.existePorUsuarioERole(idUsuario, idRole,
                usuarioLogadoService.getEmpresaIdLogada()));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        roleUsuarioService.deleteById(id);
        return ResponseEntity.ok("Role do usuário deletado com sucesso.");
    }

    @DeleteMapping("/deletarPorUsuarioERole/{idUsuario}/{idRole}")
    public ResponseEntity<String> deleteByUsuarioAndRole(
            @PathVariable Long idUsuario, @PathVariable Long idRole) {
        roleUsuarioService.deleteByUsuarioAndRole(idUsuario, idRole,
                usuarioLogadoService.getEmpresaIdLogada());
        return ResponseEntity.ok("Role do usuário deletado com sucesso.");
    }
}