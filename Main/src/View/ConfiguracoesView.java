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
        System.out.println("CaminhoFicheiros: " + conf.getCaminhoFicheiros());
        System.out.println("Separador: " + conf.getSeparador());
        System.out.println("UnidadesTempoDia: " + conf.getUnidadesTempoDia());
        System.out.println("TempoEsperaAcao: " + conf.getTempoEsperaAcao());
        System.out.println("CustoClienteNaoAtendido: " + conf.getCustoClienteNaoAtendido());
        System.out.println("Password: ********");
    }

    private void atualizarConfiguracao(Scanner scanner) {
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
        String campo = scanner.nextLine();

        switch (campo) {
            case "1":
                System.out.println("Caminho do Ficheiro Atual: " + conf.getCaminhoFicheiro());
                System.out.print("Novo Caminho: ");
                controller.atualizarCaminhoFicheiros(scanner.nextLine());
                break;
            case "2":
                System.out.println("Separador Atual: " + conf.getSeparador());
                System.out.print("Novo Separador: ");
                controller.atualizarSeparador(scanner.nextLine());
                break;
            case "3":
                System.out.println("Unidades de Tempo Atuais: " + conf.getUnidadesTempoDia());
                System.out.print("Novo valor (int): ");
                controller.atualizarUnidadesTempoDia(scanner.nextInt());
                scanner.nextLine();
                break;
            case "4":
                System.out.println("Unidades de Tempo Ação Atuais: " + conf.getTempoEsperaAcao());
                System.out.print("Novo valor (int): ");
                controller.atualizarTempoEsperaAcao(scanner.nextInt());
                scanner.nextLine();
                break;
            case "5":
                System.out.println("Custo Cliente Não Atendido Atual: " + conf.getCustoClienteNaoAtendido());
                System.out.print("Novo valor (double): ");
                controller.atualizarCustoClienteNaoAtendido(scanner.nextDouble());
                scanner.nextLine();
                break;
            case "6":
                LoginModel loginModel = new LoginModel(controller.getModelo()); // Passando o modelo corretamente
                LoginView loginView = new LoginView(); // Criando a view de login
                LoginController loginController = new LoginController(loginModel, loginView);
                loginController.alterarSenha(scanner);
                break;
            case "0":
                System.out.println("A Voltar ao Menu Configurações...");
                exibirMenu();
                break;

            default:
                System.out.println("Campo inválido. Verifique a lista acima.");
                break;
        }
    }
}

