package View;

import Controller.*;
import Model.LoginModel;
import Model.Reserva;

import java.util.Scanner;

public class MainMenuView {
    private final ConfiguracoesController configuracoesController;

    // Variáveis para preservar o estado
    private ControllerGestaoDiaADia controllerGestaoDiaADia;
    private ControllerEstatisticas controllerEstatisticas;
    private ViewGestaoDiaADia viewGestaoDiaADia;

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
            System.out.println("4. Configurações");
            System.out.println("5. Sair");
            System.out.println("Password atual: " + configuracoesController.getModelo().getPassword());
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1 -> {
                    ControllerMesa controllerMesa = new ControllerMesa(configuracoesController);
                    ViewMesa viewMesa = new ViewMesa(controllerMesa);
                    viewMesa.exibirMenu();
                }
                case 2 -> {
                    ControllerPrato controllerPrato = new ControllerPrato(configuracoesController);
                    ViewPrato viewPrato = new ViewPrato(controllerPrato);
                    viewPrato.exibirMenu();
                }
                case 3 -> {
                    // Inicializa os controladores e visualizações apenas se ainda não foram criados
                    if (controllerGestaoDiaADia == null) {
                        ControllerReserva controllerReserva = new ControllerReserva(configuracoesController);
                        Reserva[] reservas = controllerReserva.lerReservas();

                        ControllerPedido controllerPedido = new ControllerPedido(configuracoesController);
                        ControllerMesa controllerMesa = new ControllerMesa(configuracoesController);
                        ControllerPrato controllerPrato = new ControllerPrato(configuracoesController);

                        controllerGestaoDiaADia = new ControllerGestaoDiaADia(
                                controllerPedido, controllerMesa, controllerPrato,
                                configuracoesController.getModelo().getUnidadesTempoDia(), reservas
                        );

                        controllerEstatisticas = new ControllerEstatisticas(controllerGestaoDiaADia);
                        viewGestaoDiaADia = new ViewGestaoDiaADia(controllerGestaoDiaADia, controllerEstatisticas);
                    }

                    // Exibe o menu de Gestão do Dia-a-Dia sem recriar os controladores
                    viewGestaoDiaADia.exibirMenu();
                }
                case 4 -> {
                    LoginView loginView = new LoginView();
                    LoginModel loginModel = new LoginModel(configuracoesController.getModelo());
                    LoginController loginController = new LoginController(loginModel, loginView);
                    loginController.iniciarLogin(configuracoesController.getModelo());
                }
                case 5 -> {
                    System.out.println("Tem a Certeza Que Quer Sair? (S/N)");
                    String resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Encerrar a Aplicação...");
                        configuracoesController.guardar();
                        running = false;
                    } else {
                        System.out.println("Operação Cancelada.");
                    }
                }
                default -> System.out.println("Opção inválida. Tente Novamente.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        MainMenuView menu = new MainMenuView();
        menu.exibirMenu();
    }
}
