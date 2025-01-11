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
        boolean senhaCorreta = false;

        // Permitir tentativas de senha
        while (!senhaCorreta) {
            System.out.print("Digite a senha para acessar as configurações: ");
            String senhaDigitada = scanner.nextLine();

            // Verificar se a senha está correta
            if (senhaDigitada.equals(configuracoesController.getConfiguracoes().getPassword())) {
                senhaCorreta = true;  // Senha correta, sair do loop
            } else {
                System.out.println("Senha incorreta. Tente novamente.");
            }
        }

        // Caso a senha esteja correta, exibe o menu de configurações
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
                    configuracoesController.getConfiguracoes().exibirConfiguracoes();
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
