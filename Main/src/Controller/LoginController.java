package Controller;

import Model.Configuracoes;
import Model.LoginModel;
import View.ConfiguracoesView;
import View.LoginView;
import View.MainMenuView;

public class LoginController {
    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void iniciarLogin(Configuracoes configuracoes) {
        boolean senhaCorretaInformada = false;
        while (!senhaCorretaInformada) {
            String senha = view.inserirSenha();

            if (senha.equals("0")) {
                view.exibirMensagem("Voltar ao Menu Principal...");
                MainMenuView mainMenuView = new MainMenuView();
                mainMenuView.exibirMenu();
                break;
            }

            if (configuracoes.getPassword().equals(senha)) {
                view.exibirMensagem("Acesso concedido!");
                senhaCorretaInformada = true;

                // Verificando se o LogController está corretamente inicializado
                LogController logController = model.getLogController();
                if (logController == null) {
                    System.out.println("❌ O LogController não foi inicializado corretamente.");
                    return; // Abortando caso o LogController seja nulo
                }

                // Instanciando o controlador de configurações
                ConfiguracoesController configuracoesController = ConfiguracoesController.getInstancia();

                // Passando corretamente o LogController para a LoginView
                LoginView loginView = new LoginView(logController);  // Passando logController para a LoginView
                LoginModel loginModel = new LoginModel(configuracoes, logController); // Passando logController para o LoginModel

                // Criando o LoginController com o modelo e a view correta
                LoginController loginControllerInstance = new LoginController(loginModel, loginView);

                // Criando a ConfiguracoesView, passando as dependências necessárias
                ConfiguracoesView configuracoesView = new ConfiguracoesView(configuracoesController, loginControllerInstance, logController);
                configuracoesView.exibirMenu();
            } else {
                view.exibirMensagem("Senha incorreta. Tente novamente.");
            }
        }
    }

    public void alterarSenha(LogController.ScannerLog scanner) {
        System.out.println("=== Alteração de Senha ===");

        // Solicitar a senha atual
        System.out.print("Digite a senha atual: ");
        String senhaAtual = scanner.nextLine();

        // Validar a senha atual
        if (!model.validarSenha(senhaAtual)) {
            System.out.println("Senha atual incorreta. Operação cancelada.");
            return;
        }

        // Solicitar a nova senha duas vezes para confirmação
        String novaSenha;
        String confirmacaoSenha;
        do {
            System.out.print("Digite a nova senha: ");
            novaSenha = scanner.nextLine();

            System.out.print("Confirme a nova senha: ");
            confirmacaoSenha = scanner.nextLine();

            if (!novaSenha.equals(confirmacaoSenha)) {
                System.out.println("As senhas não coincidem. Tente novamente.");
            } else if (novaSenha.isEmpty()) {
                System.out.println("A senha não pode estar vazia. Tente novamente.");
            }
        } while (!novaSenha.equals(confirmacaoSenha) || novaSenha.isEmpty());

        model.alterarSenha(novaSenha);
        view.exibirMensagem("Password alterado com sucesso!");
    }
}
