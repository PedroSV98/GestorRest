import View.MainMenuView;
import Controller.ConfiguracoesController;

public class Main {
    public static void main(String[] args) {
        // Criar o controlador de configurações
        ConfiguracoesController configuracoesController = new ConfiguracoesController();

        // Carregar as configurações do ficheiro
        configuracoesController.carregarConfiguracoes();

        // Exibir o menu principal
        MainMenuView menu = new MainMenuView();
        menu.exibirMenu();
    }
}
