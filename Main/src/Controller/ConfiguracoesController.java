package Controller;

import Model.Configuracoes;

public class ConfiguracoesController {
    private Configuracoes configuracoes;

    public ConfiguracoesController() {
        configuracoes = new Configuracoes();
    }

    public void carregarConfiguracoes() {
        configuracoes.carregar();
    }

    public void salvarConfiguracoes() {
        configuracoes.salvar();
    }

    public String obterConfiguracao(String chave) {
        return configuracoes.get(chave);
    }

    public void atualizarConfiguracao(String chave, String valor) {
        configuracoes.set(chave, valor);
        salvarConfiguracoes();
    }

    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }
}
