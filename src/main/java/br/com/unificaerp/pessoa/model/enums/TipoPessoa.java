package br.com.unificaerp.pessoa.model.enums;

public enum TipoPessoa {

    FISICA("Pessoa física"),
    JURIDICA("Pessoa juridica");

    private final String descricao;

    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
