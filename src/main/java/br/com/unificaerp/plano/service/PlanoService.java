package br.com.unificaerp.plano.service;

import br.com.unificaerp.plano.model.Plano;
import br.com.unificaerp.plano.repository.PlanoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    /*Posso escrever query customizadas e dinâmicas, mais complexas do que no Repository*/
    @PersistenceContext
    private EntityManager entityManager;



    public List<Plano> findAll() {

        return planoRepository.findAll();
    }

    public List<Plano> buscaPorNome(String nome) {
        return planoRepository.buscaPorNome(nome);
    }

    public boolean existePorNome(String nome) {
        return planoRepository.existePorNome(nome);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome) {
        return planoRepository.existePorNomeDiferenteId(id, nome);
    }

    public void deleteById(Long id) {
        planoRepository.deleteById(id);
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
