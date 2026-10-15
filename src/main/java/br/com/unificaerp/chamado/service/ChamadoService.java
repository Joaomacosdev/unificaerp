package br.com.unificaerp.chamado.service;

import br.com.unificaerp.chamado.model.Chamado;
import br.com.unificaerp.chamado.repository.ChamadoRepository;
import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.usuario.model.Usuario;
import br.com.unificaerp.usuario.repository.UsuarioRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    /*Posso escrever query customizadas e dinâmicas, mais complexas do que no Repository*/
    @PersistenceContext
    private EntityManager entityManager;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioLogadoService usuarioLogadoService;



    public ChamadoService(ChamadoRepository chamadoRepository, UsuarioRepository usuarioRepository, UsuarioLogadoService usuarioLogadoService) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }


    public Chamado salvar(Chamado chamado) {
        Long empresaId = usuarioLogadoService.getEmpresaIdLogada();
        if (chamadoRepository.existePorTitulo(chamado.getTitulo(), empresaId)) {
            throw new MsgApiException("Já existe um chamado com o mesmo título para a empresa logada.");
        }

        chamado.setEmpresa(usuarioLogadoService.getEmpresaLogada());
        chamado.setAbertoUser(usuarioLogadoService.getUsuarioLogado());
        chamado.setDataAbertura(LocalDate.now());
        chamado.setAtendente(validarUsuario(chamado.getAtendente(), empresaId));
        chamado.setCliente(validarUsuario(chamado.getCliente(), empresaId));
        chamado.setFechadoUser(null);
        chamado.setDataFechamento(null);
        return chamadoRepository.saveAndFlush(chamado);
    }

    public Chamado atualizar(Chamado chamado) {
        if (chamado.getId() == null) {
            throw new MsgApiException("Id do chamado não informado para atualizar.");
        }

        Long empresaId = usuarioLogadoService.getEmpresaIdLogada();
        Chamado existente = chamadoRepository.buscarPorId(chamado.getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Chamado não encontrado para a empresa logada."));

        if (chamadoRepository.existePorTituloDiferenteId(chamado.getId(), chamado.getTitulo(), empresaId)) {
            throw new MsgApiException("Já existe outro chamado com o mesmo título para a empresa logada.");
        }

        existente.setTitulo(chamado.getTitulo());
        existente.setDescricao(chamado.getDescricao());
        existente.setTipoChamado(chamado.getTipoChamado());
        existente.setPrioridadeChamado(chamado.getPrioridadeChamado());
        existente.setStatusChamado(chamado.getStatusChamado());
        existente.setDataFechamento(chamado.getDataFechamento());
        existente.setFechadoUser(chamado.getFechadoUser() == null ? null
                : validarUsuario(chamado.getFechadoUser(), empresaId));
        existente.setAtendente(validarUsuario(chamado.getAtendente(), empresaId));
        existente.setCliente(validarUsuario(chamado.getCliente(), empresaId));
        return chamadoRepository.saveAndFlush(existente);
    }

    private Usuario validarUsuario(Usuario usuario, Long empresaId) {
        if (usuario == null || usuario.getId() == null) {
            throw new MsgApiException("Cliente e atendente devem ser informados para o chamado.");
        }

        return usuarioRepository.buscarPorId(usuario.getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Usuário relacionado não encontrado para a empresa logada."));
    }

    public Optional<Chamado> buscarPorId(Long id, Long idEmpresa) {
        return chamadoRepository.buscarPorId(id, idEmpresa);
    }

    /* Os métodos do service serão chamador pelo Controller */
    public List<Chamado> findAll(Long idEmpresa) {
        return chamadoRepository.findAll(idEmpresa);
    }

    public List<Chamado> buscaPorTitulo(String titulo, Long idEmpresa) {
        return chamadoRepository.buscaPorTitulo(titulo, idEmpresa);
    }

    public boolean existePorTitulo(String titulo, Long idEmpresa) {
        return chamadoRepository.existePorTitulo(titulo, idEmpresa);
    }

    public boolean existePorTituloDiferenteId(Long id, String titulo, Long idEmpresa) {
        return chamadoRepository.existePorTituloDiferenteId(id, titulo, idEmpresa);
    }

    public void deleteById(Long id, Long idEmpresa) {
        if (!chamadoRepository.existsById(id, idEmpresa)) {
            throw new MsgApiException("Chamado não encontrado ou já foi deletado.");
        }

        chamadoRepository.deleteById(id, idEmpresa);
    }


}
