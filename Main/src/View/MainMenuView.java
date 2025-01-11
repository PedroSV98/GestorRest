package View;

import Controller.ConfiguracoesController;
import Controller.ControllerMesa;
import Controller.ControllerPrato;

import javax.swing.text.View;
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
            System.out.println("1. Gerir Mesas");
            System.out.println("2. Gerir Menus");
            System.out.println("3. Gerir Dia-a-Dia");
            System.out.println("4. Consultar Estatísticas");
            System.out.println("5. Configurações");
            System.out.println("6. Sair");
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
                    ConfiguracoesView configuracoesView = new ConfiguracoesView(configuracoesController);
                    configuracoesView.exibirMenu();
                    break;
                case 6:
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
