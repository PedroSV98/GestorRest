package Controller;

import Model.Configuracoes;

public class ConfiguracoesController {
    private Configuracoes configuracoes;

    // Construtor
    public ConfiguracoesController() {
        this.configuracoes = new Configuracoes();
    }

    // Método para carregar as configurações (poderia ser lido de um arquivo, se necessário)
    public void carregarConfiguracoes() {
        // Implementação para carregar configurações de um arquivo, se necessário
    }

    // Getter para acessar as configurações
    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }

    // Método para atualizar uma configuração
    public void atualizarConfiguracao(String chave, String valor) {
        switch (chave) {
            case "caminhoFicheiros":
                configuracoes.setCaminhoFicheiros(valor);
                break;
            case "separador":
                configuracoes.setSeparador(valor);
                break;
            case "unidadesTempoDia":
                configuracoes.setUnidadesTempoDia(Integer.parseInt(valor));
                break;
            case "tempoEsperaAcao":
                configuracoes.setTempoEsperaAcao(Integer.parseInt(valor));
                break;
            case "custoClienteNaoAtendido":
                configuracoes.setCustoClienteNaoAtendido(Double.parseDouble(valor));
                break;
            case "password":
                configuracoes.setPassword(valor);
                break;
            default:
                System.out.println("Configuração não encontrada: " + chave);
        }
    }
}
