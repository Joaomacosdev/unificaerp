package br.com.unificaerp.usuario.service;

import br.com.unificaerp.usuario.model.RoleUsuario;
import br.com.unificaerp.usuario.repository.RoleUsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleUsuarioService {

    private final RoleUsuarioRepository roleUsuarioRepository;

    @PersistenceContext
    private  EntityManager entityManager;

    public RoleUsuarioService(RoleUsuarioRepository roleUsuarioRepository) {
        this.roleUsuarioRepository = roleUsuarioRepository;
    }

    public RoleUsuario salvar(RoleUsuario roleUsuario ) {
        return roleUsuarioRepository.saveAndFlush(roleUsuario);
    }


    // ====================Métodos específicos para Usuário====================

    public List<RoleUsuario> findAllByUsuario(Long idUsuario, Long idEmpresa) {
        return roleUsuarioRepository.findAllByUsuario(idUsuario, idEmpresa);
    }

    // ====================Métodos específicos para Role====================

    public List<RoleUsuario> findAllByRoleAndEmpresa(Long idRole, Long idEmpresa) {
        return roleUsuarioRepository.findAllByRoleAndEmpresa(idRole, idEmpresa);
    }

    // ====================Métodos de validação====================

    public boolean existePorUsuarioERole(Long idUsuario, Long idRole, Long idEmpresa) {
        return roleUsuarioRepository.existePorUsuarioERole(idUsuario, idRole, idEmpresa);
    }

    // ====================Métodos de deleção====================

    public void deleteById(Long id) {
        roleUsuarioRepository.deleteById(id);
    }

    public void deleteByUsuarioAndRole(Long idUsuario, Long idRole, Long idEmpresa) {
        roleUsuarioRepository.deleteByUsuarioAndRole(idUsuario, idRole, idEmpresa);
    }

    // ====================dentro dos métodos do
    // service===============================

    // Verificar se está em uso
    // Realizar um consulta com integração para saber se pode deletar
    // Fazer copia e backup
    // Fazer inumeras validações de regra de negocio
    // Fazer validações
    // Lançar exeções
    // Escrever regras de negócio
}
