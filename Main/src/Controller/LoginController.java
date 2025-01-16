package Controller;

import Model.Configuracoes;
import Model.LoginModel;
import View.ConfiguracoesView;
import View.LoginView;
import View.MainMenuView;

import java.util.Scanner;
import java.io.IOException;


public class LoginController {
    private LoginModel Model;
    private LoginView view;

    public LoginController(LoginModel Model, LoginView view) {
        this.Model = Model;
        this.view = view;
    }

    public void iniciarLogin(Configuracoes model) {
        boolean senhaCorretaInformada = false;
        while (!senhaCorretaInformada) {
            /*try {
                //Model.getConfiguracoes().carregarConfiguracoes(); // Carregar as configurações sempre que iniciar o login
            } catch (IOException e) {
                view.exibirMensagem("Erro ao carregar configurações.");
                return;
            }*/
           String senha = view.inserirSenha();

            if (senha.equals("0")) {
                view.exibirMensagem("Voltando ao Menu Principal...");
                MainMenuView mainMenuView = new MainMenuView();
                mainMenuView.exibirMenu();
                break;
            }

            if (model.getPassword().equals(senha)) {
                //if (Model.validarSenha(senha)) {
                view.exibirMensagem("Acesso concedido!");
                senhaCorretaInformada = true;

                ConfiguracoesController configuracoesController = ConfiguracoesController.getInstancia();
                LoginView loginView = new LoginView();
                LoginModel loginModel = new LoginModel(configuracoesController.getModelo());
                LoginController loginController = new LoginController(loginModel, loginView);
                ConfiguracoesView configuracoesView = new ConfiguracoesView(configuracoesController, loginController);
                configuracoesView.exibirMenu();

            } else {
                view.exibirMensagem("Senha incorreta. Tente novamente.");
            }
        }
    }

    public void alterarSenha(Scanner scanner) {
        System.out.println("=== Alteração de Senha ===");

        // Solicitar a senha atual
        System.out.print("Digite a senha atual: ");
        String senhaAtual = scanner.nextLine();

        // Validar a senha atual
        if (!Model.validarSenha(senhaAtual)) {
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

        Model.getConfiguracoes().setSenhaEmMemoria(novaSenha);
        view.exibirMensagem("Password alterado com sucesso!");
    }
}


