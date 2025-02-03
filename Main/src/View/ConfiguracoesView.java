package View;

import Controller.ConfiguracoesController;
import Controller.LogController;
import Controller.LoginController;
import Model.Configuracoes;
import Model.LoginModel;

import java.util.Scanner;

public class ConfiguracoesView {
    private final ConfiguracoesController controller;
    private final LogController logController;
    private final LogController.ScannerLog scannerLog;

    public ConfiguracoesView(ConfiguracoesController controller, LoginController loginController, LogController logController) {
        this.controller = controller;
        this.logController = logController;
        Scanner scanner = new Scanner(System.in);
        this.scannerLog = logController.new ScannerLog(new Scanner(System.in), logController);  // Inicializando ScannerLog com instância de LogController
    }

    public void exibirMenu() {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n=== Configurações ===");
            System.out.println("1. Ver Configurações Atuais");
            System.out.println("2. Atualizar Configuração");
            System.out.println("3. Guardar Configurações");
            System.out.println("4. Ver Log's");
            System.out.println("5. Voltar ao Menu Principal");

            System.out.print("Escolha uma opção: ");
            int opcao = scannerLog.nextInt();
            scannerLog.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> mostrarConfiguracoes();
                case 2 -> atualizarConfiguracao();
                case 3 -> controller.guardar();
                case 4 -> logController.listarEExibirArquivos();
                case 5 -> sair = true;
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void mostrarConfiguracoes() {
        Configuracoes conf = controller.getModelo();
        System.out.println("\n=== Configurações Atuais ===");
        System.out.println("CaminhoFicheiros: " + conf.getCaminhoFicheiros());
        System.out.println("Separador: " + conf.getSeparador());
        System.out.println("UnidadesTempoDia: " + conf.getUnidadesTempoDia());
        System.out.println("TempoEsperaAcao: " + conf.getTempoEsperaAcao());
        System.out.println("CustoClienteNaoAtendido: " + conf.getCustoClienteNaoAtendido());
        System.out.println("Password: ********");
    }

    private void atualizarConfiguracao() {
        Configuracoes conf = controller.getModelo();
        System.out.println("\nCampos disponíveis para atualizar:");
        System.out.println("1. CaminhoFicheiros");
        System.out.println("2. Separador");
        System.out.println("3. UnidadesTempoDia");
        System.out.println("4. TempoEsperaAcao");
        System.out.println("5. CustoClienteNaoAtendido");
        System.out.println("6. Password");
        System.out.println("0. Voltar");

        System.out.print("\nQual o campo que deseja alterar? ");
        String campo = scannerLog.nextLine();

        switch (campo) {
            case "1" -> {
                System.out.println("Caminho do Ficheiro Atual: " + conf.getCaminhoFicheiros());
                System.out.print("Novo Caminho: ");
                controller.atualizarCaminhoFicheiros(scannerLog.nextLine());
            }
            case "2" -> {
                System.out.println("Separador Atual: " + conf.getSeparador());
                System.out.print("Novo Separador: ");
                controller.atualizarSeparador(scannerLog.nextLine());
            }
            case "3" -> {
                System.out.println("Unidades de Tempo Atuais: " + conf.getUnidadesTempoDia());
                System.out.print("Novo valor (int): ");
                controller.atualizarUnidadesTempoDia(scannerLog.nextInt());
                scannerLog.nextLine();
            }
            case "4" -> {
                System.out.println("Unidades de Tempo Ação Atuais: " + conf.getTempoEsperaAcao());
                System.out.print("Novo valor (int): ");
                controller.atualizarTempoEsperaAcao(scannerLog.nextInt());
                scannerLog.nextLine();
            }
            case "5" -> {
                System.out.println("Custo Cliente Não Atendido Atual: " + conf.getCustoClienteNaoAtendido());
                System.out.print("Novo valor (double): ");
                controller.atualizarCustoClienteNaoAtendido(scannerLog.nextDouble());
                scannerLog.nextLine();
            }
            case "6" -> {
                LoginModel loginModel = new LoginModel(controller.getModelo(), logController);
                LoginView loginView = new LoginView(logController);
                LoginController loginController = new LoginController(loginModel, loginView);
                loginController.alterarSenha(scannerLog);
            }
            case "0" -> {
                exibirMenu();
            }
            default -> System.out.println("Campo inválido. Verifique a lista acima.");
        }
    }
}
