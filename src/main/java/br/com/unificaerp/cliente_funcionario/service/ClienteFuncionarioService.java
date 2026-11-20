package br.com.unificaerp.cliente_funcionario.service;

import br.com.unificaerp.cliente_funcionario.dto.ClienteFuncionarioDTO;
import br.com.unificaerp.cliente_funcionario.model.ClienteFuncionario;
import br.com.unificaerp.cliente_funcionario.repository.ClienteFuncionarioRepository;
import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.pessoa.model.Pessoa;
import br.com.unificaerp.pessoa.repository.PessoaRepository;
import br.com.unificaerp.usuario.model.Usuario;
import br.com.unificaerp.usuario.repository.UsuarioRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class ClienteFuncionarioService {

    private final ClienteFuncionarioRepository clienteFuncionarioRepository;

    private final PessoaRepository pessoaRepository;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioLogadoService usuarioLogadoService;

    @PersistenceContext
    private EntityManager entityManager;

    public ClienteFuncionarioService(ClienteFuncionarioRepository clienteFuncionarioRepository, PessoaRepository pessoaRepository, UsuarioRepository usuarioRepository, UsuarioLogadoService usuarioLogadoService) {
        this.clienteFuncionarioRepository = clienteFuncionarioRepository;
        this.pessoaRepository = pessoaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }




    @Transactional
    public ClienteFuncionarioDTO salvar(ClienteFuncionario clienteFuncionario ) {

        Long empresaId = usuarioLogadoService.getEmpresaIdLogada();

        /*A pessoa precisa ser da empresa logada*/
        Pessoa pessoa = validarPessoa(clienteFuncionario.getPessoa(), empresaId);

        if (clienteFuncionarioRepository.existeParaPessoa(pessoa.getId(), empresaId)) {
            throw new MsgApiException("Já existe um cliente/funcionário cadastrado para a pessoa informada.");
        }

        /*O usuário é opcional no cadastro, mas se vier precisa ser da empresa logada*/
        Usuario usuario = clienteFuncionario.getUsuario() == null || clienteFuncionario.getUsuario().getId() == null
                ? null : validarUsuario(clienteFuncionario.getUsuario(), empresaId);

        clienteFuncionario.setPessoa(pessoa);
        clienteFuncionario.setUsuario(usuario);
        clienteFuncionario.setEmpresa(usuarioLogadoService.getEmpresaLogada());
        clienteFuncionario = clienteFuncionarioRepository.saveAndFlush(clienteFuncionario);

        return converterParaDto(clienteFuncionario);
    }


    /**
     * Devolve a entidade: é usado pelo UsuarioService ao vincular o usuário criado.
     */
    @Transactional
    public ClienteFuncionario salvarClienteFunUser(ClienteFuncionario clienteFuncionario ) {
        return clienteFuncionarioRepository.saveAndFlush(clienteFuncionario);
    }

    private Pessoa validarPessoa(Pessoa pessoa, Long empresaId) {
        if (pessoa == null || pessoa.getId() == null) {
            throw new MsgApiException("Pessoa deve ser informada para o cadastro.");
        }

        return pessoaRepository.buscarPorId(pessoa.getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Pessoa não encontrada para a empresa logada."));
    }

    private Usuario validarUsuario(Usuario usuario, Long empresaId) {
        return usuarioRepository.buscarPorId(usuario.getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Usuário não encontrado para a empresa logada."));
    }

    @Transactional
    public ClienteFuncionarioDTO atualizar(ClienteFuncionario clienteFuncionario) {
        if (clienteFuncionario.getId() == null) {
            throw new MsgApiException("Id do cliente/funcionário não informado para atualizar.");
        }

        Long empresaId = usuarioLogadoService.getEmpresaIdLogada();
        if (!clienteFuncionarioRepository.buscarPorId(clienteFuncionario.getId(), empresaId).isPresent()) {
            throw new MsgApiException("Cliente/funcionário não encontrado para a empresa logada.");
        }

        if (clienteFuncionario.getPessoa() == null || clienteFuncionario.getPessoa().getId() == null) {
            throw new MsgApiException("Pessoa deve ser informada para atualizar o cadastro.");
        }

        Pessoa pessoa = pessoaRepository.buscarPorId(clienteFuncionario.getPessoa().getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Pessoa não encontrada para a empresa logada."));

        if (clienteFuncionario.getUsuario() == null || clienteFuncionario.getUsuario().getId() == null) {
            throw new MsgApiException("Usuário deve ser informado para atualizar o cadastro.");
        }

        Usuario usuario = usuarioRepository.buscarPorId(clienteFuncionario.getUsuario().getId(), empresaId)
                .orElseThrow(() -> new MsgApiException("Usuário não encontrado para a empresa logada."));

        clienteFuncionario.setEmpresa(usuarioLogadoService.getEmpresaLogada());
        clienteFuncionario.setPessoa(pessoa);
        clienteFuncionario.setUsuario(usuario);

        clienteFuncionario = clienteFuncionarioRepository.saveAndFlush(clienteFuncionario);

        return converterParaDto(clienteFuncionario);
    }

    public List<ClienteFuncionario> findAll(Long idEmpresa) {

        return clienteFuncionarioRepository.findAll(idEmpresa);
    }

    public List<ClienteFuncionarioDTO> buscaPorNome(String nome, Long idEmpresa) {
        return converterListaParaDto(clienteFuncionarioRepository.buscaPorNome(nome, idEmpresa));
    }

    public boolean existePorNome(String nome, Long idEmpresa) {
        return clienteFuncionarioRepository.existePorNome(nome, idEmpresa);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
        return clienteFuncionarioRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
    }

    @Transactional
    public void deleteById(Long id, Long idEmpresa) {
        if (!clienteFuncionarioRepository.existsById(id, idEmpresa)) {
            throw new MsgApiException("Cliente/funcionário não encontrado ou já foi deletado.");
        }

        clienteFuncionarioRepository.deleteById(id, idEmpresa);
    }

    /*Exclusão em lote apaga tudo da empresa: só ROLE_ADMIN (P21)*/
    private void exigirAdminParaExclusaoEmLote() {
        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem excluir registros em lote.", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public long deleteAll(Long empresaID) {
        exigirAdminParaExclusaoEmLote();

        if (clienteFuncionarioRepository.total(empresaID) == 0) {
            throw new MsgApiException("Nenhum cliente/funcionário encontrado para deletar ou todos já foram deletados.");
        }

        return clienteFuncionarioRepository.deleteAll(empresaID);
    }

    @Transactional
    public void deletarAllById(Iterable<Long> ids, Long empresaId) {
        exigirAdminParaExclusaoEmLote();

        List<Long> encontrados = clienteFuncionarioRepository.buscarPorIds(ids, empresaId).stream().map(ClienteFuncionario::getId).toList();
        List<Long> naoEncontrados = new ArrayList<>();
        ids.forEach(id -> {
            if (!encontrados.contains(id)) {
                naoEncontrados.add(id);
            }
        });

        if (encontrados.isEmpty() && naoEncontrados.isEmpty()) {
            throw new MsgApiException("Nenhum registro informado para deletar.");
        }

        if (!naoEncontrados.isEmpty()) {
            throw new MsgApiException("Clientes/funcionários não encontrados ou já deletados: " + naoEncontrados);
        }

        clienteFuncionarioRepository.deletarAllById(ids, empresaId);
    }

    public List<ClienteFuncionarioDTO> buscarPorIds(Iterable<Long> ids, Long empresaId) {
        return converterListaParaDto(clienteFuncionarioRepository.buscarPorIds(ids, empresaId));
    }

    public boolean existsById(Long id, Long empresaId) {
        return clienteFuncionarioRepository.existsById(id, empresaId);
    }

    public List<ClienteFuncionarioDTO> listar(Long empresaId) {
        return converterListaParaDto(clienteFuncionarioRepository.listar(empresaId));
    }

    public Optional<ClienteFuncionarioDTO> buscarPorId(Long id, Long empresaId) {
        return clienteFuncionarioRepository.buscarPorId(id, empresaId).map(this::converterParaDto);
    }

    public long total(Long empresaId) {
        return clienteFuncionarioRepository.total(empresaId);
    }

    public Page<ClienteFuncionarioDTO> listarPaginado(Long empresaId, Pageable pageable) {
        return clienteFuncionarioRepository.listarPaginado(empresaId, pageable).map(this::converterParaDto);
    }


    /**
     * Devolve a entidade: é usado pelo UsuarioService ao cadastrar e atualizar o usuário.
     * Para o controller, use findByPessoaDto.
     */
    public ClienteFuncionario findByPessoa(Long idPessoa, Long idEmpresa) {
        return clienteFuncionarioRepository.findByPessoa(idPessoa, idEmpresa);
    }


    public ClienteFuncionarioDTO findByPessoaDto(Long idPessoa, Long idEmpresa) {
        ClienteFuncionario clienteFuncionario = clienteFuncionarioRepository.findByPessoa(idPessoa, idEmpresa);
        return clienteFuncionario != null ? converterParaDto(clienteFuncionario) : null;
    }


    @Transactional
    public void removeUserClienteFuncionarioId(Long id, Long idEmpresa) {
        clienteFuncionarioRepository.removeUserClienteFuncionarioId(id, idEmpresa);
    }

    // ====================Conversão para DTO====================

    private ClienteFuncionarioDTO converterParaDto(ClienteFuncionario clienteFuncionario) {
        ClienteFuncionarioDTO dto = new ClienteFuncionarioDTO();
        dto.setId(clienteFuncionario.getId());
        dto.setTipoClienteFuncionario(clienteFuncionario.getTipoClienteFuncionario());
        dto.setUsuarioId(clienteFuncionario.getUsuario() != null ? clienteFuncionario.getUsuario().getId() : null);
        dto.setPessoaId(clienteFuncionario.getPessoa().getId());
        dto.setNomePessoa(clienteFuncionario.getPessoa().getNome());
        dto.setEmpresaId(clienteFuncionario.getEmpresa().getId());
        return dto;
    }

    private List<ClienteFuncionarioDTO> converterListaParaDto(List<ClienteFuncionario> clientesFuncionarios) {
        return clientesFuncionarios.stream().map(this::converterParaDto).toList();
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
