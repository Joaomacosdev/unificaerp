package br.com.unificaerp.role_usuario.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUsuarioDTO {

    private Long id;
    private Long acessoId;
    private String acessoNome;
    private Long usuarioId;
    private String usuarioNome;

}
