package br.com.unificaerp.role.repository;

import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.shared.IgnoreEmpresaId;
import br.com.unificaerp.shared.UnificaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@IgnoreEmpresaId
@Repository
public interface RoleRepository extends UnificaRepository<Role, Long> {

    /*
     * Busca todos os roles
     */
    @Query("select r from Role r")
    List<Role> findAll();


    /*Busca os roles por partes ou acesso completo passado por parametro*/
    @Query("select r from Role r "
            + " where unaccent(upper(trim(r.acesso))) "
            + " like unaccent(upper(concat('%', trim(:acesso) ,'%')))")
    List<Role> buscaPorAcesso(@Param("acesso") String acesso);


    /*Retorna true se já existir role com o mesmo acesso, no caso não podemos deixar salvar para não ficar repetido no banco de dados*/
    @Query("select count(r.id) > 0 from Role r "
            + " where unaccent(upper(trim(r.acesso))) "
            + " = unaccent(upper(trim(:acesso)))")
    boolean existePorAcesso(@Param("acesso") String acesso);

    /*Verifica se existe outro role no banco de dados com o mesmo acesso mas ID diferentes da que está tentando atualizar*/
    @Query("select count(r.id) > 0 from Role r "
            + " where unaccent(upper(trim(r.acesso))) "
            + " = unaccent(upper(trim(:acesso))) and r.id <> :id")
    boolean existePorAcessoDiferenteId(@Param("id") Long id, @Param("acesso") String acesso);

    /*Delete de um role*/
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Role r where r.id = :id")
    void deleteById(@Param("id") Long id);


    @Query("select count(r.id) > 0 from Role r where r.id = :id ")
    boolean existePorId(@Param("id") Long id);
}
