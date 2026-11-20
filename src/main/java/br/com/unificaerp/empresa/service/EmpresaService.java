package br.com.unificaerp.empresa.service;

import br.com.unificaerp.empresa.dto.EmpresaDTO;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.empresa.repository.EmpresaRepository;
import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.pessoa.model.Pessoa;
import br.com.unificaerp.pessoa.service.PessoaService;
import br.com.unificaerp.plano.model.Plano;
import br.com.unificaerp.plano.repository.PlanoRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final PlanoRepository planoRepository;

    private final PessoaService pessoaService;


    private final UsuarioLogadoService usuarioLogadoService;

    static final String MSG_APAGAR_PROPRIA_EMPRESA = "Você não pode apagar a sua própria empresa: você e os usuários dela "
            + "perderiam o acesso ao sistema. Se ela precisar mesmo ser apagada, peça a um administrador de outra empresa.";

    static final String MSG_BLOQUEAR_PROPRIA_EMPRESA = "Você não pode bloquear a sua própria empresa: você e os usuários dela "
            + "perderiam o acesso ao sistema. Se ela precisar mesmo ser bloqueada, peça a um administrador de outra empresa.";

    /*Empresa é dado global (cadastro dos clientes do SaaS): só ROLE_ADMIN cadastra, altera, apaga ou lista*/
    private void exigirAdmin(String operacao) {
        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem " + operacao + " empresas.", HttpStatus.FORBIDDEN);
        }
    }

    public EmpresaService(EmpresaRepository empresaRepository, PlanoRepository planoRepository, PessoaService pessoaService, UsuarioLogadoService usuarioLogadoService) {
        this.empresaRepository = empresaRepository;
        this.planoRepository = planoRepository;
        this.pessoaService = pessoaService;
        this.usuarioLogadoService = usuarioLogadoService;
    }



    @Transactional
    public EmpresaDTO salvar(Empresa empresa) {

        /*Permissão antes de tudo: usuário comum não pode nem descobrir se a pessoa já tem empresa*/
        exigirAdmin("cadastrar");

        if (empresa.getPessoa() == null || empresa.getPessoa().getId() == null) {
            throw new MsgApiException("Pessoa deve ser informada para cadastrar a empresa.");
        }

        if (empresaRepository.existePorPessoa(empresa.getPessoa().getId())) {
            /*O nome só aparece se a pessoa for da empresa logada; de outra empresa, a mensagem fica sem nome*/
            String nome = pessoaService.buscarPorId(empresa.getPessoa().getId(), usuarioLogadoService.getEmpresaIdLogada())
                    .map(Pessoa::getNome)
                    .orElse("informada");
            throw new MsgApiException("Já existe uma empresa cadastrada para a pessoa: " + nome);
        }

        /*bloqueio nulo vira false (decidido em 2026-11-12)*/
        empresa.setBloqueio(Boolean.TRUE.equals(empresa.getBloqueio()));
        empresa.setPlano(validarPlano(empresa.getPlano()));
        /*Referência gerenciada no lugar do objeto do request (só com id), para o DTO ler o nome da pessoa*/
        empresa.setPessoa(entityManager.getReference(Pessoa.class, empresa.getPessoa().getId()));

        return converterParaDto(empresaRepository.saveAndFlush(empresa));
    }

    @Transactional
    public EmpresaDTO atualizar(Empresa empresa) {

        exigirAdmin("atualizar");

        if (empresa.getId() == null) {
            throw new MsgApiException("Id da empresa deve ser informado para edição.");
        }

        Empresa existente = empresaRepository.buscarPorId(empresa.getId());

        if (existente == null) {
            throw new MsgApiException("Empresa com id: " + empresa.getId() + " não foi encontrada.");
        }

        /*bloqueio nulo vira false (decidido em 2026-11-12)*/
        empresa.setBloqueio(Boolean.TRUE.equals(empresa.getBloqueio()));

        /*P25: bloquear a própria empresa tiraria o acesso do admin logado e de todos os usuários dela*/
        if (empresa.getBloqueio() && empresa.getId().equals(usuarioLogadoService.getEmpresaIdLogada())) {
            throw new MsgApiException(MSG_BLOQUEAR_PROPRIA_EMPRESA, HttpStatus.FORBIDDEN);
        }

        if (empresa.getPessoa() == null || empresa.getPessoa().getId() == null) {
            throw new MsgApiException("Pessoa deve ser informada para atualizar a empresa.");
        }

        existente.setPlano(validarPlano(empresa.getPlano()));
        existente.setPessoa(entityManager.getReference(Pessoa.class, empresa.getPessoa().getId()));
        existente.setTotalUsuario(empresa.getTotalUsuario());
        existente.setTotalCliente(empresa.getTotalCliente());
        existente.setPlanoAtivo(empresa.getPlanoAtivo());
        existente.setBloqueio(empresa.getBloqueio());
        existente.setLogoMarca(empresa.getLogoMarca());
        existente.setVigenciaPlano(empresa.getVigenciaPlano());

        return converterParaDto(empresaRepository.saveAndFlush(existente));

    }

    private Plano validarPlano(Plano plano) {
        if (plano == null || plano.getId() == null) {
            throw new MsgApiException("Plano deve ser informado para a empresa.");
        }

        return planoRepository.findById(plano.getId())
                .orElseThrow(() -> new MsgApiException("Plano com id: " + plano.getId() + " não foi encontrado."));
    }

    /** Administrador vê qualquer empresa; o usuário comum, só a dele. */
    public EmpresaDTO buscaPorId(Long id) {

        if (!usuarioLogadoService.isAdmin() && !usuarioLogadoService.getEmpresaIdLogada().equals(id)) {
            throw new MsgApiException("Apenas administradores podem consultar outras empresas.", HttpStatus.FORBIDDEN);
        }

        Empresa empresa = empresaRepository.buscarPorId(id);

        if (empresa == null) {
            throw new MsgApiException("Empresa com id: " + id + " não foi encontrada.");
        }

        return converterParaDto(empresa);
    }

    public List<EmpresaDTO> findAll() {
        exigirAdmin("consultar");
        return converterListaParaDto(empresaRepository.findAll());
    }

    public List<EmpresaDTO> buscaPorNome(String nome) {
        exigirAdmin("consultar");
        return converterListaParaDto(empresaRepository.buscaPorNome(nome));
    }

    public boolean existePorNome(String nome) {
        exigirAdmin("consultar");
        return empresaRepository.existePorNome(nome);
    }

    public boolean existePorNomeDiferenteId(Long id, String nome) {
        exigirAdmin("consultar");
        return empresaRepository.existePorNomeDiferenteId(id, nome);
    }

    @Transactional
    public void deleteById(Long id) {
        exigirAdmin("deletar");

        /*P25: apagar a própria empresa tiraria o acesso do admin logado e de todos os usuários dela*/
        if (id != null && id.equals(usuarioLogadoService.getEmpresaIdLogada())) {
            throw new MsgApiException(MSG_APAGAR_PROPRIA_EMPRESA, HttpStatus.FORBIDDEN);
        }

        if (empresaRepository.buscarPorId(id) == null) {
            throw new MsgApiException("Empresa não encontrada ou já foi deletada.");
        }

        empresaRepository.deleteById(id);

    }

    // ====================Conversão para DTO====================

    private EmpresaDTO converterParaDto(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setPlanoId(empresa.getPlano().getId());
        dto.setPlanoNome(empresa.getPlano().getNome());
        dto.setTotalUsuario(empresa.getTotalUsuario());
        dto.setTotalCliente(empresa.getTotalCliente());
        dto.setPlanoAtivo(empresa.getPlanoAtivo());
        dto.setBloqueio(empresa.getBloqueio());
        dto.setLogoMarca(empresa.getLogoMarca());
        dto.setVigenciaPlano(empresa.getVigenciaPlano());
        dto.setPessoaId(empresa.getPessoa().getId());
        dto.setPessoaNome(empresa.getPessoa().getNome());
        return dto;
    }

    private List<EmpresaDTO> converterListaParaDto(List<Empresa> empresas) {
        return empresas.stream().map(this::converterParaDto).toList();
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