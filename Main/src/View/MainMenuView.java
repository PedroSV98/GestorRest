package View;

import Controller.*;
import Model.Configuracoes;
import Model.LoginModel;
import Model.Reserva;

import java.util.Scanner;

public class MainMenuView {
    private final ConfiguracoesController configuracoesController;
    private final LogController logController;
    private final LogController.ScannerLog scannerLog;  // Scanner com log

    public MainMenuView() {
        configuracoesController = ConfiguracoesController.getInstancia();
        Configuracoes configuracoes = configuracoesController.getModelo();
        this.logController = new LogController(configuracoes);

        logController.inicializarLog();

        // 🔄 Criar ScannerLog para capturar entradas automaticamente
        Scanner scanner = new Scanner(System.in);
        this.scannerLog = logController.new ScannerLog(scanner, logController);
    }

    public void exibirMenu() {
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

            int opcao = scannerLog.nextInt();  // ✅ Scanner com log automático
             // Consumir quebra de linha

            switch (opcao) {
                case 1 -> {
                    ControllerMesa controllerMesa = new ControllerMesa(configuracoesController);
                    ViewMesa viewMesa = new ViewMesa(controllerMesa, logController);
                    viewMesa.exibirMenu();
                }
                case 2 -> {
                    ControllerPrato controllerPrato = new ControllerPrato(configuracoesController);
                    ViewPrato viewPrato = new ViewPrato(controllerPrato, logController);
                    viewPrato.exibirMenu();
                }
                case 3 -> {
                    ControllerReserva controllerReserva = new ControllerReserva(configuracoesController);
                    Reserva[] reservas = controllerReserva.lerReservas();

                    ControllerPedido controllerPedido = new ControllerPedido(configuracoesController);
                    ControllerMesa controllerMesa = new ControllerMesa(configuracoesController);
                    ControllerPrato controllerPrato = new ControllerPrato(configuracoesController);

                    ControllerGestaoDiaADia controllerGestaoDiaADia = new ControllerGestaoDiaADia(
                            controllerPedido, controllerMesa, controllerPrato,
                            configuracoesController.getModelo().getUnidadesTempoDia(), reservas, logController
                    );

                    ControllerEstatisticas controllerEstatisticas = new ControllerEstatisticas(controllerGestaoDiaADia);
                    ViewGestaoDiaADia viewGestaoDiaADia = new ViewGestaoDiaADia(controllerGestaoDiaADia, controllerEstatisticas, logController);
                    viewGestaoDiaADia.exibirMenu();
                }
                case 4 -> {
                    System.out.println("Opção de consultar estatísticas ainda não implementada.");
                }
                case 5 -> {
                    LoginView loginView = new LoginView(logController);
                    LoginModel loginModel = new LoginModel(configuracoesController.getModelo(), logController);
                    LoginController loginController = new LoginController(loginModel, loginView);
                    loginController.iniciarLogin(configuracoesController.getModelo());
                }
                case 6 -> {
                    System.out.println("Tem a Certeza Que Quer Sair? (S/N)");
                    String resposta = scannerLog.nextLine();
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

        scannerLog.close();
    }

    public static void main(String[] args) {
        MainMenuView menu = new MainMenuView();
        menu.exibirMenu();
    }
}
