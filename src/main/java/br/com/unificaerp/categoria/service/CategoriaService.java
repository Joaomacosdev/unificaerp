package br.com.unificaerp.categoria.service;

import br.com.unificaerp.categoria.model.Categoria;
import br.com.unificaerp.categoria.repository.CategoriaRepository;
import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private final UsuarioLogadoService usuarioLogadoService;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioLogadoService usuarioLogadoService) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    public Categoria salvar(Categoria categoria) {

        if (this.existePorNome(categoria.getNome(), usuarioLogadoService.getEmpresaIdLogada())) {
            throw new MsgApiException("Já existe uma categoria com o mesmo nome para a empresa logada.");
        }

        categoria.setEmpresa(usuarioLogadoService.getEmpresaLogada());

        return categoriaRepository.save(categoria);
    }


    public Categoria atualizar(Categoria categoria) {

        if(categoria.getId() == null) {
            throw new MsgApiException("Id da categoria não informado para atualizar.");
        }

        if (this.existePorNomeDiferenteId(categoria.getId(),  categoria.getNome(), usuarioLogadoService.getEmpresaIdLogada())) {
            throw new MsgApiException("Já existe uma categoria com o mesmo nome desta que sendo informada para atualizar, informe outro nome.");
        }

        categoria.setEmpresa(usuarioLogadoService.getEmpresaLogada());

        return categoriaRepository.save(categoria);
    }


    /* Os métodos do service serão chamador pelo Controller */
    public List<Categoria> findAll(Long idEmpresa) {
        return categoriaRepository.findAll(idEmpresa);
    }

    public List<Categoria> buscaPorNome(String nome, Long idEmpresa) {
        return categoriaRepository.buscaPorNome(nome, idEmpresa);
    }

    public boolean existePorNome(String nome, Long idEmpresa) {

        return categoriaRepository.existePorNome(nome, idEmpresa);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
        return categoriaRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
    }

    public void deleteById(Long id, Long idEmpresa) {

        if(!categoriaRepository.existsById(id, idEmpresa)) {
            throw new MsgApiException("Categoria não encontrada para a empresa logada, portanto não pode ser deletada.");
        }

        categoriaRepository.deleteById(id, idEmpresa);
    }

    public Categoria buscarPorId(Long id, Long idEmpresa) {
        return categoriaRepository.buscarPorId(id, idEmpresa).orElseThrow(() ->
                new MsgApiException("Categoria não encontrada para a empresa logada."));
    }

    public long total(Long idEmpresa) {
        return categoriaRepository.total(idEmpresa);
    }

    public boolean existsById(Long id, Long idEmpresa) {
        return categoriaRepository.existsById(id, idEmpresa);
    }

    public Page<Categoria> listarPaginado(Long idEmpresa, Pageable pageable) {
        return categoriaRepository.listarPaginado(idEmpresa, pageable);
    }

    public List<Categoria> listarPaginadoDto(Long idEmpresa, Pageable pageable) {

        Page<Categoria> categorias = categoriaRepository.listarPaginado(idEmpresa, pageable);
        List<Categoria> listaRetorno = new java.util.ArrayList<>();

        for (Categoria categoria : categorias) {
            listaRetorno.add(categoria);
        }

        return listaRetorno;
    }
}
