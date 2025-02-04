package View;

import Controller.ConfiguracoesController;
import Controller.InputHelper;
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

            int opcao = InputHelper.lerInteiro(scanner, "Escolha uma opção: ");

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

        String campo = InputHelper.lerString(scanner, "\nQual o campo que deseja alterar? ");

        switch (campo) {
            case "1":
                System.out.println("Caminho do Ficheiro Atual: " + conf.getCaminhoFicheiro());
                String novoCaminho = InputHelper.lerString(scanner, "Novo Caminho: ");
                controller.atualizarCaminhoFicheiros(novoCaminho);
                break;
            case "2":
                System.out.println("Separador Atual: " + conf.getSeparador());
                String novoSeparador = InputHelper.lerString(scanner, "Novo Separador: ");
                controller.atualizarSeparador(novoSeparador);
                break;
            case "3":
                System.out.println("Unidades de Tempo Atuais: " + conf.getUnidadesTempoDia());
                int novoUnidades = InputHelper.lerInteiro(scanner, "Novo valor (int): ");
                controller.atualizarUnidadesTempoDia(novoUnidades);
                break;
            case "4":
                System.out.println("Tempo de Espera Ação Atual: " + conf.getTempoEsperaAcao());
                int novoTempoEspera = InputHelper.lerInteiro(scanner, "Novo valor (int): ");
                controller.atualizarTempoEsperaAcao(novoTempoEspera);
                break;
            case "5":
                System.out.println("Custo Cliente Não Atendido Atual: " + conf.getCustoClienteNaoAtendido());
                double novoCusto = InputHelper.lerDouble(scanner, "Novo valor (double): ");
                controller.atualizarCustoClienteNaoAtendido(novoCusto);
                break;
            case "6":
                // Cria uma instância de LoginModel e LoginView para alterar a senha
                LoginModel loginModel = new LoginModel(controller.getModelo());
                LoginView loginView = new LoginView();
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
