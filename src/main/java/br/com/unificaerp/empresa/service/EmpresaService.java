package br.com.unificaerp.empresa.service;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.empresa.repository.EmpresaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }


    @PersistenceContext
    private EntityManager entityManager;


    public Empresa buscaPorId(Long id) {
        return empresaRepository.buscarPorId(id);
    }

    public List<Empresa> findAll() {

        return empresaRepository.findAll();
    }

    public List<Empresa> buscaPorNome(String nome) {
        return empresaRepository.buscaPorNome(nome);
    }

    public boolean existePorNome(String nome) {
        return empresaRepository.existePorNome(nome);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome) {
        return empresaRepository.existePorNomeDiferenteId(id, nome);
    }

    public void deleteById(Long id) {
        empresaRepository.deleteById(id);

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
