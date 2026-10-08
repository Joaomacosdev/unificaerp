package br.com.unificaerp.categoria.service;

import br.com.unificaerp.categoria.model.Categoria;
import br.com.unificaerp.categoria.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll(Long idEmpresa) {
        return categoriaRepository.findAll(idEmpresa);
    }

    public List<Categoria> buscaPorNome(String nome,  Long idEmpresa) {
        return categoriaRepository.buscaPorNome(nome, idEmpresa);
    }

    public boolean existePorNome(String nome, Long idEmpresa) {
        return categoriaRepository.existePorNome(nome, idEmpresa);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome,Long idEmpresa) {
        return categoriaRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
    }

    public void deleteById(Long id, Long idEmpresa) {
        categoriaRepository.deleteById(id,  idEmpresa);
    }

    public long deleteAll(Long idEmpresa) {
        return categoriaRepository.deleteAll(idEmpresa);
    }

    public void deletarAllById(Iterable<Long> ids, Long idEmpresa) {
         categoriaRepository.deletarAllById(ids, idEmpresa);
    }

    public List<Categoria> buscarPorIds(Iterable<Long> ids, Long idEmpresa) {
        return categoriaRepository.buscarPorIds(ids, idEmpresa);
    }

    public boolean existePorId(Long id, Long idEmpresa) {
        return categoriaRepository.existsById(id,  idEmpresa);
    }

    public List<Categoria> listar(Long idEmpresa) {
        return categoriaRepository.listar(idEmpresa);
    }

    public Optional<Categoria> buscarPorId(Long id, Long idEmpresa) {
        return categoriaRepository.buscarPorId(id,  idEmpresa);
    }

    public long totalDePorId(Long idEmpresa) {
        return categoriaRepository.total(idEmpresa);
    }

    public Page<Categoria> listarPaginado(Long idEmpresa, Pageable pageable) {
        return categoriaRepository.listarPaginado(idEmpresa, pageable);
    }
}
