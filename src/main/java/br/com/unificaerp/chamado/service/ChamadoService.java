package br.com.unificaerp.chamado.service;

import br.com.unificaerp.chamado.model.Chamado;
import br.com.unificaerp.chamado.repository.ChamadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    /*Posso escrever query customizadas e dinâmicas, mais complexas do que no Repository*/
    @PersistenceContext
    private EntityManager entityManager;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }


    /* Os métodos do service serão chamador pelo Controller */
    public List<Chamado> findAll(Long idEmpresa) {
        return chamadoRepository.findAll(idEmpresa);
    }

    List<Chamado> buscaPorTitulo(String titulo, Long idEmpresa) {
        return chamadoRepository.buscaPorTitulo(titulo, idEmpresa);
    }

    boolean existePorTitulo(String titulo, Long idEmpresa) {
        return chamadoRepository.existePorTitulo(titulo, idEmpresa);
    }

    boolean existePorTituloDiferenteId(Long id, String titulo, Long idEmpresa) {
        return chamadoRepository.existePorTituloDiferenteId(id, titulo, idEmpresa);
    }

    void deleteById(Long id, Long idEmpresa) {
        chamadoRepository.deleteById(id, idEmpresa);
    }


}
