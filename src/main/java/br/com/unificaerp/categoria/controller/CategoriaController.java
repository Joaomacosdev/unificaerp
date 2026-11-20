package br.com.unificaerp.categoria.controller;

import br.com.unificaerp.categoria.model.Categoria;
import br.com.unificaerp.categoria.model.dto.CategoriaDTO;
import br.com.unificaerp.categoria.service.CategoriaService;
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
@RequestMapping("/api/categoria")
public class CategoriaController {



    private final CategoriaService categoriaService;

    private final UsuarioLogadoService usuarioLogadoService;

    public CategoriaController(CategoriaService categoriaService, UsuarioLogadoService usuarioLogadoService) {
        this.categoriaService = categoriaService;
        this.usuarioLogadoService = usuarioLogadoService;
    }


    @PostMapping("/salvar")
    public ResponseEntity<CategoriaDTO> salvar(@RequestBody @Valid Categoria categoria){

        CategoriaDTO categoriaSalva = categoriaService.salvar(categoria);

        return ResponseEntity.ok(categoriaSalva);
    }


    @PostMapping("/atualizar")
    public ResponseEntity<CategoriaDTO> atualizar(@RequestBody @Valid Categoria categoria){

        CategoriaDTO categoriaAtualizada = categoriaService.atualizar(categoria);

        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletePorId(@PathVariable(required = true, value = "id") Long idCategoria){

        categoriaService.deleteById(idCategoria, usuarioLogadoService.getEmpresaIdLogada());

        return ResponseEntity.ok("Categoria deletada com sucesso.");
    }


    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaDTO>> listar(){

        return ResponseEntity.ok(categoriaService.findAll(usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<CategoriaDTO>> buscaPorNome(@PathVariable String nome){

        return ResponseEntity.ok(categoriaService.buscaPorNome(nome, usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id){

        return ResponseEntity.ok(categoriaService.buscarPorId(id, usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/existePorNome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome){

        return ResponseEntity.ok(categoriaService.existePorNome(nome, usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/existePorNomeDiferenteId/{id}/{nome}")
    public ResponseEntity<Boolean> existePorNomeDiferenteId(@PathVariable Long id, @PathVariable String nome){

        return ResponseEntity.ok(categoriaService.existePorNomeDiferenteId(id, nome, usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/existePorId/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id){

        return ResponseEntity.ok(categoriaService.existsById(id, usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/total")
    public ResponseEntity<Long> total(){

        return ResponseEntity.ok(categoriaService.total(usuarioLogadoService.getEmpresaIdLogada()));
    }


    @GetMapping("/paginado")
    public ResponseEntity<Page<CategoriaDTO>> listarPaginado(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "id") String sort,
                                                             @RequestParam(defaultValue = "asc") String direction){

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return ResponseEntity.ok(categoriaService.listarPaginado(usuarioLogadoService.getEmpresaIdLogada(), pageable));
    }

}
