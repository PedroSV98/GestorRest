package View;

import Controller.*;
import Model.LoginModel;
import Model.Reserva;

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
                    // Passa o mesmo configuracoesController, para o ControllerMesa saber o caminho
                    ControllerMesa controllerMesa = new ControllerMesa(configuracoesController);
                    ViewMesa viewMesa = new ViewMesa(controllerMesa);
                    viewMesa.exibirMenu();
                    break;
                case 2:
                    ControllerPrato controllerPrato = new ControllerPrato(configuracoesController);
                    ViewPrato viewPrato = new ViewPrato(controllerPrato);
                    viewPrato.exibirMenu();
                    break;
                case 3:
                    // Carregar reservas
                    ControllerReserva controllerReserva = new ControllerReserva(configuracoesController);
                    Reserva[] reservas = controllerReserva.lerReservas();

                    // Criar o controlador do dia a dia com as reservas
                    ControllerGestaoDiaADia controllerGestaoDiaADia = new ControllerGestaoDiaADia(
                            new ControllerPedido(configuracoesController),
                            new ControllerMesa(configuracoesController),
                            new ControllerPrato(configuracoesController),
                            configuracoesController.getModelo().getUnidadesTempoDia(),
                            reservas // Passa as reservas carregadas
                    );

                    ViewGestaoDiaADia viewGestaoDiaADia = new ViewGestaoDiaADia(controllerGestaoDiaADia);
                    viewGestaoDiaADia.exibirMenu();
                    break;

                case 4:
                    System.out.println("Opção de consultar estatísticas ainda não implementada.");
                    break;
                case 5:
                    LoginView loginView = new LoginView();
                    LoginModel loginModel = new LoginModel(configuracoesController.getModelo()); // Criação da view de login
                    LoginController loginController = new LoginController(loginModel, loginView);  // Criação do controller de login
                    loginController.iniciarLogin(configuracoesController.getModelo());  // Chama o método de autenticação
                    break;
                case 6:
                    System.out.println("Tem a Certeza Que Quer Sair? (S/N)");
                    String resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Encerrar a Aplicação...");
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
