package br.com.unificaerp.cliente_funcionario.model.enums;

public enum TipoCliente {
    PF("Pessoa física"),
    PJ("Pessoa jurídica");

    private String descricao;

    TipoCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
