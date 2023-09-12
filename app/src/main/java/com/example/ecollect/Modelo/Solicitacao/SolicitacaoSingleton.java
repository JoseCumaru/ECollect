package com.example.ecollect.Modelo.Solicitacao;

public class SolicitacaoSingleton {
    static com.example.ecollect.Modelo.Solicitacao.SolicitacaoSingleton SolicitacaoSingleton;
    private static Solicitacao solicitacao;

    public static Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public static void setSolicitacao(Solicitacao solicitacao) {
        SolicitacaoSingleton.solicitacao = solicitacao;
    }
}
