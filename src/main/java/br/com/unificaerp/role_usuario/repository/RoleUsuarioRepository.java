package br.com.unificaerp.role_usuario.repository;

import br.com.unificaerp.shared.IgnoreEmpresaId;
import br.com.unificaerp.shared.UnificaRepository;
import br.com.unificaerp.role_usuario.model.RoleUsuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@IgnoreEmpresaId
@Repository
public interface RoleUsuarioRepository extends UnificaRepository<RoleUsuario, Long> {

    /*
     * Busca todos os roles do usuário passado como parametro da empresa
     */
    @Query("select r from RoleUsuario r where r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa")
    List<RoleUsuario> findAllByUsuario(@Param("idUsuario") Long idUsuario, @Param("idEmpresa") Long idEmpresa);


    /*Busca todos os usuários da empresa que possuem um determinado role*/
    @Query("select r from RoleUsuario r where r.acesso.id = :idRole and r.usuario.empresa.id = :idEmpresa")
    List<RoleUsuario> findAllByRoleAndEmpresa(@Param("idRole") Long idRole, @Param("idEmpresa") Long idEmpresa);


    /*Busca todas as associações de role com usuário da empresa (RoleUsuario não possui empresa, o filtro é pelo usuário)*/
    @Query("select r from RoleUsuario r where r.usuario.empresa.id = :idEmpresa")
    List<RoleUsuario> findAllByEmpresa(@Param("idEmpresa") Long idEmpresa);


    /*Busca uma associação de role com usuário pelo id, somente se o usuário pertencer à empresa*/
    @Query("select r from RoleUsuario r where r.id = :id and r.usuario.empresa.id = :idEmpresa")
    Optional<RoleUsuario> buscarPorIdEEmpresa(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);


    /*Retorna true se o usuário da empresa possui um determinado role*/
    @Query("select count(r.id) > 0 from RoleUsuario r where r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa and r.acesso.id = :idRole")
    public boolean existePorUsuarioERole(@Param("idUsuario") Long idUsuario, @Param("idRole") Long idRole, @Param("idEmpresa") Long idEmpresa);

    @Query("select count(r.id) > 0 from RoleUsuario r where r.id <> :id and r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa and r.acesso.id = :idRole")
    public boolean existePorUsuarioDiferenreId(@Param("idUsuario") Long idUsuario,
                                               @Param("idRole") Long idRole,
                                               @Param("id") Long id,
                                               @Param("idEmpresa") Long idEmpresa);


    /*Delete de um role do usuário da empresa*/
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from RoleUsuario r where r.id = :id")
    void deleteById(@Param("id") Long id);

    /*Delete de uma associação role-usuario específica da empresa*/
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from RoleUsuario r where r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa and r.acesso.id = :idRole")
    void deleteByUsuarioAndRole(@Param("idUsuario") Long idUsuario, @Param("idRole") Long idRole, @Param("idEmpresa") Long idEmpresa);}
