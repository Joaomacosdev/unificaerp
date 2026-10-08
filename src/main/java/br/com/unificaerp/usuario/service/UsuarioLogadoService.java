package br.com.unificaerp.usuario.service;

import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.usuario.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {
    public UsuarioAutenticado getUsuarioAutenticado() {
        return (UsuarioAutenticado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Usuario getUsuarioLogado() {
        return getUsuarioAutenticado().getUsuario();
    }

    public Empresa getEmpresaLogada() {
        return getUsuarioLogado().getEmpresa();
    }

    public Long getEmpresaIdLogada() {
        return getEmpresaLogada().getId();
    }


    public Long getUsuarioLogadoId() {
        return getUsuarioLogado().getId();
    }

    public boolean isAdmin() {
        return getUsuarioLogado().idAdmin();
    }


}
