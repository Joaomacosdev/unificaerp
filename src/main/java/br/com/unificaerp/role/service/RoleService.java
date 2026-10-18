package br.com.unificaerp.role.service;

import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.role.repository.RoleRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final UsuarioLogadoService usuarioLogadoService;


    public RoleService(RoleRepository roleRepository, UsuarioLogadoService usuarioLogadoService) {
        this.roleRepository = roleRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }





    public Role salvar(Role role) {
        if (!role.getAcesso().startsWith("ROLE_")) {
            throw new MsgApiException("Nomenclatura de acesso não contém ROLE_ no incio");
        }

        if (roleRepository.existePorAcesso(role.getAcesso())) {
            throw new MsgApiException("Já existe acesso com a mesma descrição: " + role.getAcesso());
        }

        return roleRepository.save(role);
    }


    public Role atualizar(Role role) {

        if (role.getId() == null) {
            throw new MsgApiException("Id do acesso deve ser informado para edição.");
        }


        if (!role.getAcesso().startsWith("ROLE_")) {
            throw new MsgApiException("Nomenclatura de acesso não contém ROLE_ no incio");
        }

        if (roleRepository.existePorAcessoDiferenteId(role.getId(), role.getAcesso())) {
            throw new MsgApiException("Já exite outro acesso com a mesma descrição: " + role.getAcesso());
        }


        return roleRepository.save(role);

    }

    public List<Role> listar() {
        return roleRepository.listar(usuarioLogadoService.getEmpresaIdLogada());
    }

    public Optional<Role> buscarPorId(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> buscaPorAcesso(String acesso) {
        return roleRepository.buscaPorAcesso(acesso);
    }

    public boolean existePorAcesso(String acesso) {
        return roleRepository.existePorAcesso(acesso);
    }

    public boolean existePorAcessoDiferenteId(Long id, String acesso) {
        return roleRepository.existePorAcessoDiferenteId(id, acesso);
    }

    public void deleteById(Long id) {

        if(!roleRepository.existePorId(id)) {
            throw new MsgApiException("Acesso com id: " + id + " já foi removido do sistema.");
        }

        roleRepository.deleteById(id);
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