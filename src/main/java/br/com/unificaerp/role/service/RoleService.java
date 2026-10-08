package br.com.unificaerp.role.service;

import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.role.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;



    public List<Role> findAll() {

        return roleRepository.findAll();
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