package br.com.unificaerp.produto.model.enums;

public enum UnidadeMedida {
    UNIDADE("Unidade"),
    QUILOGRAMA("Quilograma"),
    GRAMA("Grama"),
    LITRO("Litro"),
    MILILITRO("Mililitro"),
    METRO("Metro"),
    CENTIMETRO("Centímetro"),
    CAIXA("Caixa"),
    PACOTE("Pacote");

    private final String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
