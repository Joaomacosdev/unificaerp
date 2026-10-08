package br.com.unificaerp.cliente_funcionario.model.enums;

public enum TipoCliente {
    CLIENTE("Cliente"),
    FUNCIONARIO("Funcionário"),
    PRESTADOR_SERVICO("Prestador de serviço");

    private final String descricao;

    private TipoCliente(String descricao) {
        this.descricao = descricao;
    }


    public String getDescricao() {
        return descricao;
    }
}
