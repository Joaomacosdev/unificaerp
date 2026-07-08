package br.com.unificaerp.categoria.repository;

import br.com.unificaerp.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM  Categoria c WHERE c.empresa.id = idEmpresa")
    List<Categoria> findAll(@Param("idEmpresa")  Long idEmpresa);

    @Query("SELECT c FROM Categoria c WHERE c.nome like %:nome%")
    List<Categoria> buscaPorNome(@Param("nome") String nome);
}
