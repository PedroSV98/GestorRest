package View;

import Controller.ConfiguracoesController;
import java.util.Scanner;

public class MainMenuView {
    private ConfiguracoesController configuracoesController;

    public MainMenuView() {
        configuracoesController = new ConfiguracoesController();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== Gestão de Restaurante ===");
            System.out.println("1. Gerir Mesas e Menus");
            System.out.println("2. Gerir Dia-a-Dia");
            System.out.println("3. Consultar Estatísticas");
            System.out.println("4. Configurações");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.println("Opção de gerir mesas e menus ainda não implementada.");
                    break;
                case 2:
                    System.out.println("Opção de gerir dia-a-dia ainda não implementada.");
                    break;
                case 3:
                    System.out.println("Opção de consultar estatísticas ainda não implementada.");
                    break;
                case 4:
                    ConfiguracoesView configuracoesView = new ConfiguracoesView(configuracoesController);
                    configuracoesView.exibirMenu();
                    break;
                case 5:
                    System.out.println("Encerrando aplicação...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        MainMenuView menu = new MainMenuView();
        menu.exibirMenu();
    }
}
