package View;

import Controller.ConfiguracoesController;
import java.util.Scanner;

public class ConfiguracoesView {
    private ConfiguracoesController configuracoesController;

    public ConfiguracoesView(ConfiguracoesController controller) {
        this.configuracoesController = controller;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean configurando = true;

        while (configurando) {
            System.out.println("=== Configurações ===");
            System.out.println("1. Exibir Configurações Atuais");
            System.out.println("2. Alterar Configuração");
            System.out.println("3. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    configuracoesController.getConfiguracoes().getPropriedades()
                            .forEach((key, value) -> System.out.println(key + ": " + value));
                    break;
                case 2:
                    System.out.print("Digite o nome da configuração: ");
                    String chave = scanner.nextLine();
                    System.out.print("Digite o novo valor: ");
                    String valor = scanner.nextLine();
                    configuracoesController.atualizarConfiguracao(chave, valor);
                    break;
                case 3:
                    configurando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
