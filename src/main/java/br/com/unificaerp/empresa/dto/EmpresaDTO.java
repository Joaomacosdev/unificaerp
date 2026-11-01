package br.com.unificaerp.empresa.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmpresaDTO {
    private Long id;
    private String planoId;
    private Integer totalUsuario = 0;
    private Integer totalCliente = 0;
    private Boolean planoAtivo = false;
    private Boolean bloqueio = false;
    private String logoMarca;
    private LocalDate vigenciaPlano;
    private Long pessoaId;
    private String pessoaNome;
}
