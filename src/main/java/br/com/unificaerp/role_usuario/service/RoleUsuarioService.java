package br.com.unificaerp.role_usuario.service;

import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.role.repository.RoleRepository;
import br.com.unificaerp.role_usuario.model.RoleUsuario;
import br.com.unificaerp.role_usuario.model.dto.RoleUsuarioDTO;
import br.com.unificaerp.role_usuario.repository.RoleUsuarioRepository;
import br.com.unificaerp.usuario.model.Usuario;
import br.com.unificaerp.usuario.repository.UsuarioRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import br.com.unificaerp.util.DtoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleUsuarioService {

    private final RoleUsuarioRepository roleUsuarioRepository;

    private final RoleRepository roleRepository;

    private final UsuarioRepository usuarioRepository;



    /*Posso escrever query customizadas e dinâmicas, mais complexas do que no Repository*/
    @PersistenceContext
    private EntityManager entityManager;

    private final UsuarioLogadoService usuarioLogadoService;

    public RoleUsuarioService(RoleUsuarioRepository roleUsuarioRepository, RoleRepository roleRepository, UsuarioRepository usuarioRepository, UsuarioLogadoService usuarioLogadoService) {
        this.roleUsuarioRepository = roleUsuarioRepository;
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }


    /* P25 (2026-11-12): o admin logado não remove nem altera os próprios acessos */
    static final String MSG_PROPRIOS_ACESSOS = "Você não pode remover ou alterar os seus próprios acessos: perderia o acesso "
            + "ao sistema. Se precisar, peça a outro administrador que faça a alteração em Acessos do usuário.";

    /*Dar ou tirar acesso é área de administração: sem isso, qualquer usuário se daria ROLE_ADMIN*/
    private void exigirAdmin(String operacao) {
        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem " + operacao + ".", HttpStatus.FORBIDDEN);
        }
    }

    private void recusarSeForDoUsuarioLogado(RoleUsuario roleUsuario) {
        if (roleUsuario.getUsuario() != null && usuarioLogadoService.isUsuarioLogado(roleUsuario.getUsuario().getId())) {
            throw new MsgApiException(MSG_PROPRIOS_ACESSOS, HttpStatus.FORBIDDEN);
        }
    }


    /**
     * Devolve a entidade: é usado por outros services (ex.: UsuarioService, ao
     * cadastrar o usuário). Para o controller, use salvarDto.
     */
    @Transactional
    public RoleUsuario salvar(RoleUsuario roleUsuario ) {

        exigirAdmin("associar acessos a usuários");

        Long empresaId = usuarioLogadoService.getEmpresaIdLogada();

        Role acesso = validarAcesso(roleUsuario.getAcesso());

        /*Só associa acesso a usuário da empresa logada*/
        Usuario usuario = validarUsuario(roleUsuario.getUsuario(), empresaId);

        if (roleUsuarioRepository.existePorUsuarioERole(usuario.getId(), acesso.getId(), empresaId)) {
            throw new MsgApiException("Já existe o acesso de: " + acesso.getAcesso() + " associado para o mesmo usuário: " + DtoUtil.nomeUsuario(usuario));
        }

        roleUsuario.setAcesso(acesso);
        roleUsuario.setUsuario(usuario);

        return roleUsuarioRepository.saveAndFlush(roleUsuario);
    }


    @Transactional
    public RoleUsuarioDTO salvarDto(RoleUsuario roleUsuario) {
        return converterParaDto(salvar(roleUsuario));
    }


    @Transactional
    public RoleUsuarioDTO atualizar(RoleUsuario roleUsuario ) {

        exigirAdmin("alterar acessos de usuários");

        if(roleUsuario.getId() == null){
            throw new MsgApiException("Deve ser informado o registro para editart o acesso do usuário.");
        }

        Long empresaId = usuarioLogadoService.getEmpresaIdLogada();

        /*Só edita registro de usuário da empresa logada*/
        RoleUsuario existente = roleUsuarioRepository.buscarPorIdEEmpresa(roleUsuario.getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Registro de acesso não encontrado para a empresa logada."));

        /*Trocar o acesso ou o usuário de uma associação do próprio admin tira um acesso dele*/
        recusarSeForDoUsuarioLogado(existente);

        Role acesso = validarAcesso(roleUsuario.getAcesso());
        Usuario usuario = validarUsuario(roleUsuario.getUsuario(), empresaId);

        if (roleUsuarioRepository.existePorUsuarioDiferenreId(usuario.getId(), acesso.getId(), roleUsuario.getId(), empresaId)) {
            throw new MsgApiException("Associação de usuário com acesso é duplicada e não pode ser permitida.");
        }

        existente.setAcesso(acesso);
        existente.setUsuario(usuario);

        return converterParaDto(roleUsuarioRepository.saveAndFlush(existente));

    }

    private Role validarAcesso(Role acesso) {
        if (acesso == null || acesso.getId() == null) {
            throw new MsgApiException("Acesso deve ser informado para associar ao usuário.");
        }

        return roleRepository.findById(acesso.getId())
                .orElseThrow(() -> new MsgApiException("Acesso não encontrado."));
    }

    private Usuario validarUsuario(Usuario usuario, Long empresaId) {
        if (usuario == null || usuario.getId() == null) {
            throw new MsgApiException("Usuário deve ser informado para associar acesso.");
        }

        return usuarioRepository.buscarPorId(usuario.getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Usuário não encontrado para a empresa logada."));
    }

    public List<RoleUsuarioDTO> listar() {
        return converterListaParaDto(roleUsuarioRepository.findAllByEmpresa(usuarioLogadoService.getEmpresaIdLogada()));
    }

    public Optional<RoleUsuarioDTO> buscarPorId(Long id) {
        return roleUsuarioRepository.buscarPorIdEEmpresa(id, usuarioLogadoService.getEmpresaIdLogada()).map(this::converterParaDto);
    }


    // ====================Métodos específicos para Usuário====================

    public List<RoleUsuarioDTO> findAllByUsuario(Long idUsuario, Long idEmpresa) {
        return converterListaParaDto(roleUsuarioRepository.findAllByUsuario(idUsuario, idEmpresa));
    }

    // ====================Métodos específicos para Role====================

    public List<RoleUsuarioDTO> findAllByRoleAndEmpresa(Long idRole, Long idEmpresa) {
        return converterListaParaDto(roleUsuarioRepository.findAllByRoleAndEmpresa(idRole, idEmpresa));
    }

    // ====================Métodos de validação====================

    public boolean existePorUsuarioERole(Long idUsuario, Long idRole, Long idEmpresa) {
        return roleUsuarioRepository.existePorUsuarioERole(idUsuario, idRole, idEmpresa);
    }

    // ====================Métodos de deleção====================

    @Transactional
    public void deleteById(Long id) {

        exigirAdmin("remover acessos de usuários");

        Optional<RoleUsuario> roleUsuario = roleUsuarioRepository.buscarPorIdEEmpresa(id, usuarioLogadoService.getEmpresaIdLogada());

        if (!roleUsuario.isPresent()) {
            throw new MsgApiException("Registro de acesso não encontrado ou já foi deletado.");
        }

        recusarSeForDoUsuarioLogado(roleUsuario.get());

        roleUsuarioRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUsuarioAndRole(Long idUsuario, Long idRole, Long idEmpresa) {
        exigirAdmin("remover acessos de usuários");

        if (usuarioLogadoService.isUsuarioLogado(idUsuario)) {
            throw new MsgApiException(MSG_PROPRIOS_ACESSOS, HttpStatus.FORBIDDEN);
        }

        if (!roleUsuarioRepository.existePorUsuarioERole(idUsuario, idRole, idEmpresa)) {
            throw new MsgApiException("Acesso do usuário não encontrado ou já foi deletado.");
        }

        roleUsuarioRepository.deleteByUsuarioAndRole(idUsuario, idRole, idEmpresa);
    }

    // ====================Conversão para DTO====================

    private RoleUsuarioDTO converterParaDto(RoleUsuario roleUsuario) {
        RoleUsuarioDTO dto = new RoleUsuarioDTO();
        dto.setId(roleUsuario.getId());
        dto.setAcessoId(roleUsuario.getAcesso().getId());
        dto.setAcessoNome(roleUsuario.getAcesso().getAcesso());
        dto.setUsuarioId(roleUsuario.getUsuario().getId());
        dto.setUsuarioNome(DtoUtil.nomeUsuario(roleUsuario.getUsuario()));
        return dto;
    }

    private List<RoleUsuarioDTO> converterListaParaDto(List<RoleUsuario> rolesUsuario) {
        return rolesUsuario.stream().map(this::converterParaDto).toList();
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