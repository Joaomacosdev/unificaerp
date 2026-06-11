package br.com.unificaerp.plano.model.enums;

public enum TipoPlano {

    FREE("Plano Gratuito"),
    STARTER("Plano Starter"),
    BASIC("Plano Básico"),
    PRO("Plano Profissional"),
    BUSINESS("Plano Empresarial"),
    ENTERPRISE("Plano Corporativo");

    private final String descricao;

    TipoPlano(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
