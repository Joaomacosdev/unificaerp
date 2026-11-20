package br.com.unificaerp.plano.model.dto;


import br.com.unificaerp.plano.model.enums.TipoPlano;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private Double valorMensal;
    private Integer limiteUsuario;
    private Integer limiteCliente;
    private TipoPlano tipoPlano;

}