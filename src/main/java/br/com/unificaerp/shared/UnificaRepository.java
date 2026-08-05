package br.com.unificaerp.shared;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UnificaRepository<T, ID> extends JpaRepository<T, ID> {
    Page<T> listarPaginado(Long empresaId, Pageable pageable);

    long total(Long empresaId);

    Optional<T> buscarPorId(ID id, Long empresaId);

    List<T> listar(Long empresaId);

    boolean existsById(ID id, Long empresaId);

    List<T> buscarPorIds(Iterable<ID> ids, Long empresaId);

    void deletarAllById(Iterable<ID> ids, Long empresaId);

    long deleteAll(Long empresaID);

}
