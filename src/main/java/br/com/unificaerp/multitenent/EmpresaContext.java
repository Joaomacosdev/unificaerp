package br.com.unificaerp.multitenent;

import br.com.unificaerp.usuario.service.UsuarioLogadoService;
import org.springframework.stereotype.Component;

@Component
public class EmpresaContext {

    private final UsuarioLogadoService usuarioLogadoService;

    public EmpresaContext(UsuarioLogadoService usuarioLogadoService) {
        this.usuarioLogadoService = usuarioLogadoService;
    }

    public Long getEmpresaId() {
        return usuarioLogadoService.getEmpresaIdLogada();
    }

}
