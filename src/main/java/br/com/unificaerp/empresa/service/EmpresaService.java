package br.com.unificaerp.empresa.service;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.empresa.repository.EmpresaRepository;
import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.plano.model.Plano;
import br.com.unificaerp.plano.repository.PlanoRepository;
import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final PlanoRepository planoRepository;

    private final UsuarioLogadoService usuarioLogadoService;

    public EmpresaService(EmpresaRepository empresaRepository, PlanoRepository planoRepository, UsuarioLogadoService usuarioLogadoService) {
        this.empresaRepository = empresaRepository;
        this.planoRepository = planoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }



    public Empresa salvar(Empresa empresa) {

        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem cadastrar empresas.", HttpStatus.FORBIDDEN);
        }

        if (empresa.getPessoa() == null || empresa.getPessoa().getId() == null) {
            throw new MsgApiException("Pessoa deve ser informada para cadastrar a empresa.");
        }

        empresa.setPlano(validarPlano(empresa.getPlano()));

        return empresaRepository.saveAndFlush(empresa);
    }

    public Empresa atualizar(Empresa empresa) {

        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem atualizar empresas.", HttpStatus.FORBIDDEN);
        }

        if (empresa.getId() == null) {
            throw new MsgApiException("Id da empresa deve ser informado para edição.");
        }

        Empresa existente = empresaRepository.buscarPorId(empresa.getId());

        if (existente == null) {
            throw new MsgApiException("Empresa com id: " + empresa.getId() + " não foi encontrada.");
        }

        if (empresa.getPessoa() == null || empresa.getPessoa().getId() == null) {
            throw new MsgApiException("Pessoa deve ser informada para atualizar a empresa.");
        }

        existente.setPlano(validarPlano(empresa.getPlano()));
        existente.setPessoa(empresa.getPessoa());
        existente.setTotalUsuario(empresa.getTotalUsuario());
        existente.setTotalCliente(empresa.getTotalCliente());
        existente.setPlanoAtivo(empresa.getPlanoAtivo());
        existente.setBloqueio(empresa.getBloqueio());
        existente.setLogoMarca(empresa.getLogoMarca());
        existente.setVigenciaPlano(empresa.getVigenciaPlano());

        return empresaRepository.saveAndFlush(existente);
    }

    private Plano validarPlano(Plano plano) {
        if (plano == null || plano.getId() == null) {
            throw new MsgApiException("Plano deve ser informado para a empresa.");
        }

        return planoRepository.findById(plano.getId())
                .orElseThrow(() -> new MsgApiException("Plano com id: " + plano.getId() + " não foi encontrado."));
    }

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
        if (!usuarioLogadoService.isAdmin()) {
            throw new MsgApiException("Apenas administradores podem deletar empresas.", HttpStatus.FORBIDDEN);
        }

        if (empresaRepository.buscarPorId(id) == null) {
            throw new MsgApiException("Empresa não encontrada ou já foi deletada.");
        }

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
