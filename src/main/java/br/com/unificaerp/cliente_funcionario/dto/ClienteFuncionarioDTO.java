package br.com.unificaerp.cliente_funcionario.dto;

import br.com.unificaerp.cliente_funcionario.model.enums.TipoClienteFuncionario;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ClienteFuncionarioDTO {

    private Long id;
    private TipoClienteFuncionario tipoClienteFuncionario;
    private Long usuarioId;
    private Long pessoaId;
    private String nomePessoa;
    private Long empresaId;

}
