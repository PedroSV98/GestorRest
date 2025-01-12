package View;

import Controller.ConfiguracoesController;
import Controller.LoginController;
import Model.Configuracoes;
import Model.LoginModel;

import java.util.Scanner;


public class ConfiguracoesView {

    private ConfiguracoesController controller;

    private LoginController loginController;

    public ConfiguracoesView(ConfiguracoesController controller, LoginController loginController) {

        this.controller = controller;
        this.loginController = loginController;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("\n=== Configurações ===");
            System.out.println("1. Ver Configurações Atuais");
            System.out.println("2. Atualizar Configuração");
            System.out.println("3. Guardar Configurações");
            System.out.println("4. Voltar ao Menu Principal");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    mostrarConfiguracoes();
                    break;
                case 2:
                    atualizarConfiguracao(scanner);
                    break;
                case 3:
                    controller.guardar();
                    break;
                case 4:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void mostrarConfiguracoes() {
        Configuracoes conf = controller.getModelo();
        System.out.println("\n=== Configurações Atuais ===");
        System.out.println("caminhoFicheiros: " + conf.getCaminhoFicheiros());
        System.out.println("separador: " + conf.getSeparador());
        System.out.println("unidadesTempoDia: " + conf.getUnidadesTempoDia());
        System.out.println("tempoEsperaAcao: " + conf.getTempoEsperaAcao());
        System.out.println("custoClienteNaoAtendido: " + conf.getCustoClienteNaoAtendido());
        System.out.println("password: " + conf.getPassword());
    }

    private void atualizarConfiguracao(Scanner scanner) {
        System.out.println("\nCampos disponíveis para atualizar:");
        System.out.println("- caminhoFicheiros");
        System.out.println("- separador");
        System.out.println("- unidadesTempoDia");
        System.out.println("- tempoEsperaAcao");
        System.out.println("- custoClienteNaoAtendido");
        System.out.println("- password");

        System.out.print("\nQual o campo que deseja alterar? ");
        String campo = scanner.nextLine();

        switch (campo) {
            case "caminhoFicheiros":
                System.out.print("Novo valor: ");
                controller.atualizarCaminhoFicheiros(scanner.nextLine());
                break;
            case "separador":
                System.out.print("Novo valor: ");
                controller.atualizarSeparador(scanner.nextLine());
                break;
            case "unidadesTempoDia":
                System.out.print("Novo valor (int): ");
                controller.atualizarUnidadesTempoDia(scanner.nextInt());
                scanner.nextLine();
                break;
            case "tempoEsperaAcao":
                System.out.print("Novo valor (int): ");
                controller.atualizarTempoEsperaAcao(scanner.nextInt());
                scanner.nextLine();
                break;
            case "custoClienteNaoAtendido":
                System.out.print("Novo valor (double): ");
                controller.atualizarCustoClienteNaoAtendido(scanner.nextDouble());
                scanner.nextLine();
                break;
            case "password":
                LoginModel loginModel = new LoginModel(controller.getModelo()); // Passando o modelo corretamente
                LoginView loginView = new LoginView(); // Criando a view de login
                LoginController loginController = new LoginController(loginModel, loginView);
                loginController.alterarSenha(scanner);
                break;
            default:
                System.out.println("Campo inválido. Verifique a lista acima.");
                break;
        }
    }
}

