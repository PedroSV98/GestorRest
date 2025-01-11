import Controller.ConfiguracoesController;
import View.ConfiguracoesView;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Criar o controlador e a visão para configurações
        ConfiguracoesController configuracoesController = new ConfiguracoesController();
        ConfiguracoesView configuracoesView = new ConfiguracoesView(configuracoesController);

        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        // Menu Principal
        while (rodando) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Acessar Configurações");
            System.out.println("2. Iniciar o Dia do Restaurante");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    // Acessar configurações
                    configuracoesView.exibirMenu();
                    break;
                case 2:
                    // Iniciar o dia do restaurante (aqui você pode adicionar a lógica do dia a dia)
                    System.out.println("Iniciando o dia do restaurante...");
                    // Adicionar lógica para iniciar o dia (por exemplo, carregar clientes, mesas, etc.)
                    break;
                case 3:
                    // Sair do programa
                    System.out.println("Saindo...");
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
