package Controller;

import Model.Configuracoes;
import View.ConfiguracoesView;

import java.io.IOException;

public class ConfiguracoesController {

    private Configuracoes modelo;
    private static ConfiguracoesController controller;

    // Aqui definimos o caminho completo do config.txt (ajusta se necessário)
    private final String CAMINHO_FICHEIRO = "C:\\Users\\Guts\\Documents\\DocumentosDAS\\1ano\\lp1\\GestorRest\\Main\\src\\data\\config.txt";

    private ConfiguracoesController() {
        // Instancia o modelo com o caminho do ficheiro
        modelo = new Configuracoes(CAMINHO_FICHEIRO);

        // Tenta carregar assim que o controller é criado
        try {
            modelo.carregarConfiguracoes();
        } catch (IOException e) {
            System.out.println("Erro ao carregar configurações: " + e.getMessage());
        }
    }

    public static ConfiguracoesController getInstancia(){
        // padrão singleton aplicado
        if(controller == null)
            controller = new ConfiguracoesController();

        return controller;

    }

    // Método para retornar o objeto modelo (para a View exibir)
    public Configuracoes getModelo() {
        return modelo;
    }

    // Atualizar campos específicos
    public void atualizarCaminhoFicheiros(String valor) {
        modelo.setCaminhoFicheiros(valor);
    }

    public void atualizarSeparador(String valor) {
        modelo.setSeparador(valor);
    }

    public void atualizarUnidadesTempoDia(int valor) {
        modelo.setUnidadesTempoDia(valor);
    }

    public void atualizarTempoEsperaAcao(int valor) {
        modelo.setTempoEsperaAcao(valor);
    }

    public void atualizarCustoClienteNaoAtendido(double valor) {
        modelo.setCustoClienteNaoAtendido(valor);
    }


    // Guardar no ficheiro
    public void guardar() {
        try {
            modelo.guardarConfiguracoes();
            System.out.println("Configurações guardadas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao guardar configurações: " + e.getMessage());
        }
    }
}

