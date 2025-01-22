package View;

import Controller.ConfiguracoesController;
import Controller.ControllerMesa;
import Controller.ControllerPrato;
import Controller.LoginController;
import Model.LoginModel;


import java.util.Scanner;

public class MainMenuView {
    private ConfiguracoesController configuracoesController;

    public MainMenuView() {
        configuracoesController = ConfiguracoesController.getInstancia();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== Gestão de Restaurante ===");
            System.out.println("1. Gerir Mesas");
            System.out.println("2. Gerir Menus");
            System.out.println("3. Gerir Dia-a-Dia");
            System.out.println("4. Consultar Estatísticas");
            System.out.println("5. Configurações");
            System.out.println("6. Sair");
            System.out.println("Password atual: " + configuracoesController.getModelo().getPassword());
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    ViewMesa viewMesa = new ViewMesa(new ControllerMesa());
                    viewMesa.exibirMenu();
                    break;
                case 2:
                    ViewPrato viewPrato = new ViewPrato(new ControllerPrato());
                    viewPrato.exibirMenu();
                    break;
                case 3:
                    System.out.println("Opção de gerir dia-a-dia ainda não implementada.");
                    break;
                case 4:
                    System.out.println("Opção de consultar estatísticas ainda não implementada.");
                    break;
                case 5:
                    LoginView loginView = new LoginView();
                    LoginModel loginModel = new LoginModel(configuracoesController.getModelo());// Criação da view de login
                    LoginController loginController = new LoginController(loginModel, loginView);  // Criação do controller de login
                    loginController.iniciarLogin(configuracoesController.getModelo());  // Chama o método de autenticação
                    break;
                case 6:
                    System.out.println("Tem a Certeza Que Quer Sair? (S/N)");
                    String resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Encerrando a Aplicação...");
                        configuracoesController.guardar();
                        running = false;
                    } else {
                        System.out.println("Operação Cancelada.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente Novamente.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        MainMenuView menu = new MainMenuView();
        menu.exibirMenu();
    }
}
