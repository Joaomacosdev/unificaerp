package br.com.unificaerp.role.service;

import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.role.model.Role;
import br.com.unificaerp.role.model.dto.RoleDTO;
import br.com.unificaerp.role.repository.RoleRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final UsuarioLogadoService usuarioLogadoService;

    /** Acessos que o código usa pelo nome: não podem ser renomeados nem apagados. */
    static final Set<String> ACESSOS_DO_SISTEMA = Set.of("ROLE_USER", "ROLE_ADMIN");


    public RoleService(RoleRepository roleRepository, UsuarioLogadoService usuarioLogadoService) {
        this.roleRepository = roleRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }





    /*
     * Acesso é dado global e decide quem é administrador: só ROLE_ADMIN cadastra,
     * altera ou apaga (renomear ROLE_USER para ROLE_ADMIN tornaria todos admin).
     */
    private void exigirAdmin(String operacao) {
        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem " + operacao + " acessos.", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public RoleDTO salvar(Role role) {
        exigirAdmin("cadastrar");

        if (!role.getAcesso().startsWith("ROLE_")) {
            throw new MsgApiException("Nomenclatura de acesso não contém ROLE_ no incio");
        }

        if (roleRepository.existePorAcesso(role.getAcesso())) {
            throw new MsgApiException("Já existe acesso com a mesma descrição: " + role.getAcesso());
        }

        return converterParaDto(roleRepository.saveAndFlush(role));
    }


    @Transactional
    public RoleDTO atualizar(Role role) {

        exigirAdmin("atualizar");

        if (role.getId() == null) {
            throw new MsgApiException("Id do acesso deve ser informado para edição.");
        }

        /*Edita o registro do banco: com um id que não existe, o save criaria outro acesso*/
        Role existente = roleRepository.findById(role.getId())
                .orElseThrow(() -> new MsgApiException("Acesso com id: " + role.getId() + " não foi encontrado."));

        if (!existente.getAcesso().equals(role.getAcesso())) {
            recusarSeForAcessoDoSistema(existente, "renomeado", "Para usar outro nome, cadastre um acesso novo.");
            /*P25: renomear um acesso que o admin logado tem tiraria o acesso dele*/
            recusarSeForAcessoDoUsuarioLogado(existente, "renomear");
        }

        if (!role.getAcesso().startsWith("ROLE_")) {
            throw new MsgApiException("Nomenclatura de acesso não contém ROLE_ no incio");
        }

        if (roleRepository.existePorAcessoDiferenteId(role.getId(), role.getAcesso())) {
            throw new MsgApiException("Já exite outro acesso com a mesma descrição: " + role.getAcesso());
        }

        existente.setAcesso(role.getAcesso());

        return converterParaDto(roleRepository.saveAndFlush(existente));

    }

    /*O cadastro de usuário procura a ROLE_USER pelo nome, e o admin é reconhecido pela ROLE_ADMIN*/
    private void recusarSeForAcessoDoSistema(Role role, String operacao, String instrucao) {
        if (ACESSOS_DO_SISTEMA.contains(role.getAcesso())) {
            throw new MsgApiException("O acesso " + role.getAcesso() + " é do sistema e não pode ser " + operacao + ": "
                    + "o cadastro de usuários e a identificação do administrador dependem desse nome. " + instrucao);
        }
    }

    private void recusarSeForAcessoDoUsuarioLogado(Role role, String operacao) {
        if (usuarioLogadoService.possuiAcesso(role.getId())) {
            throw new MsgApiException("Você não pode " + operacao + " o acesso " + role.getAcesso()
                    + ": ele faz parte do seu usuário, e você perderia o seu próprio acesso. "
                    + "Para usar outro nome, cadastre um acesso novo; para removê-lo, peça a outro administrador que tire esse acesso de você antes.",
                    HttpStatus.FORBIDDEN);
        }
    }

    public List<RoleDTO> listar() {
        return converterListaParaDto(roleRepository.listar(usuarioLogadoService.getEmpresaIdLogada()));
    }

    public Optional<RoleDTO> buscarPorId(Long id) {
        return roleRepository.findById(id).map(this::converterParaDto);
    }

    /**
     * Devolve a entidade: é usado por outros services (ex.: UsuarioService, para
     * associar a ROLE_USER ao novo usuário). Para o controller, use buscaPorAcessoDto.
     */
    public List<Role> buscaPorAcesso(String acesso) {
        return roleRepository.buscaPorAcesso(acesso);
    }

    public List<RoleDTO> buscaPorAcessoDto(String acesso) {
        return converterListaParaDto(roleRepository.buscaPorAcesso(acesso));
    }

    public boolean existePorAcesso(String acesso) {
        return roleRepository.existePorAcesso(acesso);
    }

    public boolean existePorAcessoDiferenteId(Long id, String acesso) {
        return roleRepository.existePorAcessoDiferenteId(id, acesso);
    }

    @Transactional
    public void deleteById(Long id) {

        exigirAdmin("deletar");

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new MsgApiException("Acesso com id: " + id + " já foi removido do sistema."));

        recusarSeForAcessoDoSistema(role, "apagado", "Para tirá-lo de um usuário, remova a associação em Acessos do usuário.");
        recusarSeForAcessoDoUsuarioLogado(role, "apagar");

        roleRepository.deleteById(id);
    }

    // ====================Conversão para DTO====================

    private RoleDTO converterParaDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setAcesso(role.getAcesso());
        return dto;
    }

    private List<RoleDTO> converterListaParaDto(List<Role> roles) {
        return roles.stream().map(this::converterParaDto).toList();
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